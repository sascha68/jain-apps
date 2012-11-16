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

import java.util.Collection;
import java.util.Locale;

import com.jain.addon.StringHelper;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.vaadin.ui.Field;

/**
 * <code>AbstractFieldGenerator<code> is a class, having some basic methods for other field generators.
 * Each field generator should extend this class. 
 * @author Lokesh Jain
 * @since Nov 16, 2012
 * @version 1.0.3
 */
@SuppressWarnings("serial")
public abstract class AbstractFieldGenerator implements FieldGenerator {
	private Locale locale;
	private I18NProvider provider;

	/**
	 * @param locale
	 * @param provider
	 */
	public AbstractFieldGenerator(Locale locale, I18NProvider provider) {
		this.locale = locale;
		this.provider = provider;
	}

	public Field<?> createField(Class<?> type, JNIPropertyConstraint restriction){
		Field<?> field = createField(type, restriction.getProperty());
		updateFieldProperties(restriction, field);
		return field;
	}

	public Field <?> createField(Class<?> type, JNIProperty property) {
		throw new UnsupportedOperationException ();
	}

	protected String getCaption(JNIProperty property) {
		return property.getDisplayName();
	}

	protected String getRequiredError(JNIProperty property) {
		return "common.something.required";
	}

	protected String getDescription(JNIProperty property) {
		return provider.getMessage(locale, property.getDisplayName());
	}

	/**
	 * @param restriction
	 * @param field
	 */
	protected void updateFieldProperties(JNIPropertyConstraint restriction, Field<?> field) {
		if(restriction != null){
			field.setCaption(getCaption(restriction.getProperty()));
			field.setTabIndex(restriction.getProperty().getOrder());

			if(restriction.getValidator() != null){
				field.addValidator(restriction.getValidator());
			}

			updateRestriction(restriction, field);

			if(StringHelper.isNotEmptyWithTrim(restriction.getWidth())){
				field.setWidth(restriction.getWidth());
			}else{
				field.setSizeFull();
			}
		}
	}

	/**
	 * Method to update restrictions
	 * @param restriction
	 * @param field
	 */
	private void updateRestriction(JNIPropertyConstraint restriction, Field<?> field) {
		Collection<JConstraintType> types = restriction.getTypes();

		if(types != null) {
			for (JConstraintType type : types) {
				switch (type) {
				case READ_ONLY :
					field.setReadOnly(true);
					break;
				case REQUIRED :
					field.setRequired(true);
					field.setRequiredError(getRequiredError(restriction.getProperty()));
					break;
				case DISABLED :
					field.setEnabled(false);
				default:
					break;
				}
			}
		}
	}
}
