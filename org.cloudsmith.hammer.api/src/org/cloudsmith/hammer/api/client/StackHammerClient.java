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

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_GONE;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;

import org.cloudsmith.hammer.api.Constants;
import org.cloudsmith.hammer.api.json.JSONAdapter;
import org.cloudsmith.hammer.api.json.JSONException;
import org.cloudsmith.hammer.api.model.Diagnostic;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class StackHammerClient implements Constants {
	private static final String METHOD_GET = "GET"; //$NON-NLS-1$

	private static final String METHOD_PUT = "PUT"; //$NON-NLS-1$

	private static final String METHOD_POST = "POST"; //$NON-NLS-1$

	private static final String METHOD_DELETE = "DELETE"; //$NON-NLS-1$

	private static final String USER_AGENT = "StackHammerJava/1.0.0"; //$NON-NLS-1$

	private static final int HTTP_UNPROCESSABLE_ENTITY = 422;

	private final static Charset UTF_8 = Charset.forName("UTF-8");

	private final String baseUri;

	private final JSONAdapter jsonAdapter;

	private final StackHammerConnectionFactory connectionFactory;

	private final String user;

	private final String credentials;

	private String userAgent;

	private int bufferSize = 8192;

	@Inject
	public StackHammerClient(
		// @fmtOff
		@Named("StackHammer baseUri") String baseUri,
		@Named("StackHammer user") String user,
		@Named("StackHammer credentials") String credentials,
		JSONAdapter jsonAdapter,
		StackHammerConnectionFactory connectionFactory)	{
		// @fmtOn
		this.baseUri = baseUri;
		this.user = user;
		this.credentials = credentials == null
				? null
				: (AUTH_TOKEN + ' ' + credentials);
		this.jsonAdapter = jsonAdapter;
		this.connectionFactory = connectionFactory;
	}

	protected StackHammerConnection configureRequest(final StackHammerConnection request) {
		if(credentials != null)
			request.setRequestProperty(HEADER_AUTHORIZATION, credentials);
		request.setRequestProperty(HEADER_USER_AGENT, userAgent);
		request.setRequestProperty(HEADER_ACCEPT, "application/vnd.stackhammer.beta+json"); //$NON-NLS-1$
		return request;
	}

	/**
	 * Create connection to URI
	 * 
	 * @param uri
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createConnection(String uri) throws IOException {
		return connectionFactory.createConnection(new URL(createUri(uri)));
	}

	/**
	 * Create connection to URI
	 * 
	 * @param uri
	 * @param method
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createConnection(String uri, String method) throws IOException {
		StackHammerConnection connection = createConnection(uri);
		connection.setRequestMethod(method);
		return configureRequest(connection);
	}

	/**
	 * Create a DELETE request connection to the URI
	 * 
	 * @param uri
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createDelete(String uri) throws IOException {
		return createConnection(uri, METHOD_DELETE);
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
				error = parseError(response);
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
	 * Create a GET request connection to the URI
	 * 
	 * @param uri
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createGet(String uri) throws IOException {
		return createConnection(uri, METHOD_GET);
	}

	/**
	 * Create a POST request connection to the URI
	 * 
	 * @param uri
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createPost(String uri) throws IOException {
		return createConnection(uri, METHOD_POST);
	}

	/**
	 * Create a PUT request connection to the URI
	 * 
	 * @param uri
	 * @return connection
	 * @throws IOException
	 */
	protected StackHammerConnection createPut(String uri) throws IOException {
		return createConnection(uri, METHOD_PUT);
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

	/**
	 * Delete resource at URI. This method will throw an {@link IOException} when the response status is not a 204 (No Content).
	 * 
	 * @param uri
	 * @throws IOException
	 */
	public void delete(String uri) throws IOException {
		delete(uri, null);
	}

	/**
	 * Delete resource at URI. This method will throw an {@link IOException} when the response status is not a 204 (No Content).
	 * 
	 * @param uri
	 * @param params
	 * @throws IOException
	 */
	public void delete(final String uri, final Object params) throws IOException {
		StackHammerConnection request = createDelete(uri);
		if(params != null)
			sendParams(request, params);
		final int code = request.getResponseCode();
		if(!isEmpty(code))
			throw new RequestException(parseError(getStream(request)), code);
	}

	/**
	 * Get response from URI and bind to specified type
	 * 
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	public StackHammerResponse get(StackHammerRequest request) throws IOException {
		StackHammerConnection httpRequest = createGet(request.generateUri());
		String accept = request.getResponseContentType();
		if(accept != null)
			httpRequest.setRequestProperty(HEADER_ACCEPT, accept);
		final int code = httpRequest.getResponseCode();
		if(isOk(code))
			return new StackHammerResponse(httpRequest, getBody(request, getStream(httpRequest)));
		if(isEmpty(code))
			return new StackHammerResponse(httpRequest, null);
		throw createException(getStream(httpRequest), code, httpRequest.getResponseMessage());
	}

	/**
	 * Get body from response inputs stream
	 * 
	 * @param request
	 * @param stream
	 * @return parsed body
	 * @throws IOException
	 */
	protected Object getBody(StackHammerRequest request, InputStream stream) throws IOException {
		Type type = request.getType();
		return type == null
				? null
				: parseJson(stream, type);
	}

	/**
	 * Get stream from request
	 * 
	 * @param request
	 * @return stream
	 * @throws IOException
	 */
	protected InputStream getStream(StackHammerConnection request) throws IOException {
		if(request.getResponseCode() < HTTP_BAD_REQUEST)
			return request.getInputStream();

		InputStream stream = request.getErrorStream();
		return stream != null
				? stream
				: request.getInputStream();
	}

	/**
	 * Get response stream from URI. It is the responsibility of the calling
	 * method to close the returned stream.
	 * 
	 * @param request
	 * @return stream
	 * @throws IOException
	 */
	public InputStream getStream(StackHammerRequest request) throws IOException {
		return getStream(createGet(request.generateUri()));
	}

	/**
	 * Get the user that this client is currently authenticating as
	 * 
	 * @return user or null if not authentication
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Is the response empty?
	 * 
	 * @param code
	 * @return true if empty, false otherwise
	 */
	protected boolean isEmpty(final int code) {
		return HTTP_NO_CONTENT == code;
	}

	/**
	 * Does status code denote an error
	 * 
	 * @param code
	 * @return true if error, false otherwise
	 */
	protected boolean isError(final int code) {
		switch(code) {
			case HTTP_BAD_REQUEST:
			case HTTP_UNAUTHORIZED:
			case HTTP_FORBIDDEN:
			case HTTP_NOT_FOUND:
			case HTTP_CONFLICT:
			case HTTP_GONE:
			case HTTP_UNPROCESSABLE_ENTITY:
			case HTTP_INTERNAL_ERROR:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Does status code denote a non-error response?
	 * 
	 * @param code
	 * @return true if okay, false otherwise
	 */
	protected boolean isOk(final int code) {
		switch(code) {
			case HTTP_OK:
			case HTTP_CREATED:
			case HTTP_ACCEPTED:
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
	protected Diagnostic parseError(InputStream response) throws IOException {
		return parseJson(response, Diagnostic.class);
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
	protected <V> V parseJson(InputStream stream, Type type) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, UTF_8), bufferSize);
		try {
			return jsonAdapter.fromJson(reader, type);
		}
		catch(JSONException jpe) {
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
	public <V> V post(final String uri, final Object params, final Type type) throws IOException {
		StackHammerConnection request = createPost(uri);
		return sendJson(request, params, type);
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
	public <V> V put(final String uri, final Object params, final Type type) throws IOException {
		StackHammerConnection request = createPut(uri);
		return sendJson(request, params, type);
	}

	private <V> V sendJson(final StackHammerConnection request, final Object params, final Type type)
			throws IOException {
		if(params != null)
			sendParams(request, params);
		final int code = request.getResponseCode();
		if(isOk(code)) {
			if(type == null)
				return null;
			return parseJson(getStream(request), type);
		}
		if(isEmpty(code))
			return null;
		throw createException(getStream(request), code, request.getResponseMessage());
	}

	/**
	 * Send parameters to output stream of request
	 * 
	 * @param request
	 * @param params
	 * @throws IOException
	 */
	protected void sendParams(StackHammerConnection request, Object params) throws IOException {
		request.setDoOutput(true);
		request.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON + "; charset=" + UTF_8.name()); //$NON-NLS-1$
		byte[] data = toJson(params).getBytes(UTF_8);
		request.setFixedLengthStreamingMode(data.length);
		BufferedOutputStream output = new BufferedOutputStream(request.getOutputStream(), bufferSize);
		try {
			output.write(data);
		}
		finally {
			output.close();
		}
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
	protected String toJson(Object object) throws IOException {
		try {
			return jsonAdapter.toJson(object);
		}
		catch(JSONException jpe) {
			IOException ioe = new IOException("Parse exception converting object to JSON"); //$NON-NLS-1$
			ioe.initCause(jpe);
			throw ioe;
		}
	}
}
