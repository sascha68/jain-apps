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
package com.jain.addon.web.bean.factory.generator.select;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.constraint.JNIEnumeration;
import com.jain.addon.web.bean.factory.generator.AbstractFieldGenerator;

/**
 * <code>SelectFieldGenerator<code>
 * @author Lokesh Jain
 * @since Nov 16, 2012
 * @version 1.0.3
 */
@SuppressWarnings("serial")
public abstract class SelectFieldGenerator extends AbstractFieldGenerator {

	public SelectFieldGenerator(Locale locale, I18NProvider provider) {
		super(locale, provider);
	}

	protected Collection<?> extractValues(final Class<?> type, final JNIPropertyConstraint restriction) {
		Collection<?> values = null;
		if (restriction.getTypes() != null) {
			values = extractValuesFromRestriction(restriction);
		}

		if (values == null && type.isEnum()) {
			values = Arrays.asList(type.getEnumConstants());
		}

		return values;
	}

	@SuppressWarnings("unchecked")
	private Collection<?> extractValuesFromRestriction(final JNIPropertyConstraint restriction) {
		Collection<?> values = null;
		for (JConstraintType restrictionType : restriction.getTypes()) {
			JNIEnumeration enumaration = null;
			switch (restrictionType) {
			case CLASS_CONSTRAINT:
				try {
					Class <JNIEnumeration> clazz = (Class <JNIEnumeration>) Class.forName(restriction.getEnumarationName());
					enumaration = clazz.newInstance();
				} catch (Exception e) {
				}
				break;
			case CDI_COMPONENT:
				enumaration = CDIComponent.getInstance(restriction.getEnumarationName()); 
				break;
			default:
				break;
			}
			if (enumaration != null)
				values = enumaration.getValues();
		}
		return values;
	}
}
