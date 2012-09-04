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
package com.jain.addon.web.bean.factory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.jain.addon.JNINamed;
import com.jain.addon.StringHelper;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.annotation.processor.EnumerationHandler;
import com.jain.addon.web.component.upload.JImage;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField.Resolution;
import com.vaadin.ui.Field;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * <code>AbstractFieldFactory<code> is a factory class to 
 * generate {@link Field} based on bean type and {@link JNIPropertyConstraint} 
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractFieldFactory  implements Serializable {

	/**
	 * Generate {@link Field} based on bean type and {@link JNIPropertyConstraint}
	 * @param type
	 * @param propertyConstraint
	 * @return {@link Field}
	 */
	public Field<?> createField(Class<?> type, JNIPropertyConstraint propertyConstraint) {
		Field<?> field = null;

		if(propertyConstraint.getTypes() != null && propertyConstraint.getTypes().contains(JConstraintType.SECRETE)){
			return createPasswordField(propertyConstraint.getProperty());
		}

		if(StringHelper.isNotEmptyWithTrim(propertyConstraint.getEnumarationName())) {
			return createRestrictionField(propertyConstraint);
		}

		if (type != null) {
			if (Date.class.isAssignableFrom(type)) {
				field = createDateField(propertyConstraint.getProperty());
			}else if (Boolean.class.isAssignableFrom(type)) {
				field = createBooleanField(propertyConstraint.getProperty());
			}else if(Enum.class.isAssignableFrom(type)) {
				field = createEnumField(type, propertyConstraint.getProperty());
			}else {
				field = createTextField(propertyConstraint.getProperty());
			}

			updateFieldProperties(propertyConstraint, field);
		}

		return field;
	}

	protected PasswordField createPasswordField(JNIProperty property) {
		PasswordField pf = new PasswordField();
		pf.setCaption(getCaption(property));
		pf.setInputPrompt(getCaption(property));
		pf.setDescription(getDescription(property));
		pf.setNullRepresentation("");
		return pf;
	}

	protected Field<?> createTextField(JNIProperty property) {
		if(property.getType() == JPropertyType.TEXT_AREA) {
			TextArea field = new TextArea();
			field.setInputPrompt(getCaption(property));
			field.setNullRepresentation("");
			field.setRows(5);
			return field;
		}

		if(property.getType() == JPropertyType.RICH_TEXT_AREA) {
			RichTextArea field = new RichTextArea();
			field.setNullRepresentation("");
			return field;
		}

		if(property.getType() == JPropertyType.IMAGE) {
			JImage image = new JImage();
			image.setInterruptionMessage(property.getName() + ".upload.interruption");
			image.setUploadButtonCaption(property.getName() + ".upload.button.caption");
			return image;
		}

		TextField field = new TextField();
		field.setInputPrompt(getCaption(property));
		field.setNullRepresentation("");
		return field;
	}

	protected Field<?> createEnumField(Class<?> type, JNIProperty property) {
		final ComboBox combo = new ComboBox(getCaption(property));
		combo.setInputPrompt(getCaption(property));
		combo.setDescription(getDescription(property));

		Object[] values = type.getEnumConstants();
		for (Object object : values) {
			combo.addItem(object);
			if(object instanceof JNINamed) {
				combo.setItemCaption(object, ((JNINamed) object).getDisplayName());
			}
		}
		return combo;
	}

	protected Field<?> createRestrictionField(JNIPropertyConstraint propertyConstraint) {
		final ComboBox combo = new ComboBox(getCaption(propertyConstraint.getProperty()));
		combo.setInputPrompt(getCaption(propertyConstraint.getProperty()));
		combo.setDescription(getDescription(propertyConstraint.getProperty()));

		Collection <?> values = EnumerationHandler.getValue(propertyConstraint);
		for (Object object : values) {
			combo.addItem(object);
			if(object instanceof JNINamed) {
				combo.setItemCaption(object, ((JNINamed) object).getDisplayName());
			}
		}

		if(propertyConstraint.getTypes() != null) {
			if(propertyConstraint.getTypes().contains(JConstraintType.REQUIRED)){
				combo.setNullSelectionAllowed(false);
			}
		}

		updateFieldProperties(propertyConstraint, combo);

		return combo;
	}

	protected Field<?> createBooleanField(JNIProperty property) {
		return new CheckBox();
	}

	protected Field<?> createDateField(JNIProperty property) {
		final PopupDateField df = new PopupDateField();
		df.setResolution(Resolution.DAY);
		df.setInputPrompt(getCaption(property));
		return df;
	}

	protected void updateFieldProperties(JNIPropertyConstraint propertyConstraint, Field<?> field) {
		if(propertyConstraint != null){
			field.setCaption(getCaption(propertyConstraint.getProperty()));
			field.setTabIndex(propertyConstraint.getProperty().getOrder());

			if(propertyConstraint.getValidator() != null){
				field.addValidator(propertyConstraint.getValidator());
			}

			if(propertyConstraint.getTypes() != null) {
				field.setReadOnly(propertyConstraint.getTypes().contains(JConstraintType.READ_ONLY));

				if(propertyConstraint.getTypes().contains(JConstraintType.REQUIRED)){
					field.setRequired(true);
					field.setRequiredError(getRequiredError(propertyConstraint.getProperty()));
				}
			}

			if(StringHelper.isNotEmptyWithTrim(propertyConstraint.getWidth())){
				field.setWidth(propertyConstraint.getWidth());
			}else{
				field.setSizeFull();
			}
		}
	}

	protected abstract String getCaption(JNIProperty property);
	protected abstract String getRequiredError(JNIProperty property);
	protected abstract String getDescription(JNIProperty property);
}
