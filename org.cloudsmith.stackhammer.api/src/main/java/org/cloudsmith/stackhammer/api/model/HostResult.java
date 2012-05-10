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

import java.io.Serializable;
import java.util.List;

/**
 * The HostResult represents the result of deploying a host. It contains
 * a list of {@link LogEntry}s and a base-64 encoded string representing
 * the bytes of an SVG image which in turn represents the catalog graph.
 */
public class HostResult implements Serializable {
	private static final long serialVersionUID = 5189972109353893244L;

	private String catalogGraph;

	private List<LogEntry> logEntries;

	/**
	 * @return the catalogGraph
	 */
	public String getCatalogGraph() {
		return catalogGraph;
	}

	/**
	 * @return the logEntries
	 */
	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

	/**
	 * @param catalogGraph the catalogGraph to set
	 */
	public void setCatalogGraph(String catalogGraph) {
		this.catalogGraph = catalogGraph;
	}

	/**
	 * @param logEntries the logEntries to set
	 */
	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}
}
