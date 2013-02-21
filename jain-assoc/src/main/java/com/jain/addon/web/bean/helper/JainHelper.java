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
package com.jain.addon.web.bean.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.jain.addon.JNINamed;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.JVisibilityType;
import com.jain.addon.web.layout.JNILayout;
import com.vaadin.ui.Component;

/**
 * A Helper Class for Vaadin Components
 * @author Lokesh Jain
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public final class JainHelper {
	private JainHelper() {}

	/**
	 * Method will fetch a parent component for the given class name for the given component. <br/>
	 * E.g Component A is B as a parent and B is having C as parent. <br/>
	 * If we pass A as a component and C as a class then returned Component Will be C. 
	 * @param component
	 * @param clazz
	 * @return Component
	 */
	public static Component getParent(Component component, Class clazz) {
		if (component == null)
			return null;
		if(component.getClass().equals(clazz))
			return component;
		return getParent(component.getParent(), clazz);
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @return String []
	 */
	public static String [] getProperties(JNIProperty [] properties){
		return getProperties(Arrays.asList(properties));
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @param checkVisiblity
	 * @return String []
	 */
	public static String [] getProperties(JNIProperty [] properties, boolean checkVisiblity){
		return getProperties(Arrays.asList(properties), checkVisiblity);
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @return String []
	 */
	public static String [] getProperties(Collection<? extends JNIProperty> properties){
		return getProperties(properties, true);
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @param checkVisiblity
	 * @return String []
	 */
	public static String [] getProperties(Collection<? extends JNIProperty> properties, boolean checkVisiblity){
		List<String> names = new ArrayList<String>();
		for (JNIProperty property : properties) {
			if(checkVisiblity 
					&& (property.getVisibility() == JVisibilityType.COLLAPSED 
					|| JVisibilityType.VISIBLE == property.getVisibility()))
				names.add(property.getName());
			else if (!checkVisiblity)
				names.add(property.getName());
		}
		return names.toArray(new String [1]);
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @return List<String>
	 */
	public static Collection<String> getPropertiesCollection(JNIProperty [] properties){
		return Arrays.asList(getProperties(properties));
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @return List<String>
	 */
	public static Collection<String> getPropertiesCollection(Collection<? extends JNIProperty> properties){
		return Arrays.asList(getProperties(properties));
	}

	/**
	 * Method to fetch field property Display Names
	 * @param properties
	 * @return String []
	 */
	public static String [] getPropertiesDisplayName(JNIProperty [] properties){
		return getPropertiesDisplayName(Arrays.asList(properties));
	}

	/**
	 * Method to fetch field property Display Names
	 * @param locale 
	 * @param properties
	 * @param checkVisiblity
	 * @return String []
	 */
	public static String [] getPropertiesDisplayName(JNIProperty [] properties, boolean checkVisiblity){
		return getPropertiesDisplayName(Arrays.asList(properties), checkVisiblity);
	}

	/**
	 * Method to fetch field property Display Names
	 * @param properties
	 * @return String []
	 */
	public static String [] getPropertiesDisplayName(Collection<? extends JNIProperty> properties){
		return getPropertiesDisplayName(properties, true);
	}

	/**
	 * Method to fetch field property names
	 * @param properties
	 * @param checkVisiblity
	 * @return String []
	 */
	public static String [] getPropertiesDisplayName(Collection<? extends JNIProperty> properties, boolean checkVisiblity){
		List<String> displayNames = new ArrayList<String>();
		for (JNIProperty property : properties) {
			if(checkVisiblity 
					&& (property.getVisibility() == JVisibilityType.COLLAPSED 
					|| JVisibilityType.VISIBLE == property.getVisibility())) {
				displayNames.add(property.getDisplayName());
			} else if (!checkVisiblity) {
				displayNames.add(property.getDisplayName());
			}
		}
		return displayNames.toArray(new String [1]);
	}


	/**
	 * Method to get property for name
	 * @param properties
	 * @param name
	 * @return {@link JNIProperty}
	 */
	public static JNIProperty getPropertyForName(JNIProperty [] properties, Object name){
		for (JNIProperty property : properties) {
			if(property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	/**
	 * Create default Layouts, from annotations
	 * @param layouts {@link JNILayout}
	 * @param columns
	 */
	public static void updateDefaultLayout(Collection<? extends JNILayout> layouts, int columns) {
		JNILayout earlierLayout = null;
		for (JNILayout layout : layouts) {
			if(layout.getProperty().getType() == JPropertyType.RICH_TEXT_AREA 
					|| layout.getProperty().getType() == JPropertyType.TEXT_AREA 
					|| layout.getProperty().getType() == JPropertyType.IMAGE) {

				if(earlierLayout != null 
						&& earlierLayout.getProperty().getType() == JPropertyType.RICH_TEXT_AREA 
						|| earlierLayout.getProperty().getType() == JPropertyType.TEXT_AREA
						|| earlierLayout.getProperty().getType() == JPropertyType.IMAGE) {
					
					layout.setColSpan(columns/2);
					earlierLayout.setColSpan(columns - layout.getColSpan());
					earlierLayout = null;
				} else {
					layout.setColSpan(columns);
				}
			} else if (layout.getGroup() != null) {
				if(layout.getGroup().getColumns() == -1)
					layout.getGroup().setColumns(columns);
				layout.getGroup().setColSpan(columns);
			}

			earlierLayout = layout;
		}
	}

	/**
	 * 
	 * @param person
	 * @param values
	 * @return String
	 */
	public static String getPojoState(Locale locale, Object bean, JNIProperty[] values) {
		StringBuilder builder = new StringBuilder();
		I18NProvider provider = DefaultI18NResourceProvider.instance();
		for (JNIProperty property : values) {
			builder.append(provider.getTitle(locale, property.getDisplayName()));
			builder.append(": ");
			Object object = getPropertyValue(property.getName(), bean);
			if (object != null) {
				if (object instanceof JNINamed) {
					String caption = ((JNINamed) object).getDisplayName();
					if (object.getClass().isEnum()) {
						String value = provider.getText(locale, caption);
						if(caption.equals(value))
							value = provider.getTitle(locale, caption);
						builder.append(value);
					} else {
						builder.append(caption);
					}
				} else {
					builder.append(object.toString());
				}
			}

			builder.append("<br/>");
		}

		builder.setLength(builder.length() - 5);
		return builder.toString();
	}

	/**
	 * 
	 * @param property
	 * @param obj
	 * @return
	 */
	public static Object getPropertyValue(String property, Object obj) {
		String [] properties = property.split("\\.");
		return getPropertyValue(obj, properties);
	}

	/**
	 * 
	 * @param object
	 * @param properties
	 * @return
	 */
	public static Object getPropertyValue(Object object, String[] properties) {
		Object obj = object;
		for (String property : properties) {
			obj = getValue(obj, property);
		}
		return obj;
	}

	/**
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object getValue(Object obj, String property) {
		if(property.equalsIgnoreCase("")){
			return obj;
		}
		String methodName = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
		try {
			if(obj == null)
				return obj;
			Method getterMethod = obj.getClass().getMethod(methodName);
			obj = getterMethod.invoke(obj);
		}
		catch (Exception e) {
			System.out.println("Error getting property value using getter method " + methodName + " of class " + obj.getClass().getName());
		} 
		return obj;
	}
}
