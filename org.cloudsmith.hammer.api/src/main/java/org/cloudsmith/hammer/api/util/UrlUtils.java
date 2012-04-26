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
package org.cloudsmith.hammer.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.cloudsmith.hammer.api.client.StackHammerConnection;
import org.cloudsmith.hammer.api.client.StackHammerConnectionFactory;

public abstract class UrlUtils {
	public static class UrlConnection implements StackHammerConnection {
		private final HttpURLConnection conn;

		UrlConnection(HttpURLConnection conn) {
			this.conn = conn;
		}

		@Override
		public InputStream getErrorStream() {
			return conn.getErrorStream();
		}

		@Override
		public String getHeaderField(String name) {
			return conn.getHeaderField(name);
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return conn.getInputStream();
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return conn.getOutputStream();
		}

		@Override
		public int getResponseCode() throws IOException {
			return conn.getResponseCode();
		}

		@Override
		public String getResponseMessage() throws IOException {
			return conn.getResponseMessage();
		}

		@Override
		public URL getURL() {
			return conn.getURL();
		}

		@Override
		public void setDoOutput(boolean flag) {
			conn.setDoOutput(flag);
		}

		@Override
		public void setFixedLengthStreamingMode(int length) {
			conn.setFixedLengthStreamingMode(length);
		}

		@Override
		public void setRequestMethod(String method) throws ProtocolException {
			conn.setRequestMethod(method);
		}

		@Override
		public void setRequestProperty(String key, String value) {
			conn.setRequestProperty(key, value);
		}
	}

	public static class UrlConnectionFactory implements StackHammerConnectionFactory {
		@Override
		public StackHammerConnection createConnection(URL url) throws IOException {
			return new UrlConnection((HttpURLConnection) url.openConnection());
		}
	}

	private static final String QUERY_ENCODING = "UTF-8";

	public static void addParameter(String name, String value, StringBuilder uri) {
		if(uri.length() > 0)
			uri.append('&');
		uri.append(encode(name));
		if(value != null) {
			uri.append('=');
			uri.append(encode(value));
		}
	}

	public static void addParameters(Map<String, String> parameters, StringBuilder uri) {
		if(parameters == null || parameters.isEmpty())
			return;
		for(Entry<String, String> param : parameters.entrySet())
			addParameter(param.getKey(), param.getValue(), uri);
	}

	public static String decode(String value) {
		try {
			return URLDecoder.decode(value, QUERY_ENCODING);
		}
		catch(UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String encode(String value) {
		try {
			return URLEncoder.encode(value, QUERY_ENCODING);
		}
		catch(UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Map<String, String> getParameters(URI uri) {
		return parseQuery(uri.getRawQuery());
	}

	private static void parsePair(Map<String, String> result, String pair) {
		int sep = pair.indexOf('=');
		if(sep < 0)
			result.put(decode(pair), null);
		else
			result.put(decode(pair.substring(0, sep)), decode(pair.substring(sep + 1)));
	}

	public static Map<String, String> parseQuery(String query) {
		if(query == null || query.isEmpty())
			return Collections.emptyMap();

		Map<String, String> result = new HashMap<String, String>();
		int top = query.length();
		int start = 0;
		for(int idx = 0; idx < top; ++idx) {
			char c = query.charAt(idx);
			if(c == '&') {
				parsePair(result, query.substring(start, idx));
				start = idx + 1;
			}
		}
		if(start < top)
			parsePair(result, query.substring(start));
		return result;
	}
}
