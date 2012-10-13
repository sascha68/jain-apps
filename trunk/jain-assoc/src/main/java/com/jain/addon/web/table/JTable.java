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

import com.jain.addon.JNINamed;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.helper.JainHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;

/**
 * 
 * <code>JTable<code> is a extension of {@link Table} component.
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JTable extends Table {
	private Container container;
	private JNIProperty [] properties;
	
	/**
	 * Create instance of component by passing container and {@link JNIProperty} collection 
	 * @param container
	 * @param properties
	 */
	public JTable(Container container, JNIProperty ... properties) {
		this.container = container;
		this.properties = properties;
		init ();
	}

	public void init() {
		setContainerDataSource(container);
		setStyleName(Runo.TABLE_SMALL);
		setSizeFull();
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		setVisibleColumns(JainHelper.getProperties(properties, false));
		setColumnHeaders(JainHelper.getPropertiesDisplayName(properties, false));
		setNullSelectionAllowed(false);
		
		setSelectable(true);
		setImmediate(true);
		setColumnReorderingAllowed(true);

		JainColumnGenerator columnGenerator = new JainColumnGenerator(properties);
		columnGenerator.addGeneratedColumn(this);	
		
		setItemDescriptionGenerator(new JainItemDescriptionGenerator(getLocale(), properties));
		
		updateTableProperty();
	}
	
	private void updateTableProperty() {
		for (JNIProperty property : properties) {
			setColumnAlignment(property.getName(), Align.CENTER);
			switch (property.getVisibility()) {
			case COLLAPSED:
				setColumnCollapsed(property.getName(), true);
				break;
			case NO_COLLAPSING:
				setColumnCollapsed(property.getName(), false);
				break;
			default:
				break;

			}
		}
	}

	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property<?> itemProperty) {
		JNIProperty property = JainHelper.getPropertyForName(properties, colId);

		if(property != null) { 
			if (property.getType() != null) {
				switch (property.getType()) {
				case RICH_TEXT_AREA:
				case TEXT_AREA:
				case UN_SPECIFIED:
				case MULTI_SELECT:
					return formatText(itemProperty);
				case DATE:
					return formatDate(itemProperty);
				default:
					break;
				}
			}
		}
		return super.formatPropertyValue(rowId, colId, itemProperty);
	}
	
	private String formatText(final Property<?> itemProperty) {
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
				return content.substring(0, 20) + " ..."; 
			return content.toString();
		}
		return null;
	}
	
	private String formatDate(final Property<?> itemProperty) {
		if(itemProperty.getValue() != null) {
			SimpleDateFormat df = new SimpleDateFormat("MMMMM dd, yyyy hh:mm");
            return df.format((Date)itemProperty.getValue());
		}
		return null;
	}
}
