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
