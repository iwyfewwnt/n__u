<!--suppress HtmlDeprecatedAttribute -->
<h1 align="center">As silly as this emoji ~ N__U</h1>

## Packages
* [/interaction](./src/main/java/io/github/iwyfewwnt/n__u/interaction)
  * This package contains examples of defining slash commands
    and context menus, using options, buttons, select menus, and modals.
    Moreover, handling all related events to those components.

* [/event](./src/main/java/io/github/iwyfewwnt/n__u/event)
  * This package contains examples of defining event listeners.


## Hierarchy
```shell
Tree .\ /F
```
```text
.\SRC\MAIN\JAVA\IO\GITHUB\IWYFEWWNT\N__U
│   Main.java
│
├───event
│       DiscordMessageReceivedEventListener.java
│
├───interaction
│   ├───command
│   │       HeyDiscordSlashCommand.java
│   │       InfoDiscordSlashCommand.java
│   │       LoveDiscordSlashCommand.java
│   │       NameDiscordSlashCommand.java
│   │       TapDiscordSlashCommand.java
│   │
│   └───context
│           GetUserAvatarDiscordContextMenu.java
│           GetUserMessageDiscordContextMenu.java
│
└───prelude
    ├───bean
    │       LocalBean.java
    │
    └───property
            GithubConfigurationProperties.java
            GithubRepositoryConfigurationProperties.java
```

## License
Licensed under the [Apache-2.0 license](./LICENSE).
