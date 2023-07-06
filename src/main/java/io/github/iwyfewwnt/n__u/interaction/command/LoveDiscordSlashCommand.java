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

import io.github.iwyfewwnt.jda.prelude.interaction.command.IDiscordSlashCommand;
import io.github.iwyfewwnt.jda.prelude.interaction.select.IDiscordSelectMenu;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public final class LoveDiscordSlashCommand implements IDiscordSlashCommand {
	private final StringSelectMenu loveSelectMenu;
	private final Map<Long, Message> lastMessageMapByUserId;

	public LoveDiscordSlashCommand() {
		Emoji kittenEmoji = Emoji.fromUnicode("\uD83D\uDC08");
		Emoji butterflyEmoji = Emoji.fromUnicode("\uD83E\uDD8B");

		this.loveSelectMenu = StringSelectMenu.create(LoveDiscordSelectMenu.ID)
				.addOption("Kittens", "kittens", kittenEmoji)
				.addOption("Butterflies", "butterflies", butterflyEmoji)
				.build();

		this.lastMessageMapByUserId = new ConcurrentHashMap<>();
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		return Commands.slash("love", "Love!");
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.setContent("What do you love more than everything, _@ ??")
				.addActionRow(this.loveSelectMenu)
				.queue(hook -> hook.retrieveOriginal()
						.queue(message -> {
							long userId = event.getUser()
									.getIdLong();

							this.lastMessageMapByUserId.put(userId, message);
						}));
	}

	@Component
	public static final class LoveDiscordSelectMenu implements IDiscordSelectMenu {
		public static final String ID = LoveDiscordSelectMenu.class.getName();

		private final Map<Long, Message> lastMessageMapByUserId;

		public LoveDiscordSelectMenu(LoveDiscordSlashCommand parent) {
			this.lastMessageMapByUserId = parent.lastMessageMapByUserId;
		}

		@Override
		public String getDiscordSelectMenuId() {
			return ID;
		}

		@Override
		public void onDiscordStringSelectMenuInteractionEvent(StringSelectInteractionEvent event) {
			String value = event.getValues()
					.get(0);

			event.deferReply(true)
					.setContent("Aww~ I love " + value + " too!")
					.queue(hook -> {
						long userId = event.getUser()
								.getIdLong();

						Message lastMessage = this.lastMessageMapByUserId.get(userId);

						if (lastMessage == null) {
							return;
						}

						List<ActionRow> disabledActionRows = lastMessage.getActionRows()
								.stream()
								.map(ActionRow::asDisabled)
								.collect(Collectors.toList());

						hook.editMessageComponentsById(lastMessage.getIdLong(), disabledActionRows)
								.queue();
					});
		}
	}
}
