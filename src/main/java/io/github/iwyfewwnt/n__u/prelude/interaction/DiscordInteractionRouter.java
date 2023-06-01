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

package io.github.iwyfewwnt.n__u.prelude.interaction;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import io.github.iwyfewwnt.n__u.prelude.interaction.button.IDiscordButton;
import io.github.iwyfewwnt.n__u.prelude.interaction.command.IDiscordSlashCommandInteraction;
import io.github.iwyfewwnt.n__u.prelude.interaction.command.IDiscordSlashCommandOption;
import io.github.iwyfewwnt.n__u.prelude.interaction.context.IDiscordContextMenu;
import io.github.iwyfewwnt.n__u.prelude.interaction.modal.IDiscordModal;
import io.github.iwyfewwnt.n__u.prelude.interaction.select.IDiscordSelectMenu;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class DiscordInteractionRouter {
	private final Map<String, IDiscordSlashCommandInteraction> discordSlashCommandMapByPath;
	private final Map<String, Map<String, IDiscordSlashCommandOption>> discordSlashCommandOptionMapByPath;
	private final Map<String, IDiscordContextMenu> discordContextMenuMapByName;
	private final Map<String, IDiscordButton> discordButtonMapById;
	private final Map<String, IDiscordSelectMenu> discordSelectMenuMapById;
	private final Map<String, IDiscordModal> discordModalMapById;

	public DiscordInteractionRouter(
			@Qualifier(DiscordBean.SLASH_COMMAND_MAP_BY_PATH)
			Map<String, IDiscordSlashCommandInteraction> discordSlashCommandMapByPath,

			@Qualifier(DiscordBean.SLASH_COMMAND_OPTION_MAP_BY_PATH)
			Map<String, Map<String, IDiscordSlashCommandOption>> discordSlashCommandOptionMapByPath,

			@Qualifier(DiscordBean.CONTEXT_MENU_MAP_BY_NAME)
			Map<String, IDiscordContextMenu> discordContextMenuMapByName,

			@Qualifier(DiscordBean.BUTTON_MAP_BY_ID)
			Map<String, IDiscordButton> discordButtonMapById,

			@Qualifier(DiscordBean.SELECT_MENU_MAP_BY_ID)
			Map<String, IDiscordSelectMenu> discordSelectMenuMapById,

			@Qualifier(DiscordBean.MODAL_MAP_BY_ID)
			Map<String, IDiscordModal> discordModalMapById
	) {
		this.discordSlashCommandMapByPath = discordSlashCommandMapByPath;
		this.discordSlashCommandOptionMapByPath = discordSlashCommandOptionMapByPath;
		this.discordContextMenuMapByName = discordContextMenuMapByName;
		this.discordButtonMapById = discordButtonMapById;
		this.discordSelectMenuMapById = discordSelectMenuMapById;
		this.discordModalMapById = discordModalMapById;
	}

	@EventListener(SlashCommandInteractionEvent.class)
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		IDiscordSlashCommandInteraction discordSlashCommand
				= this.getDiscordSlashCommandByEvent(event);

		if (discordSlashCommand == null) {
			return;
		}

		discordSlashCommand.onDiscordSlashCommandInteractionEvent(event);
	}

	@EventListener(CommandAutoCompleteInteractionEvent.class)
	public void onDiscordSlashCommandAutoCompleteInteractionEvent(CommandAutoCompleteInteractionEvent event) {
		IDiscordSlashCommandOption discordSlashCommandOption
				= this.getDiscordSlashCommandOptionByEvent(event);

		if (discordSlashCommandOption == null) {
			return;
		}

		discordSlashCommandOption.onDiscordSlashCommandAutoCompleteInteractionEvent(event);
	}

	@EventListener(UserContextInteractionEvent.class)
	public void onDiscordUserContextInteractionEvent(UserContextInteractionEvent event) {
		IDiscordContextMenu discordContextMenu
				= this.getDiscordContextMenuByEvent(event);

		if (discordContextMenu == null) {
			return;
		}

		discordContextMenu.onDiscordUserContextInteractionEvent(event);
	}

	@EventListener(MessageContextInteractionEvent.class)
	public void onDiscordMessageContextInteractionEvent(MessageContextInteractionEvent event) {
		IDiscordContextMenu discordContextMenu
				= this.getDiscordContextMenuByEvent(event);

		if (discordContextMenu == null) {
			return;
		}

		discordContextMenu.onDiscordMessageContextInteractionEvent(event);
	}

	@EventListener(ButtonInteractionEvent.class)
	public void onDiscordButtonInteractionEvent(ButtonInteractionEvent event) {
		IDiscordButton discordButton
				= this.getDiscordButtonByEvent(event);

		if (discordButton == null) {
			return;
		}

		discordButton.onDiscordButtonInteractionEvent(event);
	}

	@EventListener(StringSelectInteractionEvent.class)
	public void onDiscordStringSelectMenuInteractionEvent(StringSelectInteractionEvent event) {
		IDiscordSelectMenu discordSelectMenu
				= this.getDiscordSelectMenuByEvent(event);

		if (discordSelectMenu == null) {
			return;
		}

		discordSelectMenu.onDiscordStringSelectMenuInteractionEvent(event);
	}

	@EventListener(EntitySelectInteractionEvent.class)
	public void onDiscordEntitySelectMenuInteractionEvent(EntitySelectInteractionEvent event) {
		IDiscordSelectMenu discordSelectMenu
				= this.getDiscordSelectMenuByEvent(event);

		if (discordSelectMenu == null) {
			return;
		}

		discordSelectMenu.onDiscordEntitySelectMenuInteractionEvent(event);
	}

	@EventListener(ModalInteractionEvent.class)
	public void onDiscordModalInteractionEvent(ModalInteractionEvent event) {
		IDiscordModal discordModal
				= this.getDiscordModalByEvent(event);

		if (discordModal == null) {
			return;
		}

		discordModal.onDiscordModalInteractionEvent(event);
	}

	private IDiscordSlashCommandInteraction getDiscordSlashCommandByEvent(SlashCommandInteractionEvent event) {
		if (this.discordSlashCommandMapByPath == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		return this.discordSlashCommandMapByPath.get(event.getFullCommandName());
	}

	private IDiscordSlashCommandOption getDiscordSlashCommandOptionByEvent(CommandAutoCompleteInteractionEvent event) {
		if (this.discordSlashCommandOptionMapByPath == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		Map<String, IDiscordSlashCommandOption> discordSlashCommandOptionMapByName
				= this.discordSlashCommandOptionMapByPath.get(event.getFullCommandName());

		if (discordSlashCommandOptionMapByName == null) {
			return null;
		}

		String discordSlashCommandOptionName = event.getFocusedOption()
				.getName();

		return discordSlashCommandOptionMapByName.get(discordSlashCommandOptionName);
	}

	private IDiscordContextMenu getDiscordContextMenuByEvent(GenericContextInteractionEvent<?> event) {
		if (this.discordContextMenuMapByName == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		return this.discordContextMenuMapByName.get(event.getName());
	}

	private IDiscordButton getDiscordButtonByEvent(ButtonInteractionEvent event) {
		if (this.discordButtonMapById == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		String discordButtonId = event.getButton()
				.getId();

		if (discordButtonId == null) {
			return null;
		}

		return this.discordButtonMapById.get(discordButtonId);
	}

	private IDiscordSelectMenu getDiscordSelectMenuByEvent(GenericSelectMenuInteractionEvent<?, ?> event) {
		if (this.discordSelectMenuMapById == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		return this.discordSelectMenuMapById.get(event.getComponentId());
	}

	private IDiscordModal getDiscordModalByEvent(ModalInteractionEvent event) {
		if (this.discordModalMapById == null) {
			return null;
		}

		if (!isInteractionByPerson(event)) {
			return null;
		}

		return this.discordModalMapById.get(event.getModalId());
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isInteractionByPerson(Interaction interaction) {
		if (interaction == null) {
			return false;
		}

		User user = interaction.getUser();

		return !user.isSystem() && !user.isBot();
	}
}
