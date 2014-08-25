BukkitBanOnDeath
================

This is a plugin for the Bukkit minecraft server wrapper which automatically bans users upon death, and will
unban them after a configurable amount of time. Attempting to reconnect during the ban period returns a message 
containing the remaining time left on the ban.

## Installation

Installing BukkitBOD is very simple, simply download the jar file `out/artifacts/BukkitBOD_jar/BukkitBOD.jar` and put
this file in your plugins folder of your bukkit directory. It will run out of the box, without issue.

## Configuration
Running the plugin once will generate a default configuration file that you can modify. Alternatively, in the plugins
folder of your bukkit directory, create a new directory called `BukkitBOD` and inside it create a file called 
`config.yml`.

The default configuration looks like this

```YAML
banned-metadata-key: bod-banned
ban-length: 60
ban-message: This server is for winners!
```

| Key | Description |
|----- | -----------|
| banned-metadata-key | This is the key that's used with the internal player metadata API. Using a less common key will yield higher efficiency |
| ban-length | The length of the bans performed, in seconds |
| ban-message | The message that is sent to players when they are banned |


If you notice any issues or have feature requests, feel free to drop an issue in the issue tracker, or create a pull
request

