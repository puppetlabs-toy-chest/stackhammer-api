/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Thomas Hallgren (Cloudsmith Inc.) - initial API and implementation
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
