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

import org.cloudsmith.hammer.api.service.StackHammerFactory;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class FactoryTest extends AbstractTest {

	@Test
	public void testFactory() {
		StackHammerFactory factory = getStackHammerFactory();
		assertNotNull(factory);
		assertNotNull(factory.createRepositoryService());
		assertNotNull(factory.createStackService());
	}
}
