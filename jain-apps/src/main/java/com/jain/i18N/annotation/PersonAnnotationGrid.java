package com.jain.i18N.annotation;

import java.util.List;

import javax.inject.Inject;

import com.jain.addon.event.JNIObserver;
import com.jain.addon.web.JNIComponent;
import com.jain.addon.web.bean.JNIProperty;
import com.jain.addon.web.bean.annotation.processor.JAnnotationProcessor;
import com.jain.addon.web.bean.container.JainBeanItemContainer;
import com.jain.addon.web.table.JTable;
import com.jain.i18N.PersonDataHandler;
import com.jain.i18N.domain.Person;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonAnnotationGrid  extends VerticalLayout implements ValueChangeListener, JNIComponent {
	public static final String PERSON_CREATED_OR_UPDATED = "personCreatedOrUpdated";
	private JTable table;
	private Person selected;
	
	@Inject PersonDataHandler dataHandler; 

	public void init () {
		table = new JTable(getContainer(), getProperties());
		table.addListener(this);
		table.setPageLength(15);
		addComponent(table);
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
}
