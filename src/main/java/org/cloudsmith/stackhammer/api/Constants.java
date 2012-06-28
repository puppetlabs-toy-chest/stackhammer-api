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
package org.cloudsmith.stackhammer.api;

import java.nio.charset.Charset;

/**
 * Constants pertaining to the Stack Hammer API
 */
public interface Constants {
	/**
	 * URI path segment used for commands specific to users
	 */
	String COMMAND_GROUP_USERS = "users";

	/**
	 * URI path segment used for commands specific to repositories
	 */
	String COMMAND_GROUP_REPOS = "repos";

	/**
	 * URI path segment used for commands specific to stacks
	 */
	String COMMAND_GROUP_STACKS = "stacks";

	/**
	 * URI path segment used by the clone command
	 */
	String COMMAND_CLONE = "clone";

	/**
	 * URI path segment used by the deploy command
	 */
	String COMMAND_DEPLOY = "deploy";

	/**
	 * URI path segment used by the command to obtain the result of a deploy
	 */
	String COMMAND_DEPLOY_RESULT = "deployResult";

	/**
	 * URI path segment used when polling the status of long running jobs
	 */
	String COMMAND_POLL_JOB = "pollJob";

	/**
	 * URI path segment used by the runTests command
	 */
	String COMMAND_RUN_TESTS = "runTests";

	/**
	 * URI path segment used by the command to obtain the result of a runTests
	 */
	String COMMAND_RUN_TESTS_RESULT = "runTestsResult";

	/**
	 * URI path segment used by the validate command
	 */
	String COMMAND_VALIDATE = "validate";

	/**
	 * Prefix used in the Authentication http header
	 */
	String AUTH_TOKEN = "token"; //$NON-NLS-1$

	/**
	 * Parameter used when polling a job or obtaing the result of a job
	 */
	String PARAM_JOB_IDENTIFIER = "jobIdentifier";

	/**
	 * The content type of posts and responses.
	 */
	String CONTENT_TYPE_JSON = "application/json"; //$NON-NLS-1$

	/**
	 * The encoding used by the API
	 */
	Charset UTF_8 = Charset.forName("UTF-8");
}
