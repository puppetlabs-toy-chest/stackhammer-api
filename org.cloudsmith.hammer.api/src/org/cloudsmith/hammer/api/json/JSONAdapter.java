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

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Simple adaptor for JSON serialization/deserialization. A user of this API must
 * provide a suitable implementation
 */
public interface JSONAdapter {
	/**
	 * Deserialize the contents of the <code>reader</code> into an instance of the
	 * given <code>type</code>
	 * 
	 * @param reader The reader holding the content
	 * @param type The type of the instance to be parsed from the reader
	 * @return The created instance
	 * @throws JSONException
	 */
	<V> V fromJson(Reader reader, Type type) throws JSONException;

	/**
	 * Serialize the given <code>object</code> onto the <code>writer</code>
	 * 
	 * @param object The object to serialize
	 * @param writer The writer where the serialized object is written
	 * @throws JSONException
	 */
	void toJson(Object object, Writer writer) throws JSONException;
}
