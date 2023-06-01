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

package io.github.iwyfewwnt.n__u.prelude.interaction.command;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Configuration
public class DiscordSlashCommandConfiguration {
	private static final String DISCORD_SLASH_COMMAND_PATH_SEPARATOR = " ";

	private final Set<SlashCommandData> discordSlashCommandDataSet;
	private final Map<String, IDiscordSlashCommandInteraction> discordSlashCommandMapByPath;
	private final Map<String, Map<String, IDiscordSlashCommandOption>> discordSlashCommandOptionMapByPath;

	public DiscordSlashCommandConfiguration(Set<IDiscordSlashCommand> discordSlashCommandSet) {
		if (discordSlashCommandSet == null) {
			this.discordSlashCommandDataSet = null;
			this.discordSlashCommandMapByPath = null;
			this.discordSlashCommandOptionMapByPath = null;
			return;
		}

		int size = discordSlashCommandSet.size();

		Set<SlashCommandData> discordSlashCommandDataSet = new HashSet<>(size);
		Map<String, IDiscordSlashCommandInteraction> discordSlashCommandMapByPath = new HashMap<>(size);
		Map<String, Map<String, IDiscordSlashCommandOption>> discordSlashCommandOptionMapByPath = new HashMap<>();

		for (IDiscordSlashCommand discordSlashCommand : discordSlashCommandSet) {
			if (discordSlashCommand == null) {
				continue;
			}

			SlashCommandData discordSlashCommandData
					= discordSlashCommand.initDiscordSlashCommandData();

			if (discordSlashCommandData == null) {
				continue;
			}

			String discordSlashCommandName = discordSlashCommandData.getName();

			IDiscordSlashSubcommandGroup[] discordSlashSubcommandGroupArray
					= discordSlashCommand.getDiscordSlashSubcommandGroupArray();

			if (discordSlashSubcommandGroupArray != null) {
				for (IDiscordSlashSubcommandGroup discordSlashSubcommandGroup : discordSlashSubcommandGroupArray) {
					SubcommandGroupData discordSlashSubcommandGroupData
							= discordSlashSubcommandGroup.initDiscordSlashSubcommandGroupData();

					if (discordSlashSubcommandGroupData == null) {
						continue;
					}

					String discordSlashSubcommandGroupName = discordSlashSubcommandGroupData.getName();

					initDiscordSlashSubcommandArray(discordSlashSubcommandGroup.getDiscordSlashSubcommandArray(),
							(discordSlashSubcommand, discordSlashSubcommandData) -> {
								discordSlashSubcommandGroupData.addSubcommands(discordSlashSubcommandData);

								String discordSlashSubcommandName
										= discordSlashSubcommandData.getName();

								String discordSlashSubcommandPath = discordSlashCommandName
										+ DISCORD_SLASH_COMMAND_PATH_SEPARATOR
										+ discordSlashSubcommandGroupName
										+ DISCORD_SLASH_COMMAND_PATH_SEPARATOR
										+ discordSlashSubcommandName;

								discordSlashCommandOptionMapByPath.put(discordSlashSubcommandPath,
										initDiscordSlashCommandOptionMapByName(
												discordSlashSubcommand, discordSlashSubcommandData
										));

								discordSlashCommandMapByPath.put(discordSlashSubcommandPath, discordSlashSubcommand);
							});

					discordSlashCommandData.addSubcommandGroups(discordSlashSubcommandGroupData);
				}
			}

			initDiscordSlashSubcommandArray(discordSlashCommand.getDiscordSlashSubcommandArray(),
					(discordSlashSubcommand, discordSlashSubcommandData) -> {
						discordSlashCommandData.addSubcommands(discordSlashSubcommandData);

						String discordSlashSubcommandName
								= discordSlashSubcommandData.getName();

						String discordSlashSubcommandPath = discordSlashCommandName
								+ DISCORD_SLASH_COMMAND_PATH_SEPARATOR
								+ discordSlashSubcommandName;

						discordSlashCommandOptionMapByPath.put(discordSlashSubcommandPath,
								initDiscordSlashCommandOptionMapByName(
										discordSlashSubcommand, discordSlashSubcommandData
								));

						discordSlashCommandMapByPath.put(discordSlashSubcommandPath, discordSlashSubcommand);
					});

			discordSlashCommandOptionMapByPath.put(discordSlashCommandName,
					initDiscordSlashCommandOptionMapByName(
							discordSlashCommand, discordSlashCommandData
					));

			discordSlashCommandDataSet.add(discordSlashCommandData);
			discordSlashCommandMapByPath.put(discordSlashCommandName, discordSlashCommand);
		}

		this.discordSlashCommandDataSet = Collections.unmodifiableSet(discordSlashCommandDataSet);
		this.discordSlashCommandMapByPath = Collections.unmodifiableMap(discordSlashCommandMapByPath);
		this.discordSlashCommandOptionMapByPath = Collections.unmodifiableMap(discordSlashCommandOptionMapByPath);
	}

