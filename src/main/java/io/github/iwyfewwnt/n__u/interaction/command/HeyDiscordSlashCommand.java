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
import io.github.iwyfewwnt.n__u.prelude.interaction.command.IDiscordSlashCommandOption;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class HeyDiscordSlashCommand implements IDiscordSlashCommand {

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		return Commands.slash("hey", "Hey!");
	}

	@Override
	public IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
		return new IDiscordSlashCommandOption[] {
				new NameDiscordSlashCommandOption()
		};
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		String name = event.getOption(NameDiscordSlashCommandOption.NAME,
				"Cutie", OptionMapping::getAsString);

		event.deferReply(true)
				.setContent("Hey, " + name + "! ^^")
				.queue();
	}

	private static final class NameDiscordSlashCommandOption implements IDiscordSlashCommandOption {
		public static final String NAME = "name";

		private static final String[] AUTO_COMPLETE_ARRAY = {
				"Ризка", "Лизка", "Редиска"
		};

		@Override
		public OptionData initDiscordSlashCommandOptionData() {
			return new OptionData(OptionType.STRING, NAME, "What's your name, _@ ??", false, true);
		}

		@Override
		public void onDiscordSlashCommandAutoCompleteInteractionEvent(CommandAutoCompleteInteractionEvent event) {
			String value = event.getFocusedOption()
					.getValue();

			List<Command.Choice> choices = Stream.of(AUTO_COMPLETE_ARRAY)
					.filter(it -> it.startsWith(value))
					.map(it -> new Command.Choice(it, it))
					.collect(Collectors.toList());

			event.replyChoices(choices)
					.queue();
		}
	}
}
