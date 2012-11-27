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
package com.jain.i18N.annotation;

import javax.inject.Inject;

import com.jain.addon.JNEventConstants;
import com.jain.addon.JNIComponentInit;
import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.component.crud.JCrud;
import com.jain.addon.component.crud.JCrudObject;
import com.jain.addon.event.JNIObserver;
import com.jain.common.VaadinHelper;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.i18N.PersonDataHandler;
import com.jain.i18N.domain.Address;
import com.jain.i18N.domain.Person;
import com.jain.i18N.domain.ZIP;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonAnnotationTabContent extends VerticalLayout {
	private static final String PERSON_CREATED_OR_UPDATED = "com.jain.i18N.domain.Person" + JNEventConstants.CREATED_OR_UPDATED;
	private static final String PERSON_DELETED = "com.jain.i18N.domain.Person" + JNEventConstants.DELETED;

	private JCrud<Person> personCrud;
	
	@Inject private PersonDataHandler dataHandler; 
	
	@JNIComponentInit
	public void init () {
		setSpacing(false);
		setMargin(false);
		setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		personCrud = new JCrud<Person>();
		personCrud.setValues(dataHandler.getPersons());
		
		JCrudObject<Person> type = new JCrudObject<Person>(Person.class, "");
		JCrudObject<Address> subType  = type.addSubObject(Address.class, "setAddress");
		subType.addSubObject(ZIP.class, "setZip");
		personCrud.setType(type);
		personCrud.setPageLength(15);
		personCrud.setColumns(2);
		personCrud.setDisplayName("person.name");
		personCrud.setSecured(CDIComponent.getInstance(AuthenticatedUser.class));
		addComponent(personCrud);
	}
	
	@JNIObserver(PERSON_CREATED_OR_UPDATED)
	public void createUpdateObserver(Person person) {
		Notification notification = VaadinHelper.createNotificationMessage("person.object.created.updated", "person.object.created.updated", person.getDisplayName());
		notification.show(getUI().getPage());
	}
	
	@JNIObserver(PERSON_DELETED)
	public void deletedObserver(Person person) {
		Notification notification = VaadinHelper.createNotificationMessage("person.object.deleted", "person.object.deleted", person.getDisplayName());
		notification.show(getUI().getPage());
	}
}
