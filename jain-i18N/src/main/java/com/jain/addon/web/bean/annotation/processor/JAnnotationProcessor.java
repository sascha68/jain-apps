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
package com.jain.addon.web.bean.annotation.processor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jain.addon.StringHelper;
import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbedded;
import com.jain.addon.web.layout.JNGroup;
import com.jain.addon.web.layout.JNIGroup;
import com.jain.addon.web.layout.JNILayout;
import com.jain.addon.web.layout.JNLayout;

/**
 * <code>JAnnotationProcessor<code> process all the annotation provided for the UI generation.
 * @see {@link JNIAttribute} {@link JNIEmbedded} {@link JNIConstraint}
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class JAnnotationProcessor implements Serializable {
	private static JAnnotationProcessor instance;
	private Map<Class<?>, JProperties> propertiesMap;

	private JAnnotationProcessor() {
		this.propertiesMap = new HashMap<Class<?>, JProperties> ();
	}

	/**
	 * Returns instance of this {@link JAnnotationProcessor}
	 * @return {@link JAnnotationProcessor}
	 */
	public static JAnnotationProcessor instance() {
		if (instance == null) {
			synchronized (JAnnotationProcessor.class) {
				if (instance == null) {
					instance = new JAnnotationProcessor();
				}
			}
		}
		return instance;
	}

	/**
	 * Returns {@link JProperties} for the given type.
	 * @param type
	 * @return {@link JProperties}
	 */
	public JProperties getProperties(Class<?> type) {
		JProperties properties = propertiesMap.get(type);  

		if(properties == null){
			properties = processAnnotation(type);

			if(propertiesMap.size() > 50)
				propertiesMap.clear();

			propertiesMap.put(type, properties);
		}

		return properties;
	}

	private JProperties processAnnotation(Class <?> type) {
		JProperties properties = new JProperties();

		JNIEmbedded embaded = type.getAnnotation(JNIEmbedded.class);
		JNIGroup group = null;
		if (embaded != null && StringHelper.isNotEmptyWithTrim(embaded.lable())) {
			group = new JNGroup (embaded, null);
		}
		processAnnotation(type, properties, "", group, null);
		return properties;
	}

	private void processAnnotation(Class<?> type, JProperties properties, String initials, JNIGroup group, JNIGroup parentGroup) {
		Method[]  methods = type.getMethods();

		for (Method method : methods) {
			String propertyName = StringHelper.methodToPropertyName(method.getName());

			if(StringHelper.isNotEmptyWithTrim(initials))
				propertyName = initials + "." + propertyName;

			JNIEmbedded embaded = method.getAnnotation(JNIEmbedded.class);

			if (embaded != null) {
				JNIGroup jniGroup = group;

				if(StringHelper.isNotEmptyWithTrim(embaded.lable())) {
					jniGroup = new JNGroup (embaded, parentGroup);
					Class <?> embadedType = method.getReturnType();
					if (embadedType != null) {
						embaded = embadedType.getAnnotation(JNIEmbedded.class);
						JNIGroup grp = jniGroup;	
						if (embaded != null && StringHelper.isNotEmptyWithTrim(embaded.lable())) {
							jniGroup = new JNGroup (embaded, grp);
						}
					}
				}
				processAnnotation (method.getReturnType(), properties, propertyName, jniGroup,  jniGroup);
			}

			JNIAttribute attribute = method.getAnnotation(JNIAttribute.class);

			if(attribute != null) {
				JNIProperty property = createProperty (attribute, propertyName);
				int position = properties.addProperty(property);

				JNIConstraint constraint = method.getAnnotation(JNIConstraint.class);
				JNIPropertyConstraint propertyConstraint = createPropertyConstraint (constraint, property);
				properties.addConstraint(propertyConstraint, position);

				JNILayout layout = new JNLayout(property, group);
				properties.addLayout(layout, position);
			}
		}
	}

	private JNIPropertyConstraint createPropertyConstraint(JNIConstraint constraint, JNIProperty property) {
		JNConstraint constrt = new JNConstraint();
		constrt.setProperty(property);
		if (constraint != null) {
			constrt.setTypes(constraint.types());
			constrt.setEnumarationName(constraint.enumarationName());
			constrt.setWidth(constraint.width());
		} else {
			constrt.setTypes(JConstraintType.NONE);
			constrt.setEnumarationName("");
			constrt.setWidth("100%");
		}
		return constrt;
	}

	private JNIProperty createProperty(JNIAttribute attribute, String name) {
		JNProperty property = new JNProperty ();
		property.setName(name);
		property.setDisplayName(attribute.lable());
		property.setDescription(attribute.lable());
		property.setType(attribute.type());
		property.setVisibility(attribute.visibility());
		property.setOrder(attribute.order());
		return property;
	}
}
