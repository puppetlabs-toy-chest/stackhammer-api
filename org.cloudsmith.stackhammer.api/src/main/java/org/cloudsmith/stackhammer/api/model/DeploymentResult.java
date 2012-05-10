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

import java.util.List;

/**
 * The result of a Validation is a base-64 encoded string representing
 * the bytes of an SVG image which in turn represents the validation
 * graph.
 */
public class DeploymentResult extends ResultWithDiagnostic<List<HostResult>> {
	private static final long serialVersionUID = 1626685805862697038L;
}
