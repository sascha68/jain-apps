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

import java.util.List;

import javax.inject.Inject;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.event.JNIObserver;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.container.JainBeanItemContainer;
import com.jain.addon.web.table.JTable;
import com.jain.i18N.PersonDataHandler;
import com.jain.i18N.domain.Person;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonAnnotationGrid  extends VerticalLayout implements ValueChangeListener, Command {
	public static final String PERSON_CREATED_OR_UPDATED = "personCreatedOrUpdated";
	private JTable table;
	private Person selected;
	
	@Inject PersonDataHandler dataHandler; 

	@JNIComponentInit
	public void init () {
		table = new JTable(getContainer(), getProperties());
		table.addValueChangeListener(this);
		table.setPageLength(15);
		addComponent(table);
		MenuBar menubar = new MenuBar();
		final MenuItem file = menubar.addItem("File", this);
		
		addComponent(menubar);
		
	}

	private Container getContainer() {
		JainBeanItemContainer <Person> container = new JainBeanItemContainer<Person>(Person.class);
		updateContainer(container);
		return container;
	}

	private void updateContainer(JainBeanItemContainer<Person> container) {
		List<Person> result = dataHandler.getPersons();
		if (result != null)
			container.addAll(result);
	}

	private JNIProperty[] getProperties() {
		return JAnnotationProcessor.instance().getProperties(Person.class).getProperties().toArray(new JNIProperty [0]);
	}

	@SuppressWarnings("unchecked")
	@JNIObserver(PERSON_CREATED_OR_UPDATED)
	public void reload(Person person) {
		JainBeanItemContainer<Person> container = (JainBeanItemContainer<Person>) table.getContainerDataSource();
		container.removeItem(person);
		container.addItem(person);
		dataHandler.removePerson(person);
		dataHandler.addPerson(person);
	}

	public void valueChange(ValueChangeEvent event) {
		selected = (Person) event.getProperty().getValue();
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		JainBeanItemContainer<Person> container = (JainBeanItemContainer<Person>) table.getContainerDataSource();
		container.removeItem(selected);
		dataHandler.removePerson(selected);
	}

	public Person getSelected() {
		return selected;
	}

	public void menuSelected(MenuItem selectedItem) {
		 getUI().showNotification("Action " + selectedItem.getText());
	}
}
