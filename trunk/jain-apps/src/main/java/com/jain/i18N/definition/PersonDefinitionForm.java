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

import java.util.Arrays;

import com.jain.addon.event.Events;
import com.jain.addon.i18N.component.I18NWindow;
import com.jain.addon.web.JNIComponent;
import com.jain.addon.web.bean.container.JainBeanItem;
import com.jain.addon.web.field.JFieldGroup;
import com.jain.addon.web.marker.JNIEditLocal;
import com.jain.common.JAction;
import com.jain.common.VaadinHelper;
import com.jain.common.listeners.JainEditClickListener;
import com.jain.i18N.annotation.PersonAnnotationGrid;
import com.jain.i18N.domain.Address;
import com.jain.i18N.domain.Person;
import com.jain.i18N.domain.ZIP;
import com.jain.theme.ApplicationTheme;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonDefinitionForm extends I18NWindow implements JNIEditLocal, JNIComponent {
	private Person person;
	private FieldGroup fieldGroup;
	private boolean viewOnly;

	public PersonDefinitionForm() {
		this.viewOnly = false;
	}

	public void init () {
		setModal(true);
		setWidth("70%");

		VerticalLayout layout = new VerticalLayout();
		layout.setWidth("100%");
		layout.setMargin(false, false, true, false);
		layout.setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		setContent(layout);
		createFieldGroup(layout);

		createActions(layout);
	}

	private void createActions(VerticalLayout layout) {
		if(!viewOnly) {
			JainEditClickListener clickListner = new JainEditClickListener(this);
			HorizontalLayout hLayout = VaadinHelper.createButtonSegment (clickListner, JAction.SAVE, JAction.CANCEL);

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeUndefined();
			vLayout.setStyleName(ApplicationTheme.VIEW);
			vLayout.addComponent(hLayout);

			layout.addComponent(vLayout);
			layout.setComponentAlignment(vLayout, Alignment.MIDDLE_CENTER);
			layout.setExpandRatio(vLayout, 1);
		}
	}

	private void createFieldGroup(VerticalLayout layout) {
		JainBeanItem<Person> item = new JainBeanItem<Person>(getPerson ());
		JFieldGroup<Person> jainFieldGroup = new JFieldGroup<Person>(2, PersonPropertyConstraint.values());
		jainFieldGroup.setViewOnly (viewOnly);
		jainFieldGroup.setStyleName(ApplicationTheme.VIEW);
		jainFieldGroup.setAlternateStyleName(ApplicationTheme.ALTERNATE_VIEW);
		layout.addComponent(jainFieldGroup);

		fieldGroup = jainFieldGroup.createFieldGroup(Person.class, item, Arrays.asList(PersonPropertyConstraint.values())); 
	}

	public void save() {
		try {
			fieldGroup.commit();
			Events.instance().raiseEvent(PersonAnnotationGrid.PERSON_CREATED_OR_UPDATED, person);
			getRoot().removeWindow(this);
		} catch (CommitException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		fieldGroup.discard();
		getRoot().removeWindow(this);
	}

	private Person getPerson() {
		if (person == null) {
			person = new Person();
			person.setAddress(new Address(new ZIP()));
		}
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
}
