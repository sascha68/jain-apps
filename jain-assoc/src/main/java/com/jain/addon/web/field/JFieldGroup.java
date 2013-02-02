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
package com.jain.addon.web.field;

import java.util.Arrays;
import java.util.Collection;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.JVisibilityType;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbedded;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.annotation.processor.JProperties;
import com.jain.addon.web.bean.factory.JFieldFactory;
import com.jain.addon.web.layout.JNILayout;
import com.jain.addon.web.layout.JainGroupLayout;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Field;

/**
 * <code>JFieldGroup<code> is used to create a {@link FieldGroup} and {@link JainGroupLayout} for the {@link Field}.    
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 * 
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public class JFieldGroup<T> extends JainGroupLayout {
	private Collection<? extends JNILayout> layouts;
	private boolean viewOnly;

	/**
	 * Create a {@link JFieldGroup} instance based on type and columns.
	 * @param type - used for default layout calculation
	 * @param columns - number of columns needs to be created in a form
	 */
	public JFieldGroup(Class<? extends T> type, int columns) {
		super(columns);
		this.layouts = JAnnotationProcessor.instance().getProperties(type).getLayouts(columns);;
	}
	
	/**
	 * Create a {@link JFieldGroup} instance based on {@link Collection} of {@link JNILayout} and columns.
	 * @param layouts - used for layout creation
	 * @param columns - number of columns needs to be created in a form
	 */
	public JFieldGroup(Collection<? extends JNILayout> layouts, int columns) {
		super(columns);
		this.layouts = layouts;
	}

	/**
	 * Create a {@link JFieldGroup} instance based on {@link Collection} of {@link JNILayout} and columns.
	 * @param layouts - used for layout creation
	 * @param columns - number of columns needs to be created in a form
	 */
	public JFieldGroup(int columns, JNILayout ... layouts) {
		this(Arrays.asList(layouts), columns);
	}

	/**
	 * Create a {@link FieldGroup} having all the fields annotated with {@link JNIAttribute} from the bean type.
	 * @param beanType - Used to calculate {@link JNIProperty} and {@link JNIPropertyConstraint} based on annotations
	 * @param item - Bean item having all these properties access logic.
	 * @return {@link FieldGroup}
	 * @see {@link JNIAttribute}, {@link JNIConstraint}, {@link JNIEmbedded}
	 */
	public FieldGroup createFieldGroup(Class<? extends T> beanType, Item item) {
		JProperties properties = JAnnotationProcessor.instance().getProperties(beanType);
		return createFieldGroup(beanType, item, properties.getPropertyConstraints());
	}

	/**
	 * Create a {@link FieldGroup} having all the fields form propertyConstraints for the bean type.
	 * @param propertyConstraints - Used to calculate {@link JNIProperty}
	 * @param item - Bean item having all these properties access logic.
	 * @return {@link FieldGroup}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FieldGroup createFieldGroup(Class<? extends T> beanType, Item item, Collection<? extends JNIPropertyConstraint> propertyConstraints) {
		FieldGroup fieldGroup = new BeanFieldGroup(beanType);
		fieldGroup.setItemDataSource(item);
		for (JNIPropertyConstraint propertyConstraint : propertyConstraints) {
			if(propertyConstraint.getProperty().getVisibility() != JVisibilityType.HIDDEN) {
				Class<?> type = item.getItemProperty(propertyConstraint.getProperty().getName()).getType();
				JFieldFactory fieldFactory = new JFieldFactory(getLocale());
				Field<?> field = fieldFactory.createField(type, propertyConstraint);
				fieldGroup.bind(field, propertyConstraint.getProperty().getName());
				attachField(propertyConstraint.getProperty().getName(), field);
			}
		}
		return fieldGroup;
	}
	
	protected void attachField(Object propertyId, Field<?> field) {
		if(viewOnly) {
			field.setReadOnly(viewOnly);
		}
		addComponent(field, findLayout(propertyId));
	}

	private JNILayout findLayout(Object propertyId) {
		if(layouts != null) {
			for (JNILayout layout : layouts) {
				if(propertyId.equals(layout.getProperty().getName())){
					return layout;
				}
			}
		}
		return null;
	}

	/**
	 * Is field group read only 
	 */
	public boolean isViewOnly() {
		return viewOnly;
	}

	/**
	 * Set if field group is read only before calling the createField group method
	 * @param viewOnly
	 */
	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
}
