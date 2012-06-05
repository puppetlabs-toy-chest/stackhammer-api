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

package org.cloudsmith.stackhammer.api;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.name.Names;

/**
 * <p>This is the main entry point to the Stack Hammer API. It uses {@link Guice} to
 * establish all bindings.</p>
 * <p>
 * Intended usage:<pre>
 * Injector injector = Guice.createInjector(new StackHammerModule(user, credentials));
 * StackHammerFactory factory = injector.getInstance(StackHammerFactory.class);
 * // Use factory to create desired service and continue from there
 * </pre>
 */
public class StackHammerModule extends AbstractModule {

	private static final String PROTOCOL_HTTPS = "https"; //$NON-NLS-1$

	private static final String STACKHAMMER_HOST = "stackservice.cloudsmith.com"; //$NON-NLS-1$

	private final String baseUri;

	private final String credentials;

	public StackHammerModule(String credentials) {
		this(PROTOCOL_HTTPS, STACKHAMMER_HOST, 443, "/service/api/", credentials);
	}

	/**
	 * Create a new module for communication with the specified service using the given credentials
	 * 
	 * @param scheme URL scheme, i.e. http or https
	 * @param hostname The hostname of the server
	 * @param port The port number
	 * @param prefix The prefix to use (first segment in URL path)
	 * @param user The login of the user
	 * @param credentials User credentials (i.e. API token)
	 */
	public StackHammerModule(String scheme, String hostname, int port, String prefix, String credentials) {
		super();
		final StringBuilder uri = new StringBuilder(scheme);
		uri.append("://");
		uri.append(hostname);
		if(port > 0) {
			uri.append(':');
			uri.append(port);
		}

		if(prefix != null && prefix.length() > 0) {
			if(!prefix.startsWith("/"))
				uri.append('/');
			uri.append(prefix);
			if(!prefix.endsWith("/"))
				uri.append('/');
		}
		else
			uri.append('/');

		uri.append("1.0");

		this.baseUri = uri.toString();
		this.credentials = credentials;

	}

	/**
	 * Configure bindings for this module.
	 */
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("StackHammer credentials")).toInstance(credentials);
		bind(String.class).annotatedWith(Names.named("StackHammer baseUri")).toInstance(baseUri);
	}
}
