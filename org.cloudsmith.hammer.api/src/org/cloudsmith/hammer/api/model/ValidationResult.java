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

import java.util.List;

public class ValidationResult {
	private List<Diagnostic> warnings;

	private List<Diagnostic> errors;

	/**
	 * @return the errors
	 */
	public List<Diagnostic> getErrors() {
		return errors;
	}

	/**
	 * @return the warnings
	 */
	public List<Diagnostic> getWarnings() {
		return warnings;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<Diagnostic> errors) {
		this.errors = errors;
	}

	/**
	 * @param warnings the warnings to set
	 */
	public void setWarnings(List<Diagnostic> warnings) {
		this.warnings = warnings;
	}
}
