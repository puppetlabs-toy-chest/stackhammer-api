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
package org.cloudsmith.hammer.api.client;

import java.lang.reflect.Type;
import java.util.Map;

import org.cloudsmith.hammer.api.util.UrlUtils;

public class StackHammerRequest {

	private String uri;

	private Map<String, String> params;

	private Type type;

	private String responseContentType;

	@Override
	public boolean equals(final Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof StackHammerRequest))
			return false;
		final String fullUri = generateUri();
		final String objUri = ((StackHammerRequest) obj).generateUri();
		return fullUri != null && objUri != null && fullUri.equals(objUri);
	}

	public String generateUri() {
		String baseUri = uri;
		if(baseUri == null)
			return null;

		if(baseUri.indexOf('?') != -1 || params == null || params.isEmpty())
			return baseUri;

		StringBuilder urlBuilder = new StringBuilder(baseUri);
		urlBuilder.append('?');
		UrlUtils.addParameters(params, urlBuilder);
		return urlBuilder.toString();
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	public Type getType() {
		return type;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public int hashCode() {
		final String fullUri = generateUri();
		return fullUri != null
				? fullUri.hashCode()
				: super.hashCode();
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUri(StringBuilder uri) {
		setUri(uri != null
				? uri.toString()
				: null);
	}

	@Override
	public String toString() {
		final String fullUri = generateUri();
		return fullUri != null
				? fullUri
				: super.toString();
	}
}
