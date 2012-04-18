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

package org.cloudsmith.hammer.api.model;

public class DiagnosticWithLink extends Diagnostic {
	private static final long serialVersionUID = 7323316406909195048L;

	private String link;

	/**
	 * @return the link
	 */
	@Override
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	@Override
	public void setLink(String link) {
		this.link = link;
	}
}
