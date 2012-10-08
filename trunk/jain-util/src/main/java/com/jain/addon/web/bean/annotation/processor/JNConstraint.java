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
package com.jain.addon.web.bean.annotation.processor;

import java.util.Arrays;
import java.util.Collection;

import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.vaadin.data.Validator;

/**
 * <code>JNConstraint<code> is a default object to handle constraints.
 * These constraints are used by framework for UI generation.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JNConstraint implements JNIPropertyConstraint {
	public JNIProperty property;
	public String width;
	public Collection<JConstraintType> types;
	public String enumarationName;

	public JNIProperty getProperty() {
		return property;
	}

	public void setProperty(JNIProperty property) {
		this.property = property;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Collection<JConstraintType> getTypes() {
		return types;
	}

	public void setTypes(Collection<JConstraintType> types) {
		this.types = types;
	}
	
	public void setTypes(JConstraintType ... types) {
		this.types = Arrays.asList(types);
	}

	public String getEnumarationName() {
		return enumarationName;
	}

	public void setEnumarationName(String enumarationName) {
		this.enumarationName = enumarationName;
	}

	public Validator getValidator() {
		return null;
	}

	public Collection<?> getRestrictions() {
		return null;
	}
}
