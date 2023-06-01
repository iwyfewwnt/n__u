/*
 * Copyright 2023 iwyfewwnt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.iwyfewwnt.n__u.prelude.bean;

public final class DiscordBean {

	// -- /prelude/event/DiscordEventConfiguration.java
	public static final String APPLICATION_EVENT_MULTICASTER = "discordApplicationEventMulticaster";

	// -- /prelude/DiscordConfiguration.java
	public static final String API = "discordApi";

	// -- /prelude/interaction/command/DiscordSlashCommandConfiguration.java
	public static final String SLASH_COMMAND_DATA_SET = "discordSlashCommandDataSet";
	public static final String SLASH_COMMAND_MAP_BY_PATH = "discordSlashCommandMapByPath";
	public static final String SLASH_COMMAND_OPTION_MAP_BY_PATH = "discordSlashCommandOptionMapByPath";

	// -- /prelude/interaction/context/DiscordContextMenuConfiguration.java
	public static final String CONTEXT_MENU_DATA_SET = "discordContextMenuDataSet";
	public static final String CONTEXT_MENU_MAP_BY_NAME = "discordContextMenuMapByName";

	// -- /prelude/interaction/button/DiscordButtonConfiguration.java
	public static final String BUTTON_MAP_BY_ID = "discordButtonMapById";

	// -- /prelude/interaction/modal/DiscordModalConfiguration.java
	public static final String MODAL_MAP_BY_ID = "discordModalMapById";

	// -- /prelude/interaction/select/DiscordSelectMenuConfiguration.java
	public static final String SELECT_MENU_MAP_BY_ID = "discordSelectMenuMapById";

	// -- /prelude/property/DiscordConfigurationProperties.java
	public static final String ENABLE_CONFIGURATION_PROPERTY = "discordEnableConfigurationProperty";
	public static final String BOT_CONFIGURATION_PROPERTIES = "discordBotConfigurationProperties";

	// -- /prelude/property/DiscordBotConfigurationProperties.java
	public static final String BOT_TOKEN_CONFIGURATION_PROPERTY = "discordBotTokenConfigurationProperty";
	public static final String BOT_GUILD_ID_CONFIGURATION_PROPERTY = "discordBotGuildIdConfigurationProperty";
	public static final String BOT_ENABLE_INTENTS_CONFIGURATION_PROPERTY = "discordBotEnableIntentsConfigurationProperty";
	public static final String BOT_DISABLE_INTENTS_CONFIGURATION_PROPERTY = "discordBotDisableIntentsConfigurationProperty";
	public static final String BOT_ENABLE_CACHES_CONFIGURATION_PROPERTY = "discordBotEnableCachesConfigurationProperty";
	public static final String BOT_DISABLE_CACHES_CONFIGURATION_PROPERTY = "discordBotDisableCachesConfigurationProperty";
	public static final String BOT_ONLINE_STATUS_CONFIGURATION_PROPERTY = "discordBotOnlineStatusConfigurationProperty";

	// -- /prelude/property/GithubConfigurationProperties.java
	public static final String GITHUB_REPOSITORY_CONFIGURATION_PROPERTIES = "githubRepositoryConfigurationProperties";

	// -- /prelude/property/GithubRepositoryConfigurationProperties.java
	public static final String GITHUB_REPOSITORY_URL_CONFIGURATION_PROPERTY = "githubRepositoryUrlConfigurationProperty";

	private DiscordBean() {
		throw new UnsupportedOperationException();
	}
}
