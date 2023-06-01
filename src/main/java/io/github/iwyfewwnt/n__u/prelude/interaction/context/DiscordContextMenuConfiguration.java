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

package io.github.iwyfewwnt.n__u.prelude.interaction.context;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DiscordContextMenuConfiguration {
	private final Set<CommandData> discordContextMenuDataSet;
	private final Map<String, IDiscordContextMenu> discordContextMenuMapByName;

	public DiscordContextMenuConfiguration(Set<IDiscordContextMenu> discordContextMenuSet) {
		if (discordContextMenuSet == null) {
			this.discordContextMenuDataSet = null;
			this.discordContextMenuMapByName = null;
			return;
		}

		int size = discordContextMenuSet.size();

		Set<CommandData> discordContextMenuDataSet = new HashSet<>(size);
		Map<String, IDiscordContextMenu> discordContextMenuMapByName = new HashMap<>(size);

		for (IDiscordContextMenu discordContextMenu : discordContextMenuSet) {
			if (discordContextMenu == null) {
				continue;
			}

			CommandData discordContextMenuData
					= discordContextMenu.initDiscordContextMenuData();

			if (discordContextMenuData == null) {
				continue;
			}

			String discordContextMenuName = discordContextMenuData.getName();

			discordContextMenuDataSet.add(discordContextMenuData);
			discordContextMenuMapByName.put(discordContextMenuName, discordContextMenu);
		}

		this.discordContextMenuDataSet = Collections.unmodifiableSet(discordContextMenuDataSet);
		this.discordContextMenuMapByName = Collections.unmodifiableMap(discordContextMenuMapByName);
	}

	@Bean(DiscordBean.CONTEXT_MENU_DATA_SET)
	public Set<CommandData> getDiscordContextMenuDataSet() {
		return this.discordContextMenuDataSet;
	}

	@Bean(DiscordBean.CONTEXT_MENU_MAP_BY_NAME)
	public Map<String, IDiscordContextMenu> getDiscordContextMenuMapByName() {
		return this.discordContextMenuMapByName;
	}
}
