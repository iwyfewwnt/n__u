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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class GetUserMessageDiscordContextMenu implements IDiscordContextMenu {

	@Override
	public CommandData initDiscordContextMenuData() {
		return Commands.context(Command.Type.MESSAGE, "Get User Message");
	}

	@Override
	public void onDiscordMessageContextInteractionEvent(MessageContextInteractionEvent event) {
		Message message = event.getTarget();
		String content = message.getContentRaw();

		if (content.isEmpty()) {
			List<Message.Attachment> attachments = message.getAttachments();

			content = attachments.stream()
					.map(Message.Attachment::getProxyUrl)
					.collect(Collectors.joining("\n"));
		}

		event.deferReply(true)
				.setContent(content)
				.queue();
	}
}
