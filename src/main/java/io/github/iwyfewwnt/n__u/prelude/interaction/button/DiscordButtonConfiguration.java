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

package io.github.iwyfewwnt.n__u.prelude.interaction.button;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DiscordButtonConfiguration {
	private final Map<String, IDiscordButton> discordButtonMapById;

	public DiscordButtonConfiguration(List<IDiscordButton> discordButtonList) {
		if (discordButtonList == null) {
			this.discordButtonMapById = null;
			return;
		}

		int size = discordButtonList.size();

		Map<String, IDiscordButton> discordButtonMapById = new HashMap<>(size);

		for (IDiscordButton discordButton : discordButtonList) {
			if (discordButton == null) {
				continue;
			}

			String discordButtonId = discordButton.getDiscordButtonId();

			if (discordButtonId == null) {
				continue;
			}

			discordButtonMapById.put(discordButtonId, discordButton);
		}

		this.discordButtonMapById = Collections.unmodifiableMap(discordButtonMapById);
	}

	@Bean(DiscordBean.BUTTON_MAP_BY_ID)
	public Map<String, IDiscordButton> getDiscordButtonMapById() {
		return this.discordButtonMapById;
	}
}