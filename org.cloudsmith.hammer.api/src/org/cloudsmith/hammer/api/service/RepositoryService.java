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

public class RepositoryService extends StackHammerService {

	public RepositoryService(StackHammerClient client) {
		super(client);
	}

	public Repository cloneRepository(Provider provider, String owner, String name, String branch) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		addRequiredParam(params, "provider", provider);
		addRequiredParam(params, "owner", owner);
		addRequiredParam(params, "name", name);
		addRequiredParam(params, "branch", branch);

		return getClient().post("/user/repos", params, Repository.class);
	}
}
