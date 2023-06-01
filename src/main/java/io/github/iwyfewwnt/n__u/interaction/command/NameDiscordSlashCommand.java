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

import io.github.iwyfewwnt.n__u.prelude.interaction.command.IDiscordSlashCommand;
import io.github.iwyfewwnt.n__u.prelude.interaction.modal.IDiscordModal;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.springframework.stereotype.Component;

@Component
public final class NameDiscordSlashCommand implements IDiscordSlashCommand {
	private final Modal nameModal;

	public NameDiscordSlashCommand() {
		this.nameModal = Modal.create(NameDiscordModal.ID, "Name of Yours")
				.addActionRow(
						TextInput.create(NameDiscordModal.TEXT_INPUT_ID, "Text Input", TextInputStyle.SHORT)
								.setPlaceholder("What's your name, _@ ??")
								.setRequired(false)
								.build()
				)
				.build();
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		return Commands.slash("name", "Name!");
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.replyModal(this.nameModal)
				.queue();
	}

	@Component
	public static final class NameDiscordModal implements IDiscordModal {
		public static final String ID = NameDiscordModal.class.getName();

		public static final String TEXT_INPUT_ID = "name";

		@Override
		public String getDiscordModalId() {
			return ID;
		}

		@Override
		public void onDiscordModalInteractionEvent(ModalInteractionEvent event) {
			ModalMapping nameModalMapping = event.getValue(TEXT_INPUT_ID);

			String name = "Cutie";

			if (nameModalMapping != null) {
				String name0 = nameModalMapping.getAsString();

				if (!name0.isBlank()) {
					name = name0;
				}
			}

			event.deferReply(true)
					.setContent("Nice to meet You! " + name + "^^")
					.queue();
		}
	}
}
