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

import java.io.IOException;
import java.util.Map;

import org.cloudsmith.stackhammer.api.Constants;
import org.cloudsmith.stackhammer.api.client.StackHammerClient;
import org.cloudsmith.stackhammer.api.model.PollResult;

/**
 * Abstract base class for all specialized services.
 */
public abstract class StackHammerService implements Constants {
	@SuppressWarnings("unchecked")
	protected static <T> void addRequiredParam(Map<String, T> params, String paramName, T paramValue)
			throws IllegalArgumentException {

		if(paramValue instanceof String) {
			String strValue = ((String) paramValue).trim();
			if(strValue.isEmpty())
				strValue = null;

			paramValue = (T) strValue;
		}

		if(paramValue == null)
			throw new IllegalArgumentException("Missing required parameter for key: '" + paramName + '\'');
		params.put(paramName, paramValue);
	}

	private final StackHammerClient client;

	public StackHammerService(StackHammerClient client) {
		this.client = client;
	}

	/**
	 * Returns the client used for communication with the remote service
	 * 
	 * @return The client.
	 */
	public StackHammerClient getClient() {
		return client;
	}

	/**
	 * Returns the command group URL segment used by this service
	 */
	protected abstract String getCommandGroup();

	/**
	 * Creates the command path
	 * 
	 * @param command The command to create a path for
	 * @return The path
	 */
	protected String getCommandURI(String command) {
		return '/' + getCommandGroup() + '/' + command;
	}

	/**
	 * Perfoms a poll of a job that was previously started by a method that returned a job
	 * identifier.
	 * 
	 * @param jobIdentifier The identifier for the job
	 * @return The current status as reported by the remote service.
	 * @throws IOException If some I/O problems occured during the poll.
	 */
	public PollResult pollJob(String jobIdentifier) throws IOException {
		return getClient().get('/' + COMMAND_POLL_JOB + '/' + jobIdentifier, null, PollResult.class);
	}
}
