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

package org.cloudsmith.hammer.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author thhal
 * 
 */
public interface StackHammerConnection {

	/**
	 * @return
	 */
	InputStream getErrorStream();

	/**
	 * @param key
	 * @return
	 */
	String getHeaderField(String key);

	/**
	 * @return
	 */
	InputStream getInputStream() throws IOException;

	/**
	 * @return
	 */
	OutputStream getOutputStream() throws IOException;

	/**
	 * @return
	 */
	int getResponseCode() throws IOException;

	/**
	 * @return
	 */
	String getResponseMessage() throws IOException;

	/**
	 * @return
	 */
	URL getURL();

	/**
	 * @param flag
	 */
	void setDoOutput(boolean flag);

	/**
	 * @param length
	 */
	void setFixedLengthStreamingMode(int length);

	/**
	 * @param method
	 */
	void setRequestMethod(String method) throws ProtocolException;

	/**
	 * @param key
	 * @param value
	 */
	void setRequestProperty(String key, String value);
}
