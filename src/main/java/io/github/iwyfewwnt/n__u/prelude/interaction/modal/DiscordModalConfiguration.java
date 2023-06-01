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

package io.github.iwyfewwnt.n__u.prelude.interaction.modal;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DiscordModalConfiguration {
	private final Map<String, IDiscordModal> discordModalMapById;

	public DiscordModalConfiguration(List<IDiscordModal> discordModalList) {
		if (discordModalList == null) {
			this.discordModalMapById = null;
			return;
		}

		int size = discordModalList.size();

		Map<String, IDiscordModal> discordModalMapById = new HashMap<>(size);

		for (IDiscordModal discordModal : discordModalList) {
			if (discordModal == null) {
				continue;
			}

			String discordModalId = discordModal.getDiscordModalId();

			if (discordModalId == null) {
				continue;
			}

			discordModalMapById.put(discordModalId, discordModal);
		}

		this.discordModalMapById = Collections.unmodifiableMap(discordModalMapById);
	}

	@Bean(DiscordBean.MODAL_MAP_BY_ID)
	public Map<String, IDiscordModal> getDiscordModalMapById() {
		return this.discordModalMapById;
	}
}
