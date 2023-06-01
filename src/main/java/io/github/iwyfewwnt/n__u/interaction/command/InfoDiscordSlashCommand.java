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

package io.github.iwyfewwnt.n__u.interaction.command;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import io.github.iwyfewwnt.n__u.prelude.interaction.command.IDiscordSlashCommand;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class InfoDiscordSlashCommand implements IDiscordSlashCommand {
	private final Button githubButton;

	public InfoDiscordSlashCommand(
			@Autowired(required = false)
			@Qualifier(DiscordBean.GITHUB_REPOSITORY_URL_CONFIGURATION_PROPERTY)
			String githubRepositoryUrl
	) {
		if (githubRepositoryUrl == null
				|| githubRepositoryUrl.isBlank()) {
			this.githubButton = null;
			return;
		}

		Emoji squidEmoji = Emoji.fromUnicode("\uD83E\uDD91");

		this.githubButton = Button.link(githubRepositoryUrl, "GitHub")
				.withEmoji(squidEmoji);
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		if (this.githubButton == null) {
			return null;
		}

		return Commands.slash("info", "Info!");
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.addActionRow(this.githubButton)
				.queue();
	}
}
