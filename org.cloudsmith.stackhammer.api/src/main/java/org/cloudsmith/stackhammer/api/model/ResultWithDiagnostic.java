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

package org.cloudsmith.stackhammer.api.model;

import java.io.PrintStream;

public class ResultWithDiagnostic<T> extends Diagnostic {
	private static final long serialVersionUID = 1618870489419505392L;

	private T result;

	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}

	/**
	 * Print the diagnostic children as a log output on the given <code>logger</code>
	 * 
	 * @param logger
	 */
	public void log(PrintStream logger) {
		for(Diagnostic child : getChildren())
			logger.println(child);
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(T result) {
		this.result = result;
	}
}
