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

package io.github.iwyfewwnt.n__u.prelude.interaction.select;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DiscordSelectMenuConfiguration {
	private final Map<String, IDiscordSelectMenu> discordSelectMenuMapById;

	public DiscordSelectMenuConfiguration(List<IDiscordSelectMenu> discordSelectMenuList) {
		if (discordSelectMenuList == null) {
			this.discordSelectMenuMapById = null;
			return;
		}

		int size = discordSelectMenuList.size();

		Map<String, IDiscordSelectMenu> discordSelectMenuMapById = new HashMap<>(size);

		for (IDiscordSelectMenu discordSelectMenu : discordSelectMenuList) {
			if (discordSelectMenu == null) {
				continue;
			}

			String discordSelectMenuId = discordSelectMenu.getDiscordSelectMenuId();

			if (discordSelectMenuId == null) {
				continue;
			}

			discordSelectMenuMapById.put(discordSelectMenuId, discordSelectMenu);
		}

		this.discordSelectMenuMapById = Collections.unmodifiableMap(discordSelectMenuMapById);
	}

	@Bean(DiscordBean.SELECT_MENU_MAP_BY_ID)
	public Map<String, IDiscordSelectMenu> getDiscordSelectMenuMapById() {
		return this.discordSelectMenuMapById;
	}
}
