/**
 * This code was initially copied from the egit-github project
 * 
 *  http://git.eclipse.org/c/egit/egit-github.git/
 * 
 * and then adjusted for this API. The original contained this copyright:
 *
 *  Copyright (c) 2011 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Kevin Sawicki (GitHub Inc.) - initial API and implementation
 *   Thomas Hallgren (Cloudsmith Inc.) - Stackhammer changes
 */
package org.cloudsmith.stackhammer.api.client;

public class StackHammerResponse<V> {

	private final StackHammerConnection response;

	private final V body;

	public StackHammerResponse(StackHammerConnection response, V body) {
		this.response = response;
		this.body = body;
	}

	public V getBody() {
		return body;
	}

	public String getHeader(String name) {
		return response.getHeaderField(name);
	}
}
