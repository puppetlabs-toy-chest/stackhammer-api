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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diagnostic implements Serializable {
	public static final int ERROR = 3;

	public static final int WARNING = 2;

	public static final int INFO = 1;

	public static final int OK = 0;

	private static final String[] severityStrings = new String[] { "OK", "INFO", "WARNING", "ERROR" };

	private static final long serialVersionUID = 4456459592694401491L;

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

	private int httpCode = 0;

	private String message;

	private String source;

	private String resourcePath;

	private String locationLabel;

	private String issue;

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
				: Collections.unmodifiableList(children);
	}

	public int getHttpCode() {
		if(httpCode != 0)
			return httpCode;

		return getSeverity() == ERROR
				? 202
				: 200;
	}

	/**
	 * The issue is a String naming a particular issue that makes it possible to have a more detailed understanding of an error
	 * and what could be done to repair it. (As opposed to parsing the error message to gain an understanding). Error messages may
	 * be subject to NLS translation, but never the issue.
	 * 
	 * @return the issue
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * The location in a resource formatted as LINE(OFFSET,LENGTH), or if LINE is -1 using '-'. If offset is >= 0 the () section
	 * is included, the ", length" part is only produced if length >= 0
	 * 
	 * @return the locationLabel
	 */
	public String getLocationLabel() {
		return locationLabel;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * ResourcePath is a reference to a relative or absolute file, or is empty/null if the diagnostic is not file related.
	 * 
	 * All diagnostic relating to files given (directly or contained) in the calls to the ValidationService will be reported with
	 * path's relative to the given root, or in the case of a single file, the leaf part of the path (the file name). Diagnostics
	 * relating to absolute files may appear - these may refer to files that are used by a particular diagnostician (e.g. system
	 * libraries or general configuration files).
	 * 
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * This method is needed by net.sf.json but should not otherwise
	 * be used.
	 * 
	 * @param children The new children to set
	 * @see #addChild(Diagnostic)
	 * @see #addChildren(Collection)
	 */
	public void setChildren(List<Diagnostic> children) {
		this.children = null;
		addChildren(children);
	}

	public void setHttpCode(int code) {
		this.httpCode = code;
	}

	/**
	 * @param issue the issue to set
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/**
	 * @param locationLabel the locationLabel to set
	 */
	public void setLocationLabel(String locationLabel) {
		this.locationLabel = locationLabel;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param resourcePath the resourcePath to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld, 0);
		return bld.toString();
	}

	public void toString(StringBuilder bld, int indent) {
		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		if(message == null && resourcePath == null && locationLabel == null) {
			if(children == null) {
				bld.append(getSeverityString(severity));
				return;
			}
		}
		else {
			bld.append(getSeverityString(severity));
			bld.append(':');

			if(resourcePath != null) {
				bld.append(resourcePath);
				bld.append(':');
			}
			if(locationLabel != null) {
				bld.append(locationLabel);
				bld.append(':');
			}
			if(message != null) {
				bld.append(message);
				bld.append(':');
			}

			bld.setLength(bld.length() - 1);
			if(children != null) {
				bld.append('\n');
				indent += 4;
			}
		}

		if(children != null) {
			int top = children.size();
			int idx = 0;
			for(;;) {
				children.get(idx).toString(bld, indent);
				if(++idx >= top)
					break;
				bld.append('\n');
			}
		}
	}
}
