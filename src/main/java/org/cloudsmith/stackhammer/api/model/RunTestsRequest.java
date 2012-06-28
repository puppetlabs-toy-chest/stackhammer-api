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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An instance of this class encapsulates all information needed to
 * perform a stack test run
 */
public class RunTestsRequest implements Serializable {
	private static final long serialVersionUID = -1602553407705036425L;

	private StackIdentifier stackIdentifier;

	private String instanceId;

	private boolean undeploy;

	private List<String> testNames = Collections.<String> emptyList();

	public String getInstanceId() {
		return instanceId;
	}

	public StackIdentifier getStackIdentifier() {
		return stackIdentifier;
	}

	public List<String> getTestNames() {
		return testNames;
	}

	public boolean isUndeploy() {
		return undeploy;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setStackIdentifier(StackIdentifier stackIdentifier) {
		this.stackIdentifier = stackIdentifier;
	}

	public void setTestNames(List<String> testNames) {
		this.testNames = testNames == null
				? Collections.<String> emptyList()
				: Collections.unmodifiableList(new ArrayList<String>(testNames));
	}

	public void setUndeploy(boolean flag) {
		this.undeploy = flag;
	}
}
