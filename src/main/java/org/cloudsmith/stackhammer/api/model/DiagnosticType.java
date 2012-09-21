/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.stackhammer.api.model;

public enum DiagnosticType {
	// @fmtOff
	UNKNOWN("Unknown"),
	GEPPETTO("Geppetto"),
	GEPPETTO_SYNTAX("Geppetto Syntax"),	
	CATALOG_PARSER("Catalog Parser"),
	CATALOG("Catalog"),
	FORGE("Forge"),
	INTERNAL_ERROR("Internal Error"),
	RUBY_SYNTAX("Ruby Syntax"),
	RUBY("Ruby"),
	PUPPET_LINT("puppet-lint");
	// @fmtOn

	private final String label;

	private DiagnosticType(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
