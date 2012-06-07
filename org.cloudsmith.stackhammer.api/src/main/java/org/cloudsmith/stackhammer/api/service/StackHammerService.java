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

	public StackHammerClient getClient() {
		return client;
	}

	protected abstract String getCommandGroup();

	protected String getCommandURI(String command) {
		return '/' + getCommandGroup() + '/' + command;
	}

	public PollResult pollJob(String jobIdentifier) throws IOException {
		return getClient().get('/' + COMMAND_POLL_JOB + '/' + jobIdentifier, null, PollResult.class);
	}
}