	@Bean(DiscordBean.SLASH_COMMAND_DATA_SET)
	public Set<SlashCommandData> getDiscordSlashCommandDataSet() {
		return this.discordSlashCommandDataSet;
	}

	@Bean(DiscordBean.SLASH_COMMAND_MAP_BY_PATH)
	public Map<String, IDiscordSlashCommandInteraction> getDiscordSlashCommandMapByPath() {
		return this.discordSlashCommandMapByPath;
	}

	@Bean(DiscordBean.SLASH_COMMAND_OPTION_MAP_BY_PATH)
	public Map<String, Map<String, IDiscordSlashCommandOption>> getDiscordSlashCommandOptionMapByPath() {
		return this.discordSlashCommandOptionMapByPath;
	}

	private static void initDiscordSlashSubcommandArray(
			IDiscordSlashSubcommand[] discordSlashSubcommandArray,
			BiConsumer<IDiscordSlashSubcommand, SubcommandData> discordSlashSubcommandConsumer
	) {
		initDiscordSlashCommandInteractionArray(discordSlashSubcommandArray, discordSlashSubcommandConsumer,
				IDiscordSlashSubcommand::initDiscordSlashSubcommandData);
	}

	private static void initDiscordSlashCommandOptionArray(
			IDiscordSlashCommandOption[] discordSlashCommandOptionArray,
			BiConsumer<IDiscordSlashCommandOption, OptionData> discordSlashCommandOptionConsumer
	) {
		initDiscordSlashCommandInteractionArray(discordSlashCommandOptionArray, discordSlashCommandOptionConsumer,
				IDiscordSlashCommandOption::initDiscordSlashCommandOptionData);
	}

	private static <T, U> void initDiscordSlashCommandInteractionArray(
			T[] discordSlashCommandInteractionArray,
			BiConsumer<T, U> discordSlashCommandInteractionConsumer,
			Function<T, U> discordSlashCommandInteractionInitializer
	) {
		if (discordSlashCommandInteractionArray == null
				|| discordSlashCommandInteractionConsumer == null
				|| discordSlashCommandInteractionInitializer == null) {
			return;
		}

		for (T discordSlashCommandInteraction : discordSlashCommandInteractionArray) {
			if (discordSlashCommandInteraction == null) {
				continue;
			}

			U discordSlashCommandInteractionData
					= discordSlashCommandInteractionInitializer.apply(discordSlashCommandInteraction);

			if (discordSlashCommandInteractionData == null) {
				continue;
			}

			discordSlashCommandInteractionConsumer.accept(discordSlashCommandInteraction, discordSlashCommandInteractionData);
		}
	}

	private static Map<String, IDiscordSlashCommandOption> initDiscordSlashCommandOptionMapByName(
			IDiscordSlashCommandOptionContainer discordSlashCommandOptionContainer,
			SlashCommandData discordSlashCommandData
	) {
		return initDiscordSlashCommandOptionMapByName(discordSlashCommandOptionContainer,
				discordSlashCommandData, SlashCommandData::addOptions
		);
	}

	private static Map<String, IDiscordSlashCommandOption> initDiscordSlashCommandOptionMapByName(
			IDiscordSlashCommandOptionContainer discordSlashCommandOptionContainer,
			SubcommandData discordSlashSubcommandData
	) {
		return initDiscordSlashCommandOptionMapByName(discordSlashCommandOptionContainer,
				discordSlashSubcommandData, SubcommandData::addOptions
		);
	}

	private static <T> Map<String, IDiscordSlashCommandOption> initDiscordSlashCommandOptionMapByName(
			IDiscordSlashCommandOptionContainer discordSlashCommandOptionContainer,
			T discordSlashCommandCustomData, BiConsumer<T, OptionData> discordSlashCommandCustomDataConsumer
	) {
		Map<String, IDiscordSlashCommandOption> discordSlashCommandOptionMapByName = new HashMap<>();

		initDiscordSlashCommandOptionArray(discordSlashCommandOptionContainer.getDiscordSlashCommandOptionArray(),
				(discordSlashCommandOption, discordSlashCommandOptionData) -> {
					if (discordSlashCommandCustomDataConsumer != null) {
						discordSlashCommandCustomDataConsumer.accept(discordSlashCommandCustomData, discordSlashCommandOptionData);
					}

					String discordSlashCommandOptionName
							= discordSlashCommandOptionData.getName();

					discordSlashCommandOptionMapByName.put(discordSlashCommandOptionName, discordSlashCommandOption);
				});

		return Collections.unmodifiableMap(discordSlashCommandOptionMapByName);
	}
}
