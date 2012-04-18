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
import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ValidationResult;

public class StackService extends StackHammerService {

	public StackService(StackHammerClient client) {
		super(client);
	}

	public ValidationResult validateStack(Repository repository, String stackName) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		addRequiredParam(params, "repository", repository);
		addRequiredParam(params, "stackName", stackName);

		return getClient().post("/user/stacks", params, Repository.class);
	}
}
