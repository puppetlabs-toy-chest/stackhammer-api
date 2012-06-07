/**
 * Copyright 2012-, Cloudsmith Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.cloudsmith.stackhammer.api.service;

import org.cloudsmith.stackhammer.api.client.StackHammerClient;

import com.google.inject.Inject;

/**
 * A Factory capable of creating the specialized services used when communicating
 * with the Stack Hammer service
 */
public class StackHammerFactory {
	@Inject
	private StackHammerClient client;

	/**
	 * Creates a service that can be used when performing repository specific
	 * actions.
	 * 
	 * @return A repository service.
	 */
	public RepositoryService createRepositoryService() {
		return new RepositoryService(client);
	}

	/**
	 * Creates a service that can be used when performing stack specific
	 * actions.
	 * 
	 * @return A stack service.
	 */
	public StackService createStackService() {
		return new StackService(client);
	}
}
