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

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.cloudsmith.hammer.api.StackHammerModule;
import org.cloudsmith.hammer.api.json.JSONAdapter;
import org.cloudsmith.hammer.api.json.JSONException;
import org.cloudsmith.hammer.api.service.StackHammerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.binder.AnnotatedBindingBuilder;

public class AbstractLiveTest {

	public static class GoogleJSONAdapter implements JSONAdapter {
		private static final GsonBuilder gsonBuilder;

		static {
			gsonBuilder = new GsonBuilder();
			gsonBuilder.setPrettyPrinting();
		}

		private final Gson gson = gsonBuilder.create();

		@Override
		public <V> V fromJson(Reader reader, Type type) throws JSONException {
			return gson.fromJson(reader, type);
		}

		@Override
		public void toJson(Object object, Writer writer) throws JSONException {
			gson.toJson(object, writer);
		}
	}

	class StackHammerTestModule extends StackHammerModule {

		public StackHammerTestModule() {
			super(
				"http", "localhost", 8080, "api", Activator.getInstance().getGitHubLogin(),
				Activator.getInstance().getAccessToken());
		}

		@Override
		protected void bindJSON(AnnotatedBindingBuilder<JSONAdapter> bind) {
			bind.to(GoogleJSONAdapter.class);
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
