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
package org.cloudsmith.hammer.api.client;

import java.io.IOException;

import org.cloudsmith.hammer.api.model.Diagnostic;

/**
 * This exception is thrown when the server encounters abnormal behavior such
 * as internal server errors.
 */
public class RequestException extends IOException {
	private static final long serialVersionUID = 3014441430950697818L;

	private final Diagnostic error;

	private final int code;

	public RequestException(Diagnostic error, int code) {
		super();
		this.error = error;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public Diagnostic getError() {
		return error;
	}

	@Override
	public String getMessage() {
		return error.toString();
	}
}
