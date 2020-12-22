# StickyChat

Chat plugin, formatter, and messaging system for dddMC.

## Overview

- Chat formatting with PlaceholderAPI support
- Custom death message support
- Persistent nicknames
- SignSpy
- Mail system
- Direct message system with server-wide blocking support
- Custom join/leave messages
- Cross-server support using Redis pub/sub
- MotD support
- Timed message broadcasts

## API

StickyChat provides an API other plugins can use to interface with its chat, direct messaging, and mail system. You can find the JavaDocs for the API [here](https://www.youtube.com/watch?v=dQw4w9WgXcQ).

## Configuration

The default configuration for StickyChat is below.

```yml
#
# StickyChat
# Authors: SkyezerFox
#
# Main Configuration File
#

# Configure remote database settings.
data:
  database: minecraft
  tableprefix: stickychat_
  host: localhost
  port: 5432
  username: postgres
  password: password

# Configure the name of this server
server: "server"

# Configure the prefix used by StickyChat
prefix: "&b&lStickyChat &r&8» &r"

# Configuration for chat.
chat:
  # The default format to use.
  # Supports placeholders and color codes.
  format: "&7{name} &8» &7{message}"

  # Disable join/quit messages - useful if you have a bungee plugin handling them for you already.
  disable-join-messages: false
  disable-quit-messages: false

  # Whether StickyChat should format death messages.
  format-death-messages: true
  death-message-format: "{content}"

  # Enable cross-server communication.
  incoming-cross-server-messaging: true
  outgoing-cross-server-messaging: true
  cross-server-format: "&8[&a{server} &8- &b{name}&8] &7{message}"

  # Configuration for staff chat
  staff-chat-format: "&d[SC]&7{name} &8» &7{message}"
  staff-chat-prefix: +

# Configuration for private messages.
#
# Available formats:
# %from_name% - Name of the player who sent the message
# %from_uuid% - UUID of the player who sent the message
# %to_name% - Name of the player who received the message
# %message% - The message
#
# Supports placeholders and color codes. For incoming messages, placeholders will be evaluated
# as the recipient. For outgoing, they will be evaluated as the sender.
dms:
  incoming:
    enable-sound: true
    format: "&8[&e&lPM&r&8] &a{from_name} &8» &r{message}"
  outgoing:
    format: "&8[&e&lPM&r&8] &a{from_name} &8» &r{message}"

# Configuration options for SignSpy
# This feature is WIP!
sign-spy:
  enabled: true
  enable-sound: true

debug:
  # Allow players to send themselves messages.
  enable-self-message: false
```

## License

StickyChat is licensed under the GNU General Public License v3.
