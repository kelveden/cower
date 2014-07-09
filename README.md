# Cower
Simple [Bower](https://github.com/bower/bower) registry written in [Clojure](http://clojure.org) over a [Redis](http://redis.io) store.

NOTE: This was done as a proof-of-concept as much as anything else and isn't ready for a heavy-usage environment: there is no testing or configuration for example. Having said that, it might be fine for a dev team server. Use or fork as you wish!

The REST API for the registry is based on [bower/registry](https://github.com/bower/registry). The exception is the DELETE method against the `packages/:name` resource for unregistering a component - this is purely added for use via the likes of `curl` rather than bower itself.

## Licensing
Provided under the [MIT License](http://opensource.org/licenses/MIT). Have fun.

## Prerequisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 2.x installed.

## Running
    lein ring uberjar
    cd bin
    ./start-cower

Sysout and syserr are redirected to `bin/cower.log`. Note that the `start-cower` shell script is simply provided as an example of how one might kick-start Cower - modify as appropriate to needs. 

## Development
To start a web server for the application, run:

    lein ring server

## Using the registry
The [Bower spec](https://docs.google.com/document/d/1APq7oA9tNao1UYWyOm8dKqlRP2blVkROYLZ2fLIjtWc) has details on how to configure your local Bower installation to make use of custom registries like Cower. Take a look in the 'Configuration' section and, in particular, the registry object.

Essentially though, you just need a `~/.bowerrc` that looks something like this:

    {
        "registry": {
            "search": [ "http://<cower host>:3000", "http://bower.herokuapp.com" ],
            "register": "http://<cower host>:3000",
            "publish": "http://<cower host>:3000"
        }
    }
    
Or, if you don't want to faff around with your Bower config, you can specify Bower configuration on the fly like this:

    bower register mypackage myurl --config.registry=http://<cower host>:3000

### Using from behind a company proxy
If you're anything like me, you'll want to bypass your http proxy to access your internal bower registry. Unfortunately, bower doesn't have a "no_proxy" type of setting; therefore you'll need to specify it on the fly:

    bower install mypackage --config.proxy=
