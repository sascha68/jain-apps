/* 
 * Copyright 2012
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
package com.jain.addon.web.bean.container;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbedded;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.annotation.processor.JProperties;
import com.jain.addon.web.bean.helper.JainBeanPropertyHelper;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;

/**
 * <code>JainBeanItem<code> is an extension of {@link BeanItem} to support POJO association. <br/>
 * Most of the time we are having POJO with HAS-A relationship, 
 * to support this relation Vaadin came up with a way called field group 
 * but still we need to pass each bean item instance in the field factory. <br/>
 * This class handles HAS-A relation ship for all the objects.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 * @param <BT>
 */
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class JainBeanItem<BT> extends BeanItem<BT> {

	/**
	 * Create Bean item instance by passing only bean object
	 * In this case bean class should be annotated with the annotations.
	 * @see {@link JNIAttribute} {@link JNIEmbedded} {@link JNIConstraint}
	 * @param bean
	 */
	public JainBeanItem(BT bean) {
		super(bean, new ArrayList<String>());
		JProperties properties = JAnnotationProcessor.instance().getProperties(bean.getClass());
		createProperties(bean, properties.getProperties());
	}
	
	/**
	 * Create ben instance by passing bean object and {@link Collection} of {@link JNIProperty}.
	 * @param bean
	 * @param properties
	 */
	public JainBeanItem(BT bean, Collection<? extends JNIProperty> properties) {
		super(bean, new ArrayList<String>());
		createProperties(bean, properties);
	}

	/**
	 * Create ben instance by passing bean object and Array of {@link JNIProperty}.
	 * @param bean
	 * @param properties
	 */
	public JainBeanItem(BT bean, JNIProperty[] properties) {
		this(bean, Arrays.asList(properties));
	}
	
	private void createProperties(BT bean, Collection<? extends JNIProperty> properties) {
		JainBeanPropertyHelper helper = new JainBeanPropertyHelper();

		//Create bean information
		LinkedHashMap<String, PropertyDescriptor> pds = helper.getPropertyDescriptors(bean, properties);

		//Add all the bean properties as MethodProperties to this Item
		for (JNIProperty property : properties) {
			PropertyDescriptor pd = pds.get(property.getName());
			if (pd != null) {
				final Method getMethod = pd.getReadMethod();
				final Method setMethod = pd.getWriteMethod();
				final Class<?> type = pd.getPropertyType();
				final Object objectBean = helper.getBeanObject(property.getName(), bean); 
				final Property p = new MethodProperty(type, objectBean, getMethod, setMethod);
				addItemProperty(property.getName(), p);
			}
		}
	}
}
