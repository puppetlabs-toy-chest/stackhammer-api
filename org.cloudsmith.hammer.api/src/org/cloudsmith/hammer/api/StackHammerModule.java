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

package org.cloudsmith.hammer.api;

import org.cloudsmith.hammer.api.client.StackHammerConnectionFactory;
import org.cloudsmith.hammer.api.json.JSONAdapter;
import org.cloudsmith.hammer.api.util.UrlUtils.UrlConnectionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.name.Names;

/**
 * <p>This is the main entry point to the Stack Hammer API. It uses {@link Guice} to
 * establish all bindings.</p>
 * <p>The class must be subclassed with an implementation that provides bindings
 * for the {@link JSONAdapter} and {@link Base64Adapter}.</p>
 * Intended usage (the subclass is named <tt>RealStackHammerModule</tt> here but that's just
 * an example):<pre>
 * Injector injector = Guice.createInjector(new RealStackHammerModule(user, credentials));
 * StackHammerFactory factory = injector.getInstance(StackHammerFactory.class);
 * // Use factory to create desired service and continue from there
 * </pre>
 */
public abstract class StackHammerModule extends AbstractModule {

	private static final String PROTOCOL_HTTPS = "https"; //$NON-NLS-1$

	private static final String STACKHAMMER_HOST = "api.stackhammer.com"; //$NON-NLS-1$

	private final String baseUri;

	private final String user;

	private final String credentials;

	public StackHammerModule(String user, String credentials) {
		this(PROTOCOL_HTTPS, STACKHAMMER_HOST, 443, null, user, credentials);
	}

	public StackHammerModule(String scheme, String hostname, int port, String prefix, String user, String credentials) {
		super();
		final StringBuilder uri = new StringBuilder(scheme);
		uri.append("://");
		uri.append(hostname);
		if(port > 0) {
			uri.append(':');
			uri.append(port);
		}
		if(prefix != null)
			uri.append(prefix);
		this.baseUri = uri.toString();
		this.user = user;
		this.credentials = credentials;

	}

	protected abstract void bindJSON(AnnotatedBindingBuilder<JSONAdapter> bind);

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("StackHammer user")).toInstance(user);
		bind(String.class).annotatedWith(Names.named("StackHammer credentials")).toInstance(credentials);
		bind(String.class).annotatedWith(Names.named("StackHammer baseUri")).toInstance(baseUri);
		bindJSON(bind(JSONAdapter.class));
	}

	@Provides
	protected StackHammerConnectionFactory provideStackHammerConnectionFactory() {
		return new UrlConnectionFactory();
	}
}
