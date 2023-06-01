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

package io.github.iwyfewwnt.n__u.event;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public final class DiscordMessageReceivedEventListener {
	private static final int MESSAGE_QUEUE_RESET_DELAY = 7;
	private static final TimeUnit MESSAGE_QUEUE_RESET_DELAY_TIME_UNIT = TimeUnit.SECONDS;

	private ScheduledFuture<?> lastScheduledFuture;

	public DiscordMessageReceivedEventListener() {
		this.lastScheduledFuture = null;
	}

	@EventListener(
			value = MessageReceivedEvent.class,
			condition = "!#event.author.system && !#event.author.bot && #event.channel.canTalk()"
	)
	public void onDiscordMessageReceivedEvent(MessageReceivedEvent event) {
		MessageChannel messageChannel = event.getChannel();

		if (this.lastScheduledFuture != null
				&& !this.lastScheduledFuture.isDone()) {
			this.lastScheduledFuture.cancel(true);
		}

		this.lastScheduledFuture = messageChannel.sendMessage("Talk to me! Uhm, I'm waiting for you\">//<")
				.queueAfter(MESSAGE_QUEUE_RESET_DELAY, MESSAGE_QUEUE_RESET_DELAY_TIME_UNIT);
	}
}
