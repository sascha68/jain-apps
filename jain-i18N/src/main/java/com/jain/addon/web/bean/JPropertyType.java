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
package com.jain.addon.web.bean;

import java.util.Date;

import com.google.gwt.user.client.ui.RichTextArea;
import com.jain.addon.web.component.upload.JImage;
import com.vaadin.ui.Field;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * <code>JPropertyType<code> used to generate UI {@link Field} <br/>
 * <pre>
 * UN_SPECIFIED - Find out based on the property type 
 * (i.e. {@link String} it should be {@link TextField}, 
 * for {@link Date} it should be {@link PopupDateField} etc.)
 * TEXT_AREA - Generated component is {@link TextArea}.
 * RICH_TEXT_AREA - Generated component is {@link RichTextArea}.
 * MULTI_SELECT - Generated component is {@link ListSelect}.
 * IMAGE - Generated component is {@link JImage}.
 * DATE - Generated component is {@link PopupDateField}.
 * </pre> 
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public enum JPropertyType {
	UN_SPECIFIED,
	TEXT_AREA,
	RICH_TEXT_AREA,
	MULTI_SELECT,
	IMAGE,
	DATE,
	FILE;
}