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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ResultWithDiagnostic;
import org.cloudsmith.hammer.api.service.StackHammerFactory;
import org.cloudsmith.hammer.api.service.StackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class StackHammerServiceTest extends AbstractTest {
	private static final String REPOSERVICE_URL = "https://api.stackhammer.com:443/repos";

	private StackService service;

	@Before
	public void init() {
		StackHammerFactory factory = getStackHammerFactory();
		assertNotNull(factory);
		service = factory.createStackService();
		assertNotNull(service);
	}

	@Test
	public void testCall() {
		FakeConnection conn = getFakeConnection();
		try {
			conn.setResponseCode(200);
			conn.setResponseMessage("OK");
			conn.setContent("{severity: 0}");

			Repository repo = new Repository();
			repo.setBranch("master");
			repo.setName("guide-fix-stack");
			repo.setOwner("cs-test1");
			ResultWithDiagnostic<String> result = service.validateStack(repo, "cs-test1/guide-fix-stack");
			String output = conn.getWrittenOutput();
			System.out.println(output);
		}
		catch(IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
