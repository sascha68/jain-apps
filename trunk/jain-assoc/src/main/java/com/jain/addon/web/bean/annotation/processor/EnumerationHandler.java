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

import java.util.Collection;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.constraint.JNIEnumeration;

/**
 * <code>EnumurationHandler<code> is a class to handle all type of enumeration,
 * i.e Either Class or a CDI component.
 * In both cased enumeration class should implement {@link JNIEnumeration} interface.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public final class EnumerationHandler {
	private EnumerationHandler () {}
	
	public static Collection<?> getValue(final JNIPropertyConstraint constraint) {
		Collection<?> values = null;
		if (constraint.getTypes() != null) {
			for (JConstraintType type : constraint.getTypes()) {
				JNIEnumeration enumaration = null;
				switch (type) {
				case CLASS_CONSTRAINT:
					try {
						@SuppressWarnings("unchecked")
						Class <JNIEnumeration> clazz = (Class <JNIEnumeration>) Class.forName(constraint.getEnumarationName());
						enumaration = clazz.newInstance();
					} catch (Exception e) {
					}
					break;
				case CDI_COMPONENT:
					enumaration = CDIComponent.getInstance(constraint.getEnumarationName()); 
					break;
				default:
					break;
				}
				if (enumaration != null)
					values = enumaration.getValues();
			}
		}
		return values;
	}
}
