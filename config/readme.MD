# Configuration MicroStream

# Minimal

As a minimum we need to connect the Root object to a storage location where the binary representation is stored.

This is done in the _simple_ module.

# All configuration options

- simple : Absolute minimal configuration
- builder : Setting many options through a builder pattern
- external : Read from properties or XML file
- foundation : Full fledged config when you want to use custom versions of MicroStream modules.

# Good to now

- a `StorageManager` need to be started before it can be used.
- Once stopped, it cannot be started again
- A Foundation instance cannot be reused to create another `StorageManager`.
