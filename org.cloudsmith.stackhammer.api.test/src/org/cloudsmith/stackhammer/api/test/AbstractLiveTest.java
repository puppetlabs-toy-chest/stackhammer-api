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
package org.cloudsmith.stackhammer.api.test;

import java.nio.charset.Charset;

import org.cloudsmith.stackhammer.api.StackHammerModule;
import org.cloudsmith.stackhammer.api.service.StackHammerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractLiveTest {

	class StackHammerTestModule extends StackHammerModule {

		public StackHammerTestModule() {
			super("http", "localhost", 8080, "/api", Activator.getInstance().getAccessToken());
		}
	}

	private final Injector injector;

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	AbstractLiveTest() {
		injector = Guice.createInjector(new StackHammerTestModule());
	}

	StackHammerFactory getStackHammerFactory() {
		return injector.getInstance(StackHammerFactory.class);
	}
}
