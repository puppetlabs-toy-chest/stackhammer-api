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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.cloudsmith.stackhammer.api.Constants;
import org.cloudsmith.stackhammer.api.model.Diagnostic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Class responsible for all request and response processing
 */
public class StackHammerClient implements Constants {
	private static final String USER_AGENT = "StackHammerJava/1.0.0"; //$NON-NLS-1$

	private final static Charset UTF_8 = Charset.forName("UTF-8");

	private static final GsonBuilder gsonBuilder;

	static {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
	}

	private static InputStream getStream(HttpEntity entity) throws IOException {
		if(entity == null)
			return null;

		return entity.getContent();
	}

	/**
	 * Does status code denote an error
	 * 
	 * @param code
	 * @return true if error, false otherwise
	 */
	private static boolean isError(final int code) {
		switch(code) {
			case HttpStatus.SC_BAD_REQUEST:
			case HttpStatus.SC_UNAUTHORIZED:
			case HttpStatus.SC_FORBIDDEN:
			case HttpStatus.SC_NOT_FOUND:
			case HttpStatus.SC_CONFLICT:
			case HttpStatus.SC_GONE:
			case HttpStatus.SC_UNPROCESSABLE_ENTITY:
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				return true;
			default:
				return false;
		}
	}

	private final String baseUri;

	private final Gson gson = gsonBuilder.create();

	private final String credentials;

	private String userAgent;

	private int bufferSize = 8192;

	private final HttpClient httpClient;

	@Inject
	public StackHammerClient(@Named("StackHammer baseUri") String baseUri,
			@Named("StackHammer credentials") String credentials) {
		this.baseUri = baseUri;
		this.credentials = credentials == null
				? null
				: (AUTH_TOKEN + ' ' + credentials);

		userAgent = USER_AGENT;
		httpClient = new DefaultHttpClient();
		try {
			httpClient.getConnectionManager().getSchemeRegistry().register(
				new Scheme("https", 443, new SSLSocketFactory(new TrustSelfSignedStrategy())));
		}
		catch(Exception e) {
			// let's try without that ...
		}
	}

