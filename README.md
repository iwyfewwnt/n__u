<!--suppress HtmlDeprecatedAttribute -->
<h1 align="center">As silly as this emoji ~ N__U</h1>

## Packages
* [/prelude](./src/main/java/io/github/iwyfewwnt/n__u/prelude)
  * This package can be separated from the entire project 
    to a `jda-spring-boot-starter` and most likely it will
    happen in few days or weeks.
  
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
    │   DiscordConfiguration.java
    │
    ├───bean
    │       DiscordBean.java
    │
    ├───condition
    │       ConditionalOnDiscordEnable.java
    │
    ├───event
    │       DiscordEventConfiguration.java
    │       DiscordEventPublisher.java
    │
    ├───interaction
    │   │   DiscordInteractionRouter.java
    │   │
    │   ├───button
    │   │       DiscordButtonConfiguration.java
    │   │       IDiscordButton.java
    │   │       IDiscordButtonInteraction.java
    │   │
    │   ├───command
    │   │       DiscordSlashCommandConfiguration.java
    │   │       IDiscordSlashCommand.java
    │   │       IDiscordSlashCommandAutoCompleteInteraction.java
    │   │       IDiscordSlashCommandInteraction.java
    │   │       IDiscordSlashCommandOption.java
    │   │       IDiscordSlashCommandOptionContainer.java
    │   │       IDiscordSlashSubcommand.java
    │   │       IDiscordSlashSubcommandContainer.java
    │   │       IDiscordSlashSubcommandGroup.java
    │   │       IDiscordSlashSubcommandGroupContainer.java
    │   │
    │   ├───context
    │   │       DiscordContextMenuConfiguration.java
    │   │       IDiscordContextMenu.java
    │   │       IDiscordMessageContextInteraction.java
    │   │       IDiscordUserContextInteraction.java
    │   │
    │   ├───modal
    │   │       DiscordModalConfiguration.java
    │   │       IDiscordModal.java
    │   │       IDiscordModalInteraction.java
    │   │
    │   └───select
    │           DiscordSelectMenuConfiguration.java
    │           IDiscordEntitySelectMenuInteraction.java
    │           IDiscordSelectMenu.java
    │           IDiscordStringSelectMenuInteraction.java
    │
    └───property
            DiscordBotConfigurationProperties.java
            DiscordConfigurationProperties.java
            GithubConfigurationProperties.java
            GithubRepositoryConfigurationProperties.java
```

## License
Licensed under the [Apache-2.0 license](./LICENSE).
