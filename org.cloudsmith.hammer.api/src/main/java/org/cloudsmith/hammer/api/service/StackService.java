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
import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ValidationRequest;
import org.cloudsmith.hammer.api.model.ValidationResult;
import org.cloudsmith.hammer.api.model.ValidationType;

public class StackService extends StackHammerService {

	public StackService(StackHammerClient client) {
		super(client);
	}

	@Override
	protected String getCommandGroup() {
		return COMMAND_GROUP_STACKS;
	}

	public ValidationResult validateStack(Repository repository, String stackName) throws IOException {
		ValidationRequest request = new ValidationRequest();
		request.setRepository(repository);
		request.setStackName(stackName);
		request.setValidationType(ValidationType.SEMANTIC);
		return getClient().post(getCommandURI(COMMAND_VALIDATE), request, ValidationResult.class);
	}
}
