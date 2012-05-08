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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;

import org.cloudsmith.stackhammer.api.model.Diagnostic;
import org.cloudsmith.stackhammer.api.model.Provider;
import org.cloudsmith.stackhammer.api.model.Repository;
import org.cloudsmith.stackhammer.api.model.ResultWithDiagnostic;
import org.cloudsmith.stackhammer.api.service.RepositoryService;
import org.cloudsmith.stackhammer.api.service.StackHammerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class RepositoryServiceTest extends AbstractTest {
	private static final String REPOSERVICE_URL = "https://api.stackhammer.com:443/repos";

	private RepositoryService service;

	@Before
	public void init() {
		StackHammerFactory factory = getStackHammerFactory();
		assertNotNull(factory);
		service = factory.createRepositoryService();
		assertNotNull(service);
	}

	@Test
	public void testCall() {
		try {
			FakeConnection conn = getFakeConnection();
			conn.setResponseCode(200);
			conn.setResponseMessage("OK");
			conn.setContent("{ result: { name: \"cs-test1\", owner: \"test-repo\", provider: \"GITHUB\", branch: \"master\" }, severity: 0}");
			ResultWithDiagnostic<Repository> result = service.cloneRepository(
				Provider.GITHUB, "cs-test1", "test-repo", "master");
			assertNotNull(result);
			assertEquals(Diagnostic.OK, result.getSeverity());
			Repository repo = result.getResult();
			URL url = conn.getURL();
			assertNotNull(url);
			assertEquals(REPOSERVICE_URL + "/clone", url.toString());
			assertEquals("POST", conn.getRequestMethod());
			assertNotNull(repo);
			assertEquals("cs-test1", repo.getName());

			String output = conn.getWrittenOutput();
			System.out.println(output);
		}
		catch(IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
