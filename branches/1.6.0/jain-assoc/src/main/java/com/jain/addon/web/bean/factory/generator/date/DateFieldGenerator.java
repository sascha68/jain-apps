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
package com.jain.addon.web.bean.factory.generator.date;

import java.util.Locale;

import com.jain.addon.resource.I18NProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.factory.generator.AbstractFieldGenerator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;

/**
 * <code>DateFieldGenerator<code> Used to generate Date PopupDateField 
 * @author Lokesh Jain
 * @since Nov 16, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class DateFieldGenerator  extends AbstractFieldGenerator {

	public DateFieldGenerator(Locale locale, I18NProvider provider) {
		super(locale, provider);
	}

	public Field <?> createField(Class<?> type, JNIProperty property) {
		final PopupDateField field = new PopupDateField();
		field.setResolution(Resolution.DAY);
		field.setInputPrompt(getCaption(property));
		return field;
	}
}
