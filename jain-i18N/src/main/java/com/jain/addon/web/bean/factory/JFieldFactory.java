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

import java.util.Locale;

import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.vaadin.ui.Field;


/**
 * <code>JFieldFactory<code> is a factory class to 
 * generate {@link Field} based on bean type, {@link JNIPropertyConstraint} and locale 
 * using given {@link I18NProvider}  
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JFieldFactory extends AbstractFieldFactory {
	private Locale locale;
	private I18NProvider provider;
	
	public JFieldFactory(Locale locale) {
		this.locale = locale;
		this.provider = I18NResourceProvider.instance(); 
	}
	
	protected String getCaption(JNIProperty property) {
		return property.getDisplayName();
	}

	protected String getRequiredError(JNIProperty property) {
		return provider.getTitle(locale, "common.something.required", provider.getTitle(locale, property.getDisplayName()));
	}

	protected String getDescription(JNIProperty property) {
		return provider.getMessage(locale, property.getDisplayName());
	}
}
