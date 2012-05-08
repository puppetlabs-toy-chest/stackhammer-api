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
package org.cloudsmith.stackhammer.api.model;

/**
 * Git repository provider known to the Stackhammer service. Currently
 * GitHub is the only one.
 */
public enum Provider {
	GITHUB {
		@Override
		public String getRepositoryBase(String owner, String name, String branch) {
			return "https://github.com/" + owner + '/' + name + "/blob/" + branch + '/';
		}
	};

	public abstract String getRepositoryBase(String owner, String name, String branch);
}
