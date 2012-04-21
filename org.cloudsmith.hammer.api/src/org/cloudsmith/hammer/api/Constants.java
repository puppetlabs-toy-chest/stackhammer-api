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

package org.cloudsmith.hammer.api;

import java.nio.charset.Charset;

public interface Constants {
	String SEGMENT_USERS = "/users";

	String SEGMENT_REPOS = "/repos";

	String SEGMENT_STACKS = "/stacks";

	String COMMAND_CLONE = "clone";

	String COMMAND_VALIDATE = "validate";

	String HEADER_CONTENT_TYPE = "Content-Type";

	String HEADER_ACCEPT = "Accept";

	String HEADER_AUTHORIZATION = "Authorization";

	String HEADER_USER_AGENT = "User-Agent";

	String AUTH_TOKEN = "token"; //$NON-NLS-1$

	String PARAM_PROVIDER = "provider";

	String PARAM_BRANCH = "branch";

	String CONTENT_TYPE_JSON = "application/json"; //$NON-NLS-1$

	Charset UTF_8 = Charset.forName("UTF-8");
}
