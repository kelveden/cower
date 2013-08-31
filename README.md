# Cower
Simple [Bower](https://github.com/bower/bower) registry written in [Clojure](http://clojure.org) over a [Redis](http://redis.io) store.

NOTE: This was done as a proof-of-concept as much as anything else and isn't ready for a heavy-usage environment: there is no testing or configuration for example. Having said that, it might be fine for a dev team server. Use or fork as you wish!

The REST API for the registry is based on [bower/registry](https://github.com/bower/registry). The exception is the DELETE method against the `packages/:name` resource for unregistering a component - this is purely added for use via the likes of `curl` rather than bower itself.

## Licensing
Provided under the [MIT License](http://opensource.org/licenses/MIT). Have fun.

## Prerequisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 1.7.0 or above installed.

## Running
    lein ring uberjar
    cd bin
    ./start-cower

## Development
To start a web server for the application, run:

    lein ring server
