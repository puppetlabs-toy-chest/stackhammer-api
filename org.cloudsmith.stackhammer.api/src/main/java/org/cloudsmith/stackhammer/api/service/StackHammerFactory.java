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

import org.cloudsmith.stackhammer.api.client.StackHammerClient;

import com.google.inject.Inject;

public class StackHammerFactory {
	@Inject
	private StackHammerClient client;

	public RepositoryService createRepositoryService() {
		return new RepositoryService(client);
	}

	public StackService createStackService() {
		return new StackService(client);
	}
}
