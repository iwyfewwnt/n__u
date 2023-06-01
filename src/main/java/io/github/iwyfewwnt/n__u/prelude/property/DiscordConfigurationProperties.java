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

package io.github.iwyfewwnt.n__u.prelude.property;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@ConfigurationProperties("discord")
public final class DiscordConfigurationProperties {
	private final Boolean enable;

	@NestedConfigurationProperty
	private final DiscordBotConfigurationProperties bot;

	public DiscordConfigurationProperties(
			Boolean enable,
			DiscordBotConfigurationProperties bot
	) {
		this.enable = enable;
		this.bot = bot;
	}

	@Bean(DiscordBean.ENABLE_CONFIGURATION_PROPERTY)
	public Boolean getEnable() {
		return this.enable;
	}

	@Bean(DiscordBean.BOT_CONFIGURATION_PROPERTIES)
	public DiscordBotConfigurationProperties getBot() {
		return this.bot;
	}

	@Bean(DiscordBean.BOT_TOKEN_CONFIGURATION_PROPERTY)
	public String getBotToken() {
		return this.bot.getToken();
	}

	@Bean(DiscordBean.BOT_GUILD_ID_CONFIGURATION_PROPERTY)
	public String getBotGuildId() {
		return this.bot.getGuildId();
	}

	@Bean(DiscordBean.BOT_ENABLE_INTENTS_CONFIGURATION_PROPERTY)
	public Set<GatewayIntent> getBotEnableIntents() {
		return this.bot.getEnableIntents();
	}

	@Bean(DiscordBean.BOT_DISABLE_INTENTS_CONFIGURATION_PROPERTY)
	public Set<GatewayIntent> getBotDisableIntents() {
		return this.bot.getDisableIntents();
	}

	@Bean(DiscordBean.BOT_ENABLE_CACHES_CONFIGURATION_PROPERTY)
	public Set<CacheFlag> getBotEnableCaches() {
		return this.bot.getEnableCaches();
	}

	@Bean(DiscordBean.BOT_DISABLE_CACHES_CONFIGURATION_PROPERTY)
	public Set<CacheFlag> getBotDisableCaches() {
		return this.bot.getDisableCaches();
	}

	@Bean(DiscordBean.BOT_ONLINE_STATUS_CONFIGURATION_PROPERTY)
	public OnlineStatus getBotOnlineStatus() {
		return this.bot.getOnlineStatus();
	}
}
