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

package io.github.iwyfewwnt.n__u.prelude.event;

import io.github.iwyfewwnt.n__u.prelude.bean.DiscordBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class DiscordEventConfiguration {
	private final ApplicationEventMulticaster applicationEventMulticaster;

	public DiscordEventConfiguration() {
		SimpleApplicationEventMulticaster simpleApplicationEventMulticaster
				= new SimpleApplicationEventMulticaster();

		simpleApplicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());

		this.applicationEventMulticaster = simpleApplicationEventMulticaster;
	}

	@Bean(DiscordBean.APPLICATION_EVENT_MULTICASTER)
	public ApplicationEventMulticaster getApplicationEventMulticaster() {
		return this.applicationEventMulticaster;
	}
}
