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

/**
 * The CatalogGraph represents the result of deploying a host. It contains
 * a base-64 encoded string representing the bytes of an SVG image which in turn
 * represents the graph.
 */
public class CatalogGraph implements Serializable {
	private static final long serialVersionUID = 5189972109353893244L;

	private String nodeName;

	private String instanceID;

	private String catalogGraph;

	/**
	 * @return the catalogGraph
	 */
	public String getCatalogGraph() {
		return catalogGraph;
	}

	public String getInstanceID() {
		return instanceID;
	}

	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param catalogGraph the catalogGraph to set
	 */
	public void setCatalogGraph(String catalogGraph) {
		this.catalogGraph = catalogGraph;
	}

	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
