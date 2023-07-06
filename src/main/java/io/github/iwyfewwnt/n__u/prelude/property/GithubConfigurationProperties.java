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

package io.github.iwyfewwnt.n__u.prelude.property;

import io.github.iwyfewwnt.n__u.prelude.bean.LocalBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties("github")
public final class GithubConfigurationProperties {

	@NestedConfigurationProperty
	private final GithubRepositoryConfigurationProperties repository;

	public GithubConfigurationProperties(GithubRepositoryConfigurationProperties repository) {
		this.repository = repository;
	}

	@Bean(LocalBean.GITHUB_REPOSITORY_CONFIGURATION_PROPERTIES)
	public GithubRepositoryConfigurationProperties getRepository() {
		return this.repository;
	}

	@Bean(LocalBean.GITHUB_REPOSITORY_URL_CONFIGURATION_PROPERTY)
	public String getRepositoryUrl() {
		return repository.getUrl();
	}
}
