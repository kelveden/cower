(defproject cower "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [ [org.clojure/clojure "1.5.1"]
                  [org.clojure/data.json "0.2.3"]
                  [com.taoensso/carmine "2.2.0"]
                  [compojure "1.1.5"] ]
  :plugins [ [lein-ring "0.8.5"] ]
  :ring {:handler cower.handler/app}
  :profiles
  {:dev {:dependencies [ [ring-mock "0.1.5"] ]}})
