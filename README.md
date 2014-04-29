clj-mudder
==========

A [CES](http://en.wikipedia.org/wiki/Entity_component_system)-based MUD engine in Clojure.

:construction: Heavily Under Construction :construction:

Running
-------

`lein start`

Documentation
-------------

### `mudder.main`

The entry point of the app. Currently, it:

* Creates the game world
* Sets up the output channel (console only for now)
* Reads from stdin
* Sends parsed commands to mudder.systems.command

### `mudder.parser`

The command parser. Uses [instaparse](https://github.com/Engelberg/instaparse/) to parse incoming strings into command data structures.

### `mudder.world.ces`

The core Component Entity System. Currently also builds the game world.

### `mudder.systems.command`

Dispatches commands parsed by `mudder.parser/parse` to other systems.

### `mudder.systems.communication`

Sends textual data to the user. Used by other systems to provide output.

### `mudder.systems.environment`

Manages actions that involve interacting with the environment or knowing where the player is in the game world.

License
-------

MIT License

See `LICENSE` for more information.
