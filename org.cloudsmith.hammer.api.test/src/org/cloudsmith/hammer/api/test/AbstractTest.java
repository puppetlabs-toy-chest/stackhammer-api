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
package org.cloudsmith.hammer.api.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.cloudsmith.hammer.api.StackHammerModule;
import org.cloudsmith.hammer.api.client.StackHammerConnection;
import org.cloudsmith.hammer.api.client.StackHammerConnectionFactory;
import org.cloudsmith.hammer.api.service.StackHammerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractTest {
	@SuppressWarnings("serial")
	public static class FakeConnection extends HashMap<String, String> implements StackHammerConnection {
		private URL url;

		private String errors;

		private String content;

		private ByteArrayOutputStream output;

		private int responseCode;

		private String responseMessage;

		private boolean doOutput;

		private String method;

		@Override
		public InputStream getErrorStream() {
			return new ByteArrayInputStream(errors.getBytes(UTF_8));
		}

		@Override
		public String getHeaderField(String key) {
			return get(key);
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(content.getBytes(UTF_8));
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			if(output == null)
				output = new ByteArrayOutputStream();
			return output;
		}

		public String getRequestMethod() {
			return method;
		}

		@Override
		public int getResponseCode() throws IOException {
			return responseCode;
		}

		@Override
		public String getResponseMessage() throws IOException {
			return responseMessage;
		}

		@Override
		public URL getURL() {
			return url;
		}

		/**
		 * Return the UTF_8 encoded data that has been written to
		 * the output stream or null if the output stream hasn't been
		 * requested.
		 * 
		 * @return
		 */
		public String getWrittenOutput() {
			if(output == null)
				return null;
			return new String(output.toByteArray(), UTF_8);
		}

		public boolean isDoOutput() {
			return doOutput;
		}

		/**
		 * @param content the content to set
		 */
		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public void setDoOutput(boolean flag) {
			doOutput = flag;
		}

		/**
		 * @param errors the errors to set
		 */
		public void setErrors(String errors) {
			this.errors = errors;
		}

		@Override
		public void setFixedLengthStreamingMode(int length) {
		}

		@Override
		public void setRequestMethod(String method) throws ProtocolException {
			this.method = method;
		}

		@Override
		public void setRequestProperty(String key, String value) {
			put(key, value);
		}

		/**
		 * @param responseCode the responseCode to set
		 */
		public void setResponseCode(int responseCode) {
			this.responseCode = responseCode;
		}

		/**
		 * @param responseMessage the responseMessage to set
		 */
		public void setResponseMessage(String responseMessage) {
			this.responseMessage = responseMessage;
		}

		public void setURL(URL url) {
			this.url = url;
		}
	}

	class StackHammerTestModule extends StackHammerModule {

		public StackHammerTestModule() {
			super("abcdefgh");
		}

		@Override
		protected StackHammerConnectionFactory provideStackHammerConnectionFactory() {
			return new StackHammerConnectionFactory() {
				@Override
				public StackHammerConnection createConnection(URL url) throws IOException {
					FakeConnection conn = getFakeConnection();
					conn.setURL(url);
					return conn;
				}
			};
		}
	}

	private FakeConnection fakeConnection;

	private final Injector injector;

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	AbstractTest() {
		injector = Guice.createInjector(new StackHammerTestModule());
	}

	FakeConnection getFakeConnection() {
		if(fakeConnection == null)
			fakeConnection = new FakeConnection();
		return fakeConnection;
	}

	StackHammerFactory getStackHammerFactory() {
		return injector.getInstance(StackHammerFactory.class);
	}
}
