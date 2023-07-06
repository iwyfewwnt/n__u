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

package io.github.iwyfewwnt.n__u.interaction.context;

import io.github.iwyfewwnt.jda.prelude.interaction.context.IDiscordContextMenu;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public final class GetUserAvatarDiscordContextMenu implements IDiscordContextMenu {

	@Override
	public CommandData initDiscordContextMenuData() {
		return Commands.context(Command.Type.USER, "Get User Avatar");
	}

	@Override
	public void onDiscordUserContextInteractionEvent(UserContextInteractionEvent event) {
		String userAvatarUrl = event.getTarget()
				.getEffectiveAvatarUrl();

		event.deferReply(true)
				.queue(hook -> hook.sendMessage(userAvatarUrl)
						.queue());
	}
}
