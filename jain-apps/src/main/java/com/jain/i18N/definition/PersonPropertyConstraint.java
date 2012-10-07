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

import java.util.Collection;

import com.jain.addon.web.bean.JConstraintType;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.JNIPropertyConstraint;
import com.jain.addon.web.layout.JNGroup;
import com.jain.addon.web.layout.JNIGroup;
import com.jain.addon.web.layout.JNILayout;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;

public enum PersonPropertyConstraint implements JNIPropertyConstraint, JNILayout {
	NAME(PersonProperty.NAME, "100%", JConstraintType.getTypesList(JConstraintType.REQUIRED), 0),
	GENDER(PersonProperty.GENDER, "90%", 2),
	BIRTH_DATE(PersonProperty.BIRTH_DATE, "90%", JConstraintType.getTypesList(JConstraintType.REQUIRED), 3),
	EMAIL(PersonProperty.EMAIL, "100%", JConstraintType.getTypesList(JConstraintType.REQUIRED), 4, 
			new EmailValidator("Please JConstraintType proper Email address")),

	ADDRESS1(PersonProperty.ADDRESS1, "100%", JConstraintType.getTypesList(JConstraintType.REQUIRED), 5, null, 2), 
	STREET(PersonProperty.STREET, "100%", JConstraintType.getTypesList(JConstraintType.REQUIRED), 6), 
	CITY(PersonProperty.CITY, "100%", JConstraintType.getTypesList(JConstraintType.NONE), 7),
	ZIP(PersonProperty.ZIP, "100%", JConstraintType.getTypesList(JConstraintType.NONE), 8),
	PICTURE(PersonProperty.PICTURE, "100%", JConstraintType.getTypesList(JConstraintType.NONE), 9, null, 2),
	DESCRIPTION(PersonProperty.DESCRIPTION, "100%", JConstraintType.getTypesList(JConstraintType.NONE), 10, null, 2);


	private final JNIProperty property;
	private final String width;
	private final Collection<JConstraintType> types;
	private final int tabIndex;
	private final Validator validator;
	private final int colSpan;

	private PersonPropertyConstraint(JNIProperty property, String width,
			int tabIndex) {
		this(property, width, null, tabIndex, null);
	}

	private PersonPropertyConstraint(JNIProperty property, String width,
			Collection<JConstraintType> types, int tabIndex) {
		this(property, width, types, tabIndex, null);
	}

	private PersonPropertyConstraint(JNIProperty property, String width,
			Collection<JConstraintType> types, int tabIndex,
			Validator validator) {
		this(property, width, types, tabIndex, validator, 0);
	}

	private PersonPropertyConstraint(JNIProperty property, String width,
			Collection<JConstraintType> types, int tabIndex,
			Validator validator, int colSpan) {

		this.property = property;
		this.width = width;
		this.types = types;
		this.tabIndex = tabIndex;
		this.validator = validator;
		this.colSpan = colSpan;
	}

	public JNIProperty getProperty() {
		return property;
	}

	public String getWidth() {
		return width;
	}

	public Collection<JConstraintType> getTypes() {
		return types;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public Validator getValidator() {
		return validator;
	}

	public int getColSpan() {
		return colSpan;
	}

	public JNIGroup getGroup() {
		JNIGroup personDetails = createGroup("personal.details.group.name", 2, null);;
		if(tabIndex <= 4)
			return personDetails;
		JNIGroup group = createGroup("other.details.group.name", 2, null);
		if(getProperty() == PersonProperty.ADDRESS1)
			return createGroup("address.group.name", 1, group);
		else if (getProperty() == PersonProperty.DESCRIPTION)
			return createGroup("person.description.group.name", 2, personDetails);
		else if (getProperty() == PersonProperty.PICTURE)
			return createGroup("person.picture.group.name", 2, group);
		return createGroup("city.details.group.name", 1, group);
	}

	private JNIGroup createGroup(final String string, final int columns, final JNIGroup parent) {
		JNGroup group = new JNGroup(string, string, string, parent, columns, columns);
		return group;
	}

	public void setColSpan(int columnSpan) {
		
	}

	public String getEnumarationName() {
		return null;
	}
}
