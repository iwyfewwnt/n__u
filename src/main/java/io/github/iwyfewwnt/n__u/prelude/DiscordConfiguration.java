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

package io.github.iwyfewwnt.n__u.prelude;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import io.github.iwyfewwnt.n__u.prelude.condition.ConditionalOnDiscordEnable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConditionalOnDiscordEnable
public class DiscordConfiguration {
	private final JDA discordApi;

	public DiscordConfiguration(
			@Autowired(required = false)
			Set<EventListener> discordEventListenerSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.SLASH_COMMAND_DATA_SET)
			Set<SlashCommandData> discordSlashCommandDataSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.CONTEXT_MENU_DATA_SET)
			Set<CommandData> discordContextMenuDataSet,

			@Qualifier(DiscordBean.BOT_TOKEN_CONFIGURATION_PROPERTY)
			String discordBotToken,

			@Autowired(required = false)
			@Qualifier(DiscordBean.BOT_GUILD_ID_CONFIGURATION_PROPERTY)
			String discordBotGuildId,

			@Qualifier(DiscordBean.BOT_ENABLE_INTENTS_CONFIGURATION_PROPERTY)
			Set<GatewayIntent> discordBotEnableIntentSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.BOT_DISABLE_INTENTS_CONFIGURATION_PROPERTY)
			Set<GatewayIntent> discordBotDisableIntentSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.BOT_ENABLE_CACHES_CONFIGURATION_PROPERTY)
			Set<CacheFlag> discordBotEnableCacheSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.BOT_DISABLE_CACHES_CONFIGURATION_PROPERTY)
			Set<CacheFlag> discordBotDisableCacheSet,

			@Autowired(required = false)
			@Qualifier(DiscordBean.BOT_ONLINE_STATUS_CONFIGURATION_PROPERTY)
			OnlineStatus discordBotOnlineStatus
	) throws InterruptedException {
		JDABuilder discordApiBuilder = JDABuilder.create(discordBotToken, discordBotEnableIntentSet);

		if (discordBotDisableIntentSet != null
				&& !discordBotDisableIntentSet.isEmpty()) {
			discordApiBuilder.disableIntents(discordBotDisableIntentSet);
		}

		if (discordBotEnableCacheSet != null
				&& !discordBotEnableCacheSet.isEmpty()) {
			discordApiBuilder.enableCache(discordBotEnableCacheSet);
		}

		if (discordBotDisableCacheSet != null
				&& !discordBotDisableCacheSet.isEmpty()) {
			discordApiBuilder.disableCache(discordBotDisableCacheSet);
		}

		if (discordBotOnlineStatus != null) {
			discordApiBuilder.setStatus(discordBotOnlineStatus);
		}

		if (discordEventListenerSet != null
				&& !discordEventListenerSet.isEmpty()) {
			Object[] discordEventListenerArray
					= discordEventListenerSet.toArray();

			discordApiBuilder.addEventListeners(discordEventListenerArray);
		}

		this.discordApi = discordApiBuilder.build()
				.awaitReady();

		if (discordBotGuildId == null) {
			return;
		}

		Guild discordBotGuild = this.discordApi.getGuildById(discordBotGuildId);

		if (discordBotGuild == null) {
			return;
		}

		CommandListUpdateAction discordCommandListUpdateAction
				= discordBotGuild.updateCommands();

		if (discordSlashCommandDataSet != null
				&& !discordSlashCommandDataSet.isEmpty()) {
			//noinspection ResultOfMethodCallIgnored
			discordCommandListUpdateAction.addCommands(discordSlashCommandDataSet);
		}

		if (discordContextMenuDataSet != null
				&& !discordContextMenuDataSet.isEmpty()) {
			//noinspection ResultOfMethodCallIgnored
			discordCommandListUpdateAction.addCommands(discordContextMenuDataSet);
		}

		discordCommandListUpdateAction.queue();
	}

	@Bean(DiscordBean.API)
	public JDA getDiscordApi() {
		return this.discordApi;
	}
}
