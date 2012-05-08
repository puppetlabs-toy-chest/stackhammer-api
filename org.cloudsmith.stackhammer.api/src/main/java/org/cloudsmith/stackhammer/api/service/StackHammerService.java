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

import java.util.Map;

import org.cloudsmith.stackhammer.api.Constants;
import org.cloudsmith.stackhammer.api.client.StackHammerClient;

public abstract class StackHammerService implements Constants {
	@SuppressWarnings("unchecked")
	protected static <T> void addRequiredParam(Map<String, T> params, String paramName, T paramValue)
			throws IllegalArgumentException {

		if(paramValue instanceof String) {
			String strValue = ((String) paramValue).trim();
			if(strValue.isEmpty())
				strValue = null;

			paramValue = (T) strValue;
		}

		if(paramValue == null)
			throw new IllegalArgumentException("Missing required parameter for key: '" + paramName + '\'');
		params.put(paramName, paramValue);
	}

	private final StackHammerClient client;

	public StackHammerService(StackHammerClient client) {
		this.client = client;
	}

	public StackHammerClient getClient() {
		return client;
	}

	protected abstract String getCommandGroup();

	protected String getCommandURI(String command) {
		return '/' + getCommandGroup() + '/' + command;
	}
}
