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

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.JVisibilityType;

/**
 * <code>JNProperty<code> is a default implementation for property handling.
 * These properties are used by the framework for the UI generation.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JNProperty implements JNIProperty {
	private String name;
	private String displayName;
	private String description;
	private JPropertyType type;
	private JVisibilityType visibility;
	private int order;

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

	public JPropertyType getType() {
		return type;
	}

	public void setType(JPropertyType type) {
		this.type = type;
	}

	public JVisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(JVisibilityType visibility) {
		this.visibility = visibility;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
