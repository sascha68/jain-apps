/* 
 * Copyright 2012 Lokesh Jain.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jain.addon.web.bean.factory.generator;

import java.io.Serializable;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.vaadin.ui.Field;

/**
 * <code>FieldGenerator<code> Every field generator should implement this interface.
 * @author Lokesh Jain
 * @since Nov 16, 2012
 * @version 1.0.3
 */
public interface FieldGenerator extends Serializable {

	/**
	 * Generate {@link Field} based on bean type and {@link JNIPropertyConstraint}
	 * @param type
	 * @param restriction
	 * @return {@link Field}
	 */
	public Field<?> createField(Class<?> type, JNIPropertyConstraint restriction);
	
	/**
	 * Generate {@link Field} based on bean type and {@link JNIProperty}
	 * @param type
	 * @param property
	 * @return {@link Field}
	 */
	public Field<?> createField(Class<?> type, JNIProperty property);
}
