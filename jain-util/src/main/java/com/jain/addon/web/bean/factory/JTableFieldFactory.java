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

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TableFieldFactory;

/**
 * <code>JTableFieldFactory<code> is a factory class to 
 * generate {@link Field} based on bean type, {@link JNIPropertyConstraint} and locale 
 * using given {@link I18NProvider}  
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JTableFieldFactory extends AbstractFieldFactory implements FormFieldFactory, TableFieldFactory  {
	private Collection<JNIPropertyConstraint> propertyConstraints;
	private Locale locale;
	private I18NProvider provider;
	
	public JTableFieldFactory(Locale locale, Collection<JNIPropertyConstraint> fieldProperties) {
		this.propertyConstraints = fieldProperties;
		this.locale = locale;
		this.provider = DefaultI18NResourceProvider.instance(); 
	}

	public JTableFieldFactory(Locale locale, JNIPropertyConstraint [] fieldProperties) {
		this(locale, Arrays.asList(fieldProperties));
	}

	public Field<?> createField(Item item, Object propertyId, Component uiContext){
		Class<?> type = item.getItemProperty(propertyId).getType();
		return createField(type, propertyId);
	}

	public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
		Property<?> containerProperty = container.getContainerProperty(itemId, propertyId);
		Class<?> type = containerProperty.getType();
		return createField(type, propertyId);
	}

	protected Field<?> createField(Class<?> type, Object propertyId) {
		JNIPropertyConstraint propertyConstraint = findPropertyConstraint(propertyId);
		return createField(type, propertyConstraint);
	}

	protected JNIPropertyConstraint findPropertyConstraint(Object propertyId) {
		for (JNIPropertyConstraint propertyConstraint : propertyConstraints) {
			if(propertyId.equals(propertyConstraint.getProperty().getName())){
				return propertyConstraint;
			}
		}
		return null;
	}
	
	protected String getCaption(JNIProperty property) {
		return provider.getTitle(locale, property.getDisplayName());
	}

	protected String getRequiredError(JNIProperty property) {
		return provider.getMessage(locale, "something.required", property.getDisplayName());
	}

	protected String getDescription(JNIProperty property) {
		return provider.getMessage(locale, property.getDisplayName());
	}
}
