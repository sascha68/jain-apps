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

import com.jain.addon.resource.I18NProvider;
import com.jain.addon.web.bean.annotation.JNIEmbaded;

/**
 * <code>JNGroup<code> is a default implementation for the {@link JNIGroup}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JNGroup implements JNIGroup {
	private String name;
	private String displayName;
	private String description;
	private JNIGroup parent;
	private int columns;
	private int columnSpan;

	/**
	 * Create a default instance
	 */
	public JNGroup() {
	}

	/**
	 * Create a instance from the {@link JNIEmbaded} annotation
	 * @param embaded
	 * @param parent
	 */
	public JNGroup(JNIEmbaded embaded, JNIGroup parent) {
		this.parent = parent;
		this.name = embaded.lable();
		this.displayName = embaded.lable() + I18NProvider.TITLE_KEY;
		this.description = embaded.lable() + I18NProvider.MESSAGE_KEY;
		this.columns = embaded.columns();
		this.columnSpan = -1;
	}

	/**
	 * Create a instance by passing all the arguments
	 * @param name
	 * @param displayName
	 * @param description
	 * @param parent
	 * @param columns
	 * @param columnSpan
	 */
	public JNGroup(String name, String displayName, String description, JNIGroup parent, int columns, int columnSpan) {
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.parent = parent;
		this.columns = columns;
		this.columnSpan = columnSpan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JNIGroup getParent() {
		return parent;
	}

	public void setParent(JNIGroup parent) {
		this.parent = parent;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getColSpan() {
		return columnSpan;
	}

	public void setColSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}
}
