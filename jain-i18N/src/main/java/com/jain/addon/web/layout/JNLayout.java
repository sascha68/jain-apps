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
package com.jain.addon.web.layout;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.JNIAttribute;
import com.jain.addon.web.bean.annotation.JNIConstraint;
import com.jain.addon.web.bean.annotation.JNIEmbaded;

/**
 * <code>JNLayout<code> is a default implementation for the {@link JNILayout}. <br/>
 * This default implementation is populated using Annotation.
 * @see {@link JNIAttribute} {@link JNIEmbaded} {@link JNIConstraint} 
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JNLayout implements JNILayout {
	private JNIProperty property;
	private int colSpan;
	private JNIGroup group;

	/**
	 * Create an instance of the layout
	 */
	public JNLayout() {
	}

	/**
	 * Create an instance of the layout by passing {@link JNIProperty}
	 * @param property
	 */
	public JNLayout(JNIProperty property) {
		this(property, 0);
	}
	
	/**
	 * Create an instance of the layout by passing {@link JNIProperty} 
	 * and Column span for the property component.
	 * @param property
	 * @param colSpan
	 */
	public JNLayout(JNIProperty property, int colSpan) {
		this(property, colSpan, null);
	}
	
	/**
	 * Create an instance of the layout by passing {@link JNIProperty} 
	 * and {@link JNIGroup} for the property component.
	 * @param property
	 * @param group
	 */
	public JNLayout(JNIProperty property, JNIGroup group) {
		this(property, 0, group);
	}
	
	/**
	 * Create an instance of the layout by passing {@link JNIProperty}, 
	 * {@link JNIGroup} and Column span for the property component.
	 * @param property
	 * @param colSpan
	 * @param group
	 */
	public JNLayout(JNIProperty property, int colSpan, JNIGroup group) {
		this.property = property;
		this.colSpan = colSpan;
		this.group = group;
	}

	public JNIProperty getProperty() {
		return property;
	}

	public void setProperty(JNIProperty property) {
		this.property = property;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public JNIGroup getGroup() {
		return group;
	}

	public void setGroup(JNIGroup group) {
		this.group = group;
	}
}
