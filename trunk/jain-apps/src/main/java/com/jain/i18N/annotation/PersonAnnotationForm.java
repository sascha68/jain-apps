package com.jain.i18N.annotation;

import com.jain.addon.event.Events;
import com.jain.addon.i18N.component.I18NWindow;
import com.jain.addon.web.JNIComponent;
import com.jain.addon.web.bean.container.JainBeanItem;
import com.jain.addon.web.field.JFieldGroup;
import com.jain.addon.web.marker.JNIEditLocal;
import com.jain.common.JAction;
import com.jain.common.VaadinHelper;
import com.jain.common.listeners.JainEditClickListener;
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
public class PersonAnnotationForm extends I18NWindow implements JNIEditLocal, JNIComponent {
	private Person person;
	private FieldGroup fieldGroup;
	private boolean viewOnly;

	public PersonAnnotationForm() {
		this.viewOnly = false;
	}

	public void init () {
		setModal(true);
		setWidth("40%");

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
		JFieldGroup<Person> jainFieldGroup = new JFieldGroup<Person>(Person.class, 2);
		jainFieldGroup.setViewOnly (viewOnly);
		jainFieldGroup.setStyleName(ApplicationTheme.VIEW);
		jainFieldGroup.setAlternateStyleName(ApplicationTheme.ALTERNATE_VIEW);
		layout.addComponent(jainFieldGroup);

		fieldGroup = jainFieldGroup.createFieldGroup(Person.class, item); 
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
