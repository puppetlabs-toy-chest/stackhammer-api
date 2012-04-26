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
package org.cloudsmith.hammer.api.json;

/**
 * Simple adaptor for JSON serialization/deserialization. A user of this API must
 * provide a suitable implementation
 */
public interface JSONAdapter {
	/**
	 * Deserialize the contents of the <code>jsonString</code> into an instance of the
	 * given <code>type</code>
	 * 
	 * @param jsonString The string holding the content
	 * @param type The type of the instance to be parsed from the jsonString
	 * @return The created instance
	 * @throws JSONException
	 */
	<V> V fromJson(String jsonString, Class<V> type) throws JSONException;

	/**
	 * Serialize the given <code>object</code> into a json string
	 * 
	 * @param bean The object to serialize
	 * @return The string representation of the bean
	 * @throws JSONException
	 */
	String toJson(Object bean) throws JSONException;
}
