package com.jain.i18N.annotation;

import com.jain.addon.JNINamed;
import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.web.JNIComponent;
import com.jain.common.JAction;
import com.jain.common.VaadinHelper;
import com.jain.common.listeners.JNICrudLocal;
import com.jain.common.listeners.JainCrudClickListener;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonAnnotationTabContent extends VerticalLayout implements JNICrudLocal, JNIComponent {
	private PersonAnnotationGrid grid;

	public void init () {
		setSpacing(false);
		setMargin(false);
		setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		JainCrudClickListener clickListner = new JainCrudClickListener(this);
		
		HorizontalLayout hLayout = VaadinHelper.createButtonSegment (clickListner, JAction.VIEW, JAction.ADD, JAction.EDIT, JAction.DELETE);
		
		addComponent(hLayout);
		setComponentAlignment(hLayout, Alignment.TOP_RIGHT);
		setExpandRatio(hLayout, 1);

		grid = CDIComponent.getInstance(PersonAnnotationGrid.class);
		addComponent(grid);
		setExpandRatio(grid, 3);
	}

	public void view() {
		PersonAnnotationForm form = CDIComponent.getInstance(PersonAnnotationForm.class);
		form.setViewOnly(true);
		form.setPerson(grid.getSelected());
		form.setCaption(JAction.VIEW.getDisplayName(getLocale(), grid.getSelected().getDisplayName()));
		getRoot().addWindow(form);
	}

	public void create() {
		PersonAnnotationForm form = CDIComponent.getInstance(PersonAnnotationForm.class);
		form.setCaption(JAction.ADD.getDisplayName(getLocale(), "person.name"));
		getRoot().addWindow(form);
	}

	public void update() {
		PersonAnnotationForm form = CDIComponent.getInstance(PersonAnnotationForm.class);
		form.setPerson(grid.getSelected());
		form.setCaption(JAction.EDIT.getDisplayName(getLocale(), grid.getSelected().getDisplayName()));
		getRoot().addWindow(form);
	}

	public void delete() {
		grid.delete();
	}

	public JNINamed getSelected() {
		return grid.getSelected();
	}
}
