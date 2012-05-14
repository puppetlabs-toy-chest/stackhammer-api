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

import static java.lang.String.format;

import java.io.Serializable;

public class MessageWithSeverity implements Serializable {
	private static final long serialVersionUID = 3125174260505451357L;

	public static final int FATAL = 5;

	public static final int ERROR = 4;

	public static final int WARNING = 3;

	public static final int INFO = 2;

	public static final int DEBUG = 1;

	public static final int OK = 0;

	private static final String[] severityStrings = new String[] { "OK", "DEBUG", "INFO", "WARNING", "ERROR", "FATAL" };

	/**
	 * Return the severity as a string. The string &quot;UNKNOWN(&lt;severity&gt;)&quot; will
	 * be returned if the argument represents an unknown severity.
	 * 
	 * @param severity
	 * @return A string representing the severity
	 */
	public static String getSeverityString(int severity) {
		return severity >= 0 && severity < severityStrings.length
				? severityStrings[severity]
				: format("UNKNOWN(%d)", severity);
	}

	private int severity;

	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

	public String getSeverityString() {
		return getSeverityString(getSeverity());
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public final String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld, 0);
		return bld.toString();
	}

	public void toString(StringBuilder bld, int indent) {
		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		bld.append(getSeverityString(severity));
		if(message != null) {
			bld.append(':');
			bld.append(message);
		}
	}
}
