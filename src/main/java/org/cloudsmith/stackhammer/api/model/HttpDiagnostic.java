/**
 * Copyright 2013-, Cloudsmith Inc.
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

package org.cloudsmith.stackhammer.api.model;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.stackhammer.api.client.StackHammerClient;

public class HttpDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private int httpCode;

	public HttpDiagnostic() {
		super();
		setType(StackHammerClient.HTTP);
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int code) {
		httpCode = code;
	}
}
