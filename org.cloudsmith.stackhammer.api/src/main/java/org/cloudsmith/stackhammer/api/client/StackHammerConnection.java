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

package org.cloudsmith.stackhammer.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * This is primarily an abstraction of a URLConnection, making it easier
 * to provide a fake connection where only a small subset of the methods
 * must be implemented.
 */
public interface StackHammerConnection {

	/**
	 * See {@link HttpURLConnection#getErrorStream()}
	 */
	InputStream getErrorStream();

	/**
	 * See {@link HttpURLConnection#getHeaderField()}
	 */
	String getHeaderField(String key);

	/**
	 * See {@link HttpURLConnection#getInputStream()}
	 */
	InputStream getInputStream() throws IOException;

	/**
	 * See {@link HttpURLConnection#getOutputStream()}
	 */
	OutputStream getOutputStream() throws IOException;

	/**
	 * See {@link HttpURLConnection#getResponseCode()}
	 */
	int getResponseCode() throws IOException;

	/**
	 * See {@link HttpURLConnection#getResponseMessage()}
	 */
	String getResponseMessage() throws IOException;

	/**
	 * See {@link HttpURLConnection#getURL()}
	 */
	URL getURL();

	/**
	 * See {@link HttpURLConnection#setDoOutput(boolean))}
	 */
	void setDoOutput(boolean flag);

	/**
	 * See {@link HttpURLConnection#setFixedLengthStreamingMode(int))}
	 */
	void setFixedLengthStreamingMode(int length);

	/**
	 * See {@link HttpURLConnection#setRequestMethod(String))}
	 */
	void setRequestMethod(String method) throws ProtocolException;

	/**
	 * See {@link HttpURLConnection#setRequestProperty(String, String))}
	 */
	void setRequestProperty(String key, String value);
}
