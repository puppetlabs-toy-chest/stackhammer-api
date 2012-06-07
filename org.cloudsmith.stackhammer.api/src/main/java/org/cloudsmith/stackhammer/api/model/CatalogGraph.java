/**
 * Copyright 2012-, Cloudsmith Inc.
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
