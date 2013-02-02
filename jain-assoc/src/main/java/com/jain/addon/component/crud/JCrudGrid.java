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
package com.jain.addon.component.crud;

import java.util.Collection;

import com.jain.addon.JNEventConstants;
import com.jain.addon.JNIComponentInit;
import com.jain.addon.event.Events;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.container.JainBeanItemContainer;
import com.jain.addon.web.table.JTable;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JCrudGrid<code> Grid for Crud component
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.1.0
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public class JCrudGrid <T>  extends VerticalLayout implements ValueChangeListener {
	private JTable table;
	private T selected;
	private Collection <T> values;
	private Class <T> type;
	private int pageLength = 5;
	
	@JNIComponentInit
	public void init () {
		table = new JTable(getContainer(), getProperties());
		table.addValueChangeListener(this);
		table.setPageLength(pageLength);
		addComponent(table);
	}

	private Container getContainer() {
		JainBeanItemContainer <T> container = new JainBeanItemContainer<T>(getType());
		updateContainer(container);
		return container;
	}

	private void updateContainer(JainBeanItemContainer<T> container) {
		if (values != null)
			container.addAll(values);
	}

	private JNIProperty[] getProperties() {
		return JAnnotationProcessor.instance().getProperties(getType()).getProperties().toArray(new JNIProperty [0]);
	}

	@SuppressWarnings("unchecked")
	public void reload(T value) {
		values.remove(value);
		values.add(value);
		JainBeanItemContainer<T> container = (JainBeanItemContainer<T>) table.getContainerDataSource();
		container.removeItem(value);
		container.addItem(value);
		Events.instance().raiseEvent(getUI(), type.getCanonicalName() + JNEventConstants.CREATED_OR_UPDATED, value);
	}

	@SuppressWarnings("unchecked")
	public void valueChange(ValueChangeEvent event) {
		selected = (T) event.getProperty().getValue();
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		values.remove(getSelected());
		JainBeanItemContainer<T> container = (JainBeanItemContainer<T>) table.getContainerDataSource();
		container.removeItem(selected);
		Events.instance().raiseEvent(getUI(), type.getCanonicalName() + JNEventConstants.DELETED, selected);
	}

	public T getSelected() {
		return selected;
	}
	
	public Collection<T> getValues() {
		return values;
	}

	public void setValues(Collection<T> values) {
		this.values = values;
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength > 0 ? pageLength : 5;
	}
}
