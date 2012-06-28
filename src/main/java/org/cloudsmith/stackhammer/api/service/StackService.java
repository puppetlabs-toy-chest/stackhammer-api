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
import java.util.List;

import org.cloudsmith.stackhammer.api.client.StackHammerClient;
import org.cloudsmith.stackhammer.api.model.DeployRequest;
import org.cloudsmith.stackhammer.api.model.DeployResult;
import org.cloudsmith.stackhammer.api.model.Repository;
import org.cloudsmith.stackhammer.api.model.RunTestsRequest;
import org.cloudsmith.stackhammer.api.model.RunTestsResult;
import org.cloudsmith.stackhammer.api.model.StackIdentifier;
import org.cloudsmith.stackhammer.api.model.ValidationResult;

/**
 * A service that provides methods for stack related things.
 */
public class StackService extends StackHammerService {

	/**
	 * Creates a StackService instance that will use the designated client for
	 * its communication with the remote service
	 * 
	 * @param client The client to be used for communicating with the remote service.
	 */
	public StackService(StackHammerClient client) {
		super(client);
	}

	/**
	 * Send a request to start a job that will deploy a stack to the remote service.
	 * 
	 * @param repository The repository containing the stack
	 * @param stackName The name of the stack
	 * @param dryRun Controls the puppet dry-run setting during deploy
	 * @return A job identifier of the deploy job
	 * @throws IOException If the job could not be started due to I/O problems.
	 */
	public String deployStack(Repository repository, String stackName, boolean dryRun) throws IOException {
		StackIdentifier stackIdentifier = new StackIdentifier();
		stackIdentifier.setRepository(repository);
		stackIdentifier.setStackName(stackName);
		return deployStack(stackIdentifier, dryRun);
	}

	/**
	 * Send a request to start a job that will deploy a stack to the remote service.
	 * 
	 * @param stackIdentifier The identifier of the stack
	 * @param dryRun Controls the puppet dry-run setting during deploy
	 * @return A job identifier of the deploy job
	 * @throws IOException If the job could not be started due to I/O problems.
	 */
	public String deployStack(StackIdentifier stackIdentifier, boolean dryRun) throws IOException {
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
	 * Retrieve the result of a deployment job that has completed from the remote service.
	 * 
	 * @param jobIdentifier The identifier of the job.
	 * @return The result of the job
	 */
	public DeployResult getDeploymentResult(String jobIdentifier) throws IOException {
		return getClient().get(
			getCommandURI(COMMAND_DEPLOY_RESULT), Collections.singletonMap(PARAM_JOB_IDENTIFIER, jobIdentifier),
			DeployResult.class);
	}

	/**
	 * Retrieve the result of a test run that has completed from the remote service.
	 * 
	 * @param jobIdentifier The identifier of the job.
	 * @return The result of the job
	 */
	public RunTestsResult getRunTestsResult(String jobIdentifier) throws IOException {
		return getClient().get(
			getCommandURI(COMMAND_RUN_TESTS_RESULT), Collections.singletonMap(PARAM_JOB_IDENTIFIER, jobIdentifier),
			RunTestsResult.class);
	}

	/**
	 * Send a request to run stack tests to the remote service.
	 * 
	 * @param repository The repository containing the stack.
	 * @param stackName The name of the stack.
	 * @param testNames The names of the tests to run. Leave empty to run all tests
	 * @param undeploy Set to <code>true</code> if the test harness stack should be
	 *        undeployed when the run is complete
	 * @return A job identifier of the job that performs the test run
	 * @throws IOException If the run could not be performed due to I/O problems.
	 */
	public String runTests(Repository repository, String stackName, List<String> testNames, boolean undeploy)
			throws IOException {
		StackIdentifier stackId = new StackIdentifier();
		stackId.setRepository(repository);
		stackId.setStackName(stackName);
		RunTestsRequest request = new RunTestsRequest();
		request.setStackIdentifier(stackId);
		request.setTestNames(testNames);
		return getClient().post(getCommandURI(COMMAND_RUN_TESTS), request, String.class);
	}

	/**
	 * Send a request to validate a stack to the remote service.
	 * 
	 * @param repository The repository containing the stack.
	 * @param stackName The name of the stack.
	 * @return The result of the validation.
	 * @throws IOException If the validation could not be performed due to I/O problems.
	 */
	public ValidationResult validateStack(Repository repository, String stackName) throws IOException {
		StackIdentifier stackId = new StackIdentifier();
		stackId.setRepository(repository);
		stackId.setStackName(stackName);
		return getClient().post(getCommandURI(COMMAND_VALIDATE), stackId, ValidationResult.class);
	}
}
