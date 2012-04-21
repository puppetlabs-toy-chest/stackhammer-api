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

package org.cloudsmith.hammer.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diagnostic implements Serializable {
	public static final int ERROR = 3;

	public static final int WARNING = 2;

	public static final int INFO = 1;

	public static final int OK = 0;

	private static final long serialVersionUID = 4456459592694401491L;

	private int severity;

	private int httpCode = 0;

	private String message;

	private String link;

	private List<Diagnostic> children;

	public void addChild(Diagnostic child) {
		if(severity < child.getSeverity())
			severity = child.getSeverity();
		if(children == null)
			children = new ArrayList<Diagnostic>();
		children.add(child);
	}

	public void addChildren(Collection<Diagnostic> children) {
		for(Diagnostic child : children)
			addChild(child);
	}

	public List<Diagnostic> getChildren() {
		return children == null
				? Collections.<Diagnostic> emptyList()
				: children;
	}

	public int getHttpCode() {
		if(httpCode != 0)
			return httpCode;

		return getSeverity() == ERROR
				? 202
				: 200;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

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

	public void setHttpCode(int code) {
		this.httpCode = code;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
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
}
