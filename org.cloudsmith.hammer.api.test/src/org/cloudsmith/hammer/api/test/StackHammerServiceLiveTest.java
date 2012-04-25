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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.cloudsmith.hammer.api.model.Diagnostic;
import org.cloudsmith.hammer.api.model.Provider;
import org.cloudsmith.hammer.api.model.Repository;
import org.cloudsmith.hammer.api.model.ResultWithDiagnostic;
import org.cloudsmith.hammer.api.service.RepositoryService;
import org.cloudsmith.hammer.api.service.StackHammerFactory;
import org.cloudsmith.hammer.api.service.StackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class StackHammerServiceLiveTest extends AbstractLiveTest {
	private RepositoryService repoService;

	private StackService stackService;

	@Before
	public void init() {
		StackHammerFactory factory = getStackHammerFactory();
		assertNotNull(factory);
		stackService = factory.createStackService();
		repoService = factory.createRepositoryService();
		assertNotNull(stackService);
	}

	@Test
	public void testCall() {
		try {
			Activator activator = Activator.getInstance();
			String owner = activator.getGitHubLogin();
			ResultWithDiagnostic<Repository> cloneResult = repoService.cloneRepository(
				Provider.GITHUB, owner, "puppetconf-demo", "master");
			assertNotNull(cloneResult);
			System.out.println(cloneResult);
			assertEquals(Diagnostic.OK, cloneResult.getSeverity());

			Repository repo = cloneResult.getResult();
			assertNotNull(repo);

			ResultWithDiagnostic<byte[]> validationResult = stackService.validateStack(repo, repo.getOwner() + "/" +
					repo.getName());
			assertNotNull(validationResult);
			System.out.println(validationResult);
			assertFalse(validationResult.getSeverity() == Diagnostic.ERROR);
		}
		catch(IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
