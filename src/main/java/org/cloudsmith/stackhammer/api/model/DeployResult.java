/**
 * Copyright 2012-, Cloudsmith Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.cloudsmith.stackhammer.api.model;

import java.util.List;

/**
 * The result of a Validation is a base-64 encoded string representing
 * the bytes of an SVG image which in turn represents the validation
 * graph.
 */
public class DeployResult extends ResultWithDiagnostic<List<CatalogGraph>> {
	private static final long serialVersionUID = 1626685805862697038L;
}
