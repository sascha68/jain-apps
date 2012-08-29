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
package com.jain.addon.i18N;

import java.io.Serializable;
import java.util.Collection;

import com.jain.addon.web.listners.JAttachDetachListner;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Root;
import com.vaadin.ui.ComponentContainer.ComponentAttachEvent;

/**
 * <code>I18NHelper<code> is a helper class for the i18N component handling.
 * This is also having couple of helping methods.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class I18NHelper implements Serializable {
	private I18NHelper () {}

	/**
	 * Method to get i18N caption for given component 
	 * @param {@link Component}
	 * @return i18NCaption
	 */
	public static String getKey (Component component) {
		I18NChangeListener listener =  findListener(component, false);
		if (listener != null) 
			return listener.getI18NCaption(component);
		return component.getCaption();
	}

	/**
	 * Register component for i18N handling.
	 * @param {@link Component}
	 * @param {@link Root}
	 */
	public static void register(Root root, Component component) {
		I18NChangeListener listener =  findListener(root, true);
		listener.registor(component);
	}

	/**
	 * Remove registered component for i18N handling.
	 * @param {@link Component}
	 * @param {@link Root}
	 */
	public static void deRegistor(Root root, Component component) {
		I18NChangeListener listener =  findListener(root, true);
		listener.deRegistor(component);
	}

	/**
	 * Method to add Value change listener for a locale drop down 
	 * because complete i18N works with this listener.  
	 * @param {@link Root}
	 * @param {@link Field}
	 */
	public static void addListener (Root root, Field<?> field) {
		I18NChangeListener listener =  findListener(root, true);
		field.addListener(listener);
	}

	private static I18NChangeListener findListener (Component component, boolean create) {
		Collection<?> collection = component.getRoot().getListeners(ComponentAttachEvent.class);
		for (Object object : collection) {
			if (object instanceof JAttachDetachListner) {
				I18NChangeListener listener =  ((JAttachDetachListner) object).getListener();
				if (listener != null) {
					return listener;
				} 

				if (create) {
					listener = new I18NChangeListener();
					((JAttachDetachListner) object).setListener(listener);
					return listener;
				}
			}
		}
		return null;
	}
}
