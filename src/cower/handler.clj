(ns cower.handler
  (:use compojure.core)
  (:require [clojure.data.json :as json]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [taoensso.carmine :as car :refer (wcar)]))

(def server1-conn { :pool {} :spec {} })
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn- key-value-to-map [key-value]
  (hash-map :name (nth key-value 0) :url (nth key-value 1)))

(defn- key-values-to-json [keys values]
  (map key-value-to-map (zipmap keys values)))

(defn- search [name-pattern]
  (let [keys (wcar* (car/keys name-pattern))
        values (if (empty? keys)
                 []
                 (wcar* (apply car/mget keys)))]
    (key-values-to-json keys values)))

(defn- write-json [thing]
  (json/write-str thing :escape-slash false))

(defroutes app-routes
  (GET "/packages" []
       {:status 200
        :body (write-json (search "*"))})

  (POST "/packages" [name url]
        (do
          (wcar* (car/set name url))
          {:status 201}))

  (GET "/packages/:name" [name]
       (let [url (wcar* (car/get name))]
         (if url
           {:status 200
            :body (write-json
                   (key-value-to-map [name url]))}
           {:status 404})))

  (DELETE "/packages/:name" [name]
          (do
            (wcar* (car/del name))
            {:status 204}))

  (GET "/packages/search/:name" [name]
       {:status 200
        :body (write-json
               (search (str "*" name "*")))})

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
