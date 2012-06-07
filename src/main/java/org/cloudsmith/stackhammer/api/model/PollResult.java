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
import java.util.List;

/**
 * Result of pinging an asynchronously executing job
 */
public class PollResult implements Serializable {
	private static final long serialVersionUID = 3363361329096042952L;

	private JobState jobState;

	private List<LogEntry> logEntries;

	public JobState getJobState() {
		return jobState;
	}

	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

	public void setJobState(JobState jobState) {
		this.jobState = jobState;
	}

	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}
}