	private void assignJSONContent(HttpEntityEnclosingRequestBase request, Object params) {
		if(params != null) {
			request.addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON + "; charset=" + UTF_8.name()); //$NON-NLS-1$
			byte[] data = toJson(params).getBytes(UTF_8);
			request.setEntity(new ByteArrayEntity(data));
		}
	}

	protected void configureRequest(final HttpRequestBase request) {
		if(credentials != null)
			request.addHeader(HttpHeaders.AUTHORIZATION, credentials);
		request.addHeader(HttpHeaders.USER_AGENT, userAgent);
		request.addHeader(HttpHeaders.ACCEPT, "application/vnd.stackhammer.beta+json"); //$NON-NLS-1$
	}

	/**
	 * Create error exception from response and throw it
	 * 
	 * @param response
	 * @param code
	 * @param status
	 * @return non-null newly created {@link IOException}
	 */
	protected IOException createException(InputStream response, int code, String status) {
		if(isError(code)) {
			final Diagnostic error;
			try {
				error = parseError(code, response);
			}
			catch(IOException e) {
				return e;
			}
			if(error != null)
				return new RequestException(error, code);
		}
		String message;
		if(status != null && status.length() > 0)
			message = status + " (" + code + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		else
			message = "Unknown error occurred (" + code + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		return new IOException(message);
	}

	/**
	 * Create full URI from path
	 * 
	 * @param path
	 * @return uri
	 */
	protected String createUri(final String path) {
		return baseUri + path;
	}

	private <V> V executeRequest(final HttpRequestBase request, final Class<V> type) throws IOException {

		ResponseHandler<V> responseHandler = new ResponseHandler<V>() {
			@Override
			public V handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();
				int code = statusLine.getStatusCode();
				if(code >= 300)
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());

				HttpEntity entity = response.getEntity();
				if(isOk(code))
					return parseJson(getStream(entity), type);

				throw createException(getStream(entity), code, statusLine.getReasonPhrase());
			}
		};

		return httpClient.execute(request, responseHandler);
	}

	/**
	 * Executes a HTTP GET request. The http response is expected to be a JSON representation of
	 * an object of the specified <code>type</code>. The object is parsed and returned.
	 * 
	 * @param urlStr The URL of the request
	 * @param params Parameters to include in the URL
	 * @param type The expected type of the result
	 * @return An object of the expected type
	 * @throws IOException if the request could not be completed
	 */
	public <V> V get(String urlStr, Map<String, String> params, Class<V> type) throws IOException {
		URI uri;
		try {
			uri = new URI(createUri(urlStr));
			if(params != null && !params.isEmpty()) {
				List<NameValuePair> queryParams = new ArrayList<NameValuePair>(params.size());
				for(Map.Entry<String, String> param : params.entrySet())
					queryParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));

				uri = URIUtils.createURI(
					uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath(),
					URLEncodedUtils.format(queryParams, UTF_8.name()), uri.getFragment());
			}
		}
		catch(URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}

		HttpGet request = new HttpGet(uri);
		configureRequest(request);
		return executeRequest(request, type);
	}

	/**
	 * Does status code denote a non-error response?
	 * 
	 * @param code
	 * @return true if okay, false otherwise
	 */
	protected boolean isOk(final int code) {
		switch(code) {
			case HttpStatus.SC_OK:
			case HttpStatus.SC_CREATED:
			case HttpStatus.SC_ACCEPTED:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Parse error from response
	 * 
	 * @param response
	 * @return request error
	 * @throws IOException
	 */
	protected Diagnostic parseError(int code, InputStream response) throws IOException {
		Reader reader = new InputStreamReader(response, UTF_8);
		StringBuilder bld = new StringBuilder();
		char[] buffer = new char[1024];
		int cnt;
		while((cnt = reader.read(buffer)) > 0)
			bld.append(buffer, 0, cnt);
		Diagnostic diag = new Diagnostic();
		diag.setMessage(bld.toString());
		diag.setSeverity(Diagnostic.ERROR);
		diag.setHttpCode(code);
		return diag;
	}

	/**
	 * Parse JSON to specified type
	 * 
	 * @param <V>
	 * @param stream
	 * @param type
	 * @return parsed type
	 * @throws IOException
	 */
	protected <V> V parseJson(InputStream stream, Class<V> type) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, UTF_8), bufferSize);
		try {
			return gson.fromJson(reader, type);
		}
		catch(JsonSyntaxException jpe) {
			IOException ioe = new IOException("Parse exception converting JSON to object"); //$NON-NLS-1$
			ioe.initCause(jpe);
			throw ioe;
		}
		finally {
			try {
				reader.close();
			}
			catch(IOException ignored) {
				// Ignored
			}
		}
	}

	/**
	 * Post to URI
	 * 
	 * @param uri
	 * @throws IOException
	 */
	public void post(String uri) throws IOException {
		post(uri, null, null);
	}

	/**
	 * Post data to URI
	 * 
	 * @param <V>
	 * @param uri
	 * @param params
	 * @param type
	 * @return response
	 * @throws IOException
	 */
	public <V> V post(final String uri, final Object params, final Class<V> type) throws IOException {
		HttpPost request = new HttpPost(createUri(uri));
		configureRequest(request);
		assignJSONContent(request, params);
		return executeRequest(request, type);
	}

	/**
	 * Put to URI
	 * 
	 * @param uri
	 * @throws IOException
	 */
	public void put(String uri) throws IOException {
		put(uri, null, null);
	}

	/**
	 * Put data to URI
	 * 
	 * @param <V>
	 * @param uri
	 * @param params
	 * @param type
	 * @return response
	 * @throws IOException
	 */
	public <V> V put(final String uri, final Object params, final Class<V> type) throws IOException {
		HttpPut request = new HttpPut(createUri(uri));
		configureRequest(request);
		assignJSONContent(request, params);
		return executeRequest(request, type);
	}

	/**
	 * Set buffer size used to send the request and read the response
	 * 
	 * @param bufferSize
	 * @return this client
	 */
	public StackHammerClient setBufferSize(int bufferSize) {
		if(bufferSize < 1)
			throw new IllegalArgumentException("Buffer size must be greater than zero"); //$NON-NLS-1$

		this.bufferSize = bufferSize;
		return this;
	}

	/**
	 * Set the user agent to use for all subsequent requests performed by this client.
	 * 
	 * @param agent The agent or <code>null</code> to use the default agent.
	 * @return this client
	 */
	public StackHammerClient setUserAgent(final String agent) {
		if(agent != null && agent.length() > 0)
			userAgent = agent;
		else
			userAgent = USER_AGENT;
		return this;
	}

	/**
	 * Convert object to a JSON string
	 * 
	 * @param object
	 * @return JSON string
	 * @throws IOException
	 */
	protected String toJson(Object object) {
		return gson.toJson(object);
	}
}
