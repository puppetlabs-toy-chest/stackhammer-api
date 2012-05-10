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

package org.cloudsmith.stackhammer.api;

import java.nio.charset.Charset;

/**
 * Constants pertaining to the Stack Hammer API
 */
public interface Constants {
	String COMMAND_GROUP_USERS = "users";

	String COMMAND_GROUP_REPOS = "repos";

	String COMMAND_GROUP_STACKS = "stacks";

	String COMMAND_CLONE = "clone";

	String COMMAND_DEPLOY = "deploy";

	String COMMAND_VALIDATE = "validate";

	String AUTH_TOKEN = "token"; //$NON-NLS-1$

	String PARAM_NAME = "name";

	String PARAM_REPOSITORY = "repository";

	String PARAM_PROVIDER = "provider";

	String CONTENT_TYPE_JSON = "application/json"; //$NON-NLS-1$

	Charset UTF_8 = Charset.forName("UTF-8");
}
