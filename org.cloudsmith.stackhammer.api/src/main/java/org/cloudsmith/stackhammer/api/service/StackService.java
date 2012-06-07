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
import java.util.Collections;

import org.cloudsmith.stackhammer.api.client.StackHammerClient;
import org.cloudsmith.stackhammer.api.model.DeployRequest;
import org.cloudsmith.stackhammer.api.model.DeployResult;
import org.cloudsmith.stackhammer.api.model.Repository;
import org.cloudsmith.stackhammer.api.model.StackIdentifier;
import org.cloudsmith.stackhammer.api.model.ValidationResult;

public class StackService extends StackHammerService {

	public StackService(StackHammerClient client) {
		super(client);
	}

	public String deployStack(Repository repository, String stackName, boolean dryRun) throws IOException {
		StackIdentifier stackIdentifier = new StackIdentifier();
		stackIdentifier.setRepository(repository);
		stackIdentifier.setStackName(stackName);
		DeployRequest request = new DeployRequest();
		request.setStackIdentifier(stackIdentifier);
		request.setDryRun(dryRun);
		return getClient().post(getCommandURI(COMMAND_DEPLOY), request, String.class);
	}

	@Override
	protected String getCommandGroup() {
		return COMMAND_GROUP_STACKS;
	}

	/**
	 * @param jobIdentifier
	 * @return
	 */
	public DeployResult getDeploymentResult(String jobIdentifier) throws IOException {
		return getClient().get(
			getCommandURI(COMMAND_DEPLOY_RESULT), Collections.singletonMap(PARAM_JOB_IDENTIFIER, jobIdentifier),
			DeployResult.class);
	}

	public ValidationResult validateStack(Repository repository, String stackName) throws IOException {
		StackIdentifier request = new StackIdentifier();
		request.setRepository(repository);
		request.setStackName(stackName);
		return getClient().post(getCommandURI(COMMAND_VALIDATE), request, ValidationResult.class);
	}
}
