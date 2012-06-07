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
package org.cloudsmith.stackhammer.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class UrlUtils {
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
