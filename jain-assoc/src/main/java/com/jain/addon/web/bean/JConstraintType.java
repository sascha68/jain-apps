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
package com.jain.addon.web.bean;

import java.util.Arrays;
import java.util.List;

import com.jain.addon.web.bean.constraint.JNIEnumeration;

/**
 * <code>JConstraintType<code> is used for UI generation constraints <br/>
 * <pre>
 * NONE - No constraint for the property
 * REQUIRED - Property value is required
 * READ_ONLY - Property is read only
 * DISABLED - Property is disabled 
 * BEAN_VALIDATION - bean validation is required
 * CLASS_CONSTRAINT - Class level enumeration is provided 
 * (class should implement {@link JNIEnumeration})
 * CDI_COMPONENT - CDI component level enumeration is provided 
 * (Component should implement {@link JNIEnumeration})
 * </pre>
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public enum JConstraintType {
	NONE,
	REQUIRED,
	READ_ONLY,
	DISABLED,
	BEAN_VALIDATION,
	CLASS_CONSTRAINT,
	CDI_COMPONENT;
	
	public static JConstraintType [] getTypesArray (JConstraintType ... propertyTypes) {
		return propertyTypes;
	}
	
	public static List<JConstraintType> getTypesList (JConstraintType ... propertyTypes) {
		return Arrays.asList(propertyTypes);
	}
}