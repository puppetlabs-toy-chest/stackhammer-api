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
package org.cloudsmith.hammer.api.service;

import java.io.IOException;

import org.cloudsmith.hammer.api.client.StackHammerClient;
import org.cloudsmith.hammer.api.model.Provider;
import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ResultWithDiagnostic;

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
