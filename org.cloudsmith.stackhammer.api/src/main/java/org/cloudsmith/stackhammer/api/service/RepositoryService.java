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

import org.cloudsmith.stackhammer.api.client.StackHammerClient;
import org.cloudsmith.stackhammer.api.model.Provider;
import org.cloudsmith.stackhammer.api.model.Repository;
import org.cloudsmith.stackhammer.api.model.ResultWithDiagnostic;

public class RepositoryService extends StackHammerService {

	public static class RepositoryResult extends ResultWithDiagnostic<Repository> {
		private static final long serialVersionUID = 6242536629437814049L;
	}

	public RepositoryService(StackHammerClient client) {
		super(client);
	}

	public ResultWithDiagnostic<Repository> cloneRepository(Provider provider, String owner, String name, String branch)
			throws IOException {
		Repository repo = new Repository();
		repo.setName(name);
		repo.setOwner(owner);
		repo.setBranch(branch);
		repo.setProvider(provider);
		return getClient().post(getCommandURI(COMMAND_CLONE), repo, RepositoryResult.class);
	}

	@Override
	protected String getCommandGroup() {
		return COMMAND_GROUP_REPOS;
	}
}
