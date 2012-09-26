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

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.helper.JainHelper;
import com.jain.addon.web.component.JStreamSource;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 * <code>JainColumnGenerator<code> is a default column generator for the {@link JTable}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JainColumnGenerator implements ColumnGenerator {
	private JNIProperty[] properties;

	public JainColumnGenerator(JNIProperty... properties) {
		this.properties = properties;
	}

	public Component generateCell(final Table source, final Object itemId, final Object columnId) {
		JNIProperty property = JainHelper.getPropertyForName(properties, columnId);
		
		if(property != null && property.getType() != null) {
			final Item item = source.getItem(itemId);
			final Property<?> itemProperty = item.getItemProperty(property.getName());
			
			switch (property.getType()) {
			case IMAGE:
				return createImage(itemProperty, source);
			default:
				break;
			}
		}
		return null;
	}

	private Component createImage(final Property<?> itemProperty, final Table source) {
		JStreamSource streamSource = new JStreamSource((byte[]) itemProperty.getValue());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String filename = "myfilename-" + df.format(new Date()) + ".png";
		StreamResource resource = new StreamResource(streamSource, filename);
		Embedded embedded = new Embedded("", resource);
		embedded.setHeight("20px");
		return embedded;
	}
	
	

	public JNIProperty[] getProperties() {
		return properties;
	}

	public void setProperties(JNIProperty[] properties) {
		this.properties = properties;
	}

	public void addGeneratedColumn(final Table source) {
		for (JNIProperty property : properties) {
			if(property.getType() != null) {
				switch (property.getType()) {
				case IMAGE:
					source.addGeneratedColumn(property.getName(), this);
					source.setColumnAlignment(property.getName(), Align.CENTER);
					break;
				default:
					break;
				}
			}
		}
	}
}
