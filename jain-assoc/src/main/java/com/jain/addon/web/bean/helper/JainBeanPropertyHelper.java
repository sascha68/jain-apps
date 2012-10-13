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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jain.addon.web.bean.JNIProperty;

/**
 * <code>JainBeanPropertyHelper<code> is a helper class to handle all the properties for 
 * a bean to support POJO association (HAS-A relation). 
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JainBeanPropertyHelper implements Serializable {
	private transient final Map<String, Object> objectMap;
	private transient final Map<Class<?>, LinkedHashMap<String, PropertyDescriptor>> classPdsMap;

	public JainBeanPropertyHelper() {
		this.objectMap = new HashMap<String, Object>();
		this.classPdsMap = new HashMap<Class<?>, LinkedHashMap<String, PropertyDescriptor>>();
	}
	
	public LinkedHashMap<String, PropertyDescriptor> getPropertyDescriptors(Object bean, Collection<? extends JNIProperty> props) {
		final LinkedHashMap<String, PropertyDescriptor> pdMap = new LinkedHashMap<String, PropertyDescriptor>();
		try {
			for (JNIProperty prop : props) {
				String propertyId = prop.getName();
				Object beanObject = bean;
				if(propertyId.contains(".")){
					PropertyDescriptor pd = null;
					String [] properties = propertyId.split("\\.");
					for (String property : properties) { 

						if(bean instanceof Class){
							pd = getPropertyDescriptor((Class<?>)beanObject, property);
						}else {
							pd = getPropertyDescriptor(beanObject.getClass(), property);
						}

						final Method getMethod = pd.getReadMethod();
						Object value = objectMap.get(property);

						if(value == null){
							if(bean instanceof Class){
								value = getMethod.getReturnType();
							}else {
								value = getMethod.invoke(beanObject);
							}
						}

						if(value != null)
							objectMap.put(property, value);

						beanObject = value;
					}
					pdMap.put(propertyId, pd);
				}else{
					PropertyDescriptor pd = null;

					if(bean instanceof Class){
						pd = getPropertyDescriptor((Class<?>)beanObject, propertyId);
					}else {
						pd = getPropertyDescriptor(beanObject.getClass(), propertyId);
					}

					pdMap.put(propertyId, pd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdMap;
	}

	public PropertyDescriptor getPropertyDescriptor(final Class<?> type, final String propertyId) {
		final LinkedHashMap<String, PropertyDescriptor> pds = getPropertyDescriptors(type);
		return pds.get(propertyId);
	}

	public LinkedHashMap<String, PropertyDescriptor> getPropertyDescriptors(final Class<?> beanClass) {
		LinkedHashMap<String, PropertyDescriptor> pdMap = classPdsMap.get(beanClass); 
		if(pdMap == null){
			pdMap = new LinkedHashMap<String, PropertyDescriptor>();
			classPdsMap.put(beanClass, pdMap);
			try {
				final BeanInfo info = Introspector.getBeanInfo(beanClass);
				final PropertyDescriptor[] pds = info.getPropertyDescriptors();

				for (int i = 0; i < pds.length; i++) {
					final Method getMethod = pds[i].getReadMethod();
					if ((getMethod != null) && getMethod.getDeclaringClass() != Object.class) {
						pdMap.put(pds[i].getName(), pds[i]);
					}
				}
			} catch (final IntrospectionException ignored) {
			}
		}
		return pdMap;
	}

	public Object getBeanObject(String propertyId, Object bean) {
		if(propertyId.contains(".")){
			String property = propertyId.substring(0, propertyId.lastIndexOf("."));

			if(property.contains("."))
				property = propertyId.substring(propertyId.lastIndexOf(".") + 1);

			Object beanObject = objectMap.get(property);
			if(beanObject != null)
				return beanObject;
		}
		return bean;
	}
}
