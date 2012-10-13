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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.bean.helper.JainHelper;
import com.jain.addon.web.layout.JNILayout;

/**
 * <code>JProperties<code> handles all the properties generated after the annotation processing.<br/>
 * @see {@link JNIProperty} {@link JNIPropertyConstraint} {@link JNILayout}
 * @see {@link JNProperty} {@link JNConstraint} {@link JNLayout}
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JProperties implements Serializable {
	private List<JNIProperty> properties;
	private List<JNIPropertyConstraint> propertyConstraints;
	private List<JNILayout> layouts;

	public Collection<JNIProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<JNIProperty> properties) {
		this.properties = properties;
	}

	public int addProperty(JNIProperty property) {
		if (this.properties == null)
			this.properties = new ArrayList<JNIProperty>();
		int position = findPosition(property);
		this.properties.add(position, property);
		return position;
	}

	public List<JNIPropertyConstraint> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<JNIPropertyConstraint> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}

	public void addConstraint(JNIPropertyConstraint constraint, int position) {
		if (this.propertyConstraints == null)
			this.propertyConstraints = new ArrayList<JNIPropertyConstraint> ();
		this.propertyConstraints.add(position, constraint);
	}

	public List<JNILayout> getLayouts() {
		return layouts;
	}

	public void setLayouts(List<JNILayout> layouts) {
		this.layouts = layouts;
	}

	public void addLayout(JNILayout layout, int position) {
		if (this.layouts == null)
			this.layouts = new ArrayList<JNILayout>();
		this.layouts.add(position, layout);
	}
	
	public List<JNILayout> getLayouts(int columns) {
		JainHelper.updateDefaultLayout(layouts, columns);
		return layouts; 
	}

	private int findPosition(JNIProperty property) {
		int i = 0;
		for (JNIProperty prop : this.properties) {
			if (prop.getOrder() < property.getOrder())
				i ++;
		}
		return i;
	}
}
