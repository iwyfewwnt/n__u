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

import io.github.iwyfewwnt.jda.prelude.interaction.button.IDiscordButton;
import io.github.iwyfewwnt.jda.prelude.interaction.command.IDiscordSlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public final class TapDiscordSlashCommand implements IDiscordSlashCommand {
	private final Button tapButton;
	private final Map<Long, Long> lastMessageIdMapByUserId;

	public TapDiscordSlashCommand() {
		this.tapButton = Button.primary(TapDiscordButton.ID, "Tap Tap!");
		this.lastMessageIdMapByUserId = new ConcurrentHashMap<>();
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		return Commands.slash("tap", "Tap!");
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		long userId = event.getUser()
				.getIdLong();

		this.lastMessageIdMapByUserId.remove(userId);

		event.deferReply(true)
				.addActionRow(this.tapButton)
				.queue();
	}

	@Component
	public static final class TapDiscordButton implements IDiscordButton {
		public static final String ID = TapDiscordButton.class.getName();

		private final AtomicInteger tapCountRef;
		private final Map<Long, Long> lastMessageIdMapByUserId;

		public TapDiscordButton(TapDiscordSlashCommand parent) {
			this.tapCountRef = new AtomicInteger();
			this.lastMessageIdMapByUserId = parent.lastMessageIdMapByUserId;
		}

		@Override
		public String getDiscordButtonId() {
			return ID;
		}

		@Override
		public void onDiscordButtonInteractionEvent(ButtonInteractionEvent event) {
			int tapCount = this.tapCountRef.incrementAndGet();
			String tapMessage = "My compliment to You! " + tapCount + " is an awesome number!";

			long userId = event.getUser()
					.getIdLong();

			Long lastMessageId = this.lastMessageIdMapByUserId.get(userId);

			if (lastMessageId == null) {
				event.deferReply(true)
						.setContent(tapMessage)
						.queue(hook -> hook.retrieveOriginal()
								.queue(message -> this.lastMessageIdMapByUserId.put(userId, message.getIdLong())));

				return;
			}

			event.deferEdit()
					.queue(hook -> hook.editMessageById(lastMessageId, tapMessage)
							.queue(message -> this.lastMessageIdMapByUserId.put(userId, message.getIdLong())));
		}
	}
}
