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

import java.io.Serializable;

/**
 */
public class DeployRequest implements Serializable {
	private static final long serialVersionUID = -1602553407705036425L;

	private StackIdentifier stackIdentifier;

	private boolean dryRun;

	public StackIdentifier getStackIdentifier() {
		return stackIdentifier;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public void setStackIdentifier(StackIdentifier stackIdentifier) {
		this.stackIdentifier = stackIdentifier;
	}

}
