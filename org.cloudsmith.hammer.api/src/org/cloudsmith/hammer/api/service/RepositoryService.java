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
import java.util.HashMap;
import java.util.Map;

import org.cloudsmith.hammer.api.client.StackHammerClient;
import org.cloudsmith.hammer.api.model.Provider;
import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ResultWithDiagnostic;
import org.cloudsmith.hammer.api.util.UrlUtils;

public class RepositoryService extends StackHammerService {

	public static class RepositoryResult extends ResultWithDiagnostic<Repository> {
		private static final long serialVersionUID = 6242536629437814049L;
	}

	public RepositoryService(StackHammerClient client) {
		super(client);
	}

	public ResultWithDiagnostic<Repository> cloneRepository(Provider provider, String owner, String name, String branch)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		addRequiredParam(params, PARAM_PROVIDER, provider.name());
		addRequiredParam(params, PARAM_BRANCH, branch);

		StringBuilder uriBld = new StringBuilder(SEGMENT_REPOS);
		uriBld.append('/');
		uriBld.append(UrlUtils.encode(owner));
		uriBld.append('/');
		uriBld.append(UrlUtils.encode(name));
		uriBld.append('/');
		uriBld.append(COMMAND_CLONE);

		return getClient().post(uriBld.toString(), params, RepositoryResult.class);
	}
}
