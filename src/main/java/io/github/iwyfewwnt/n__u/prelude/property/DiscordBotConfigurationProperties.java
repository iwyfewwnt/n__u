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

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Set;

public final class DiscordBotConfigurationProperties {
	private final String token;
	private final String guildId;
	private final Set<GatewayIntent> enableIntents;
	private final Set<GatewayIntent> disableIntents;
	private final Set<CacheFlag> enableCaches;
	private final Set<CacheFlag> disableCaches;
	private final OnlineStatus onlineStatus;

	public DiscordBotConfigurationProperties(
			String token,
			String guildId,
			Set<GatewayIntent> enableIntents,
			Set<GatewayIntent> disableIntents,
			Set<CacheFlag> enableCaches,
			Set<CacheFlag> disableCaches,
			OnlineStatus onlineStatus
	) {
		this.token = token;
		this.guildId = guildId;
		this.enableIntents = enableIntents;
		this.disableIntents = disableIntents;
		this.enableCaches = enableCaches;
		this.disableCaches = disableCaches;
		this.onlineStatus = onlineStatus;
	}

	public String getToken() {
		return this.token;
	}

	public String getGuildId() {
		return this.guildId;
	}

	public Set<GatewayIntent> getEnableIntents() {
		return this.enableIntents;
	}

	public Set<GatewayIntent> getDisableIntents() {
		return this.disableIntents;
	}

	public Set<CacheFlag> getEnableCaches() {
		return this.enableCaches;
	}

	public Set<CacheFlag> getDisableCaches() {
		return this.disableCaches;
	}

	public OnlineStatus getOnlineStatus() {
		return this.onlineStatus;
	}
}
