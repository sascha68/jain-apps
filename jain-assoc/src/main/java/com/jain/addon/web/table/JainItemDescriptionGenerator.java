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
package com.jain.addon.web.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.jain.addon.JNINamed;
import com.jain.addon.i18N.I18NItemDescriptionGenerator;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.helper.JainHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

/**
 * <code>JainItemDescriptionGenerator<code> is a default Item description generator for the {@link JTable}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JainItemDescriptionGenerator implements I18NItemDescriptionGenerator {
	private JNIProperty[] properties;
	private Locale locale;

	public JainItemDescriptionGenerator(Locale locale, JNIProperty... properties) {
		this.properties = properties;
		this.locale = locale;
	}

	public String generateDescription(Component source, Object itemId, Object propertyId) {
		if(propertyId == null) {
			return JainHelper.getPojoState(locale, itemId, properties);
		}

		JNIProperty property = JainHelper.getPropertyForName(properties, propertyId);

		if(property != null && property.getType() != null) {
			Item item = ((Table)source).getItem(itemId);
			Property<?> itemProperty = item.getItemProperty(property.getName());

			switch (property.getType()) {
			case RICH_TEXT_AREA:
			case TEXT_AREA:
			case UN_SPECIFIED:
			case MULTI_SELECT:
				return formatText(itemProperty, source, itemId, propertyId);
			case DATE:
				return formatDate(itemProperty);
			default:
				break;
			}
		}
		return null;
	}

	public JNIProperty[] getProperties() {
		return properties;
	}

	public void setProperties(JNIProperty[] properties) {
		this.properties = properties;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	private String formatText(final Property<?> itemProperty, Component source, Object itemId, Object propertyId) {
		Object item = itemProperty.getValue();
		if(item != null) {
			StringBuilder content = null;

			if (item instanceof JNINamed) {
				String caption = ((JNINamed) item).getDisplayName();
				if (item.getClass().isEnum()) {
					I18NProvider provider = DefaultI18NResourceProvider.instance();
					String value = provider.getText(getLocale(), caption);
					if(caption.equals(value))
						value = provider.getTitle(getLocale(), caption);
					content = new StringBuilder(value);
				} else {
					content = new StringBuilder(caption);
				}
			} else {
				content = new StringBuilder(item.toString());
			}

			if(content.length() > 25) 
				return content.toString(); 
			return JainHelper.getPojoState(source.getLocale(), itemId, properties);
		}
		return null;
	}

	private String formatDate(final Property<?> itemProperty) {
		if(itemProperty.getValue() != null) {
			SimpleDateFormat df = new SimpleDateFormat("EEEEE MMMMM dd, yyyy hh:mm:ss aaa");
			return df.format((Date)itemProperty.getValue());
		}
		return null;
	}
}
