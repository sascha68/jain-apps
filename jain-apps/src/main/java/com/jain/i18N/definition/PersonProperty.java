/* 
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
package com.jain.i18N.definition;

import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.JVisibilityType;

public enum PersonProperty implements JNIProperty {
	NAME("name", "name"),
	GENDER("gender", "gender"),
	BIRTH_DATE("birthDate", "birth.date"),
	EMAIL("email", "email"),
	ADDRESS1("address.address1", "address", JPropertyType.TEXT_AREA, JVisibilityType.COLLAPSED), 
	STREET("address.street", "street"), 
	CITY("address.city", "city"),
	ZIP("address.zip.zip", "zip"),
	PICTURE("picture", "picture", JPropertyType.IMAGE, JVisibilityType.VISIBLE),
	DESCRIPTION("description", "description", JPropertyType.RICH_TEXT_AREA, JVisibilityType.COLLAPSED);
	
	private final String name;
	private final String displayName;
	private final String description;
	private final JVisibilityType visibilityType;
	private final JPropertyType type;
	
	private PersonProperty(String name, String displayName) {
		this(name, displayName, displayName);
	}
	
	private PersonProperty(String name, String displayName, JVisibilityType visibilityType) {
		this(name, displayName, displayName, visibilityType);
	}
	
	private PersonProperty(String name, String displayName, JPropertyType type, JVisibilityType visibilityType) {
		this(name, displayName, displayName, visibilityType, type);
	}
	
	private PersonProperty(String name, String displayName, String description) {
		this(name, displayName, description, JVisibilityType.VISIBLE);
	}

	private PersonProperty(String name, String displayName, String description, JVisibilityType visibilityType) {
		this(name, displayName, description, visibilityType, null);
	}
	
	private PersonProperty(String name, String displayName, String description, JPropertyType type) {
		this(name, displayName, description, JVisibilityType.VISIBLE, type);
	}
	
	private PersonProperty(String name, String displayName, String description, JVisibilityType visibilityType, JPropertyType type) {
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.visibilityType = visibilityType;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public JPropertyType getType() {
		return this.type;
	}

	public JVisibilityType getVisibility() {
		return visibilityType;
	}

	public int getOrder() {
		return ordinal();
	}
}
