package com.jain.common.header;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.web.JNIComponent;
import com.jain.theme.ApplicationTheme;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class Header extends HorizontalLayout implements ClickListener, JNIComponent {
	private static final String IMAGES_LOGO_PNG = "images/logo.png";
	private Button current = null;
	private Component currentComponent = null;

	public void init() {
		setMargin(true);
		setSpacing(true);
		setStyleName(ApplicationTheme.HEADER);
		setWidth("100%");
		addHeaderMenu();
		addLogo();
	}

	private void addHeaderMenu() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setStyleName(ApplicationTheme.HEADER_SEGMENT);

		int i = 0;
		Button button = null;

		for (JHeaderItem item : JHeaderItem.values()) {
			button = new Button(item.getDisplayName(), this);
			layout.addComponent(button);
			if (i == 0) {
				current = button;
				current.setStyleName(ApplicationTheme.FIRST);
				current.addStyleName(ApplicationTheme.SELECTED);
				i ++;
			}
		}
		button.setStyleName(ApplicationTheme.LAST);
		addComponent(layout);
		setComponentAlignment(layout, Alignment.MIDDLE_LEFT);
		setExpandRatio(layout, 1);
	}

	public void addDefaultTab() {
		findNCreateCurrentComponent();
		VerticalLayout contentLayout = (VerticalLayout) getRoot().getContent();
		contentLayout.setSpacing(false);
		contentLayout.setMargin(false);
		contentLayout.addComponent(currentComponent);
		contentLayout.setExpandRatio(currentComponent, 3);
	}

	private void addLogo() {
		Embedded em = new Embedded("", new ThemeResource(IMAGES_LOGO_PNG));
		addComponent(em);
		setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
		setExpandRatio(em, 1);
	}

	public void buttonClick(ClickEvent event) {
		Button selected = event.getButton(); 

		if(selected != current) {
			current.removeStyleName(ApplicationTheme.SELECTED);
			current = event.getButton(); 
			current.addStyleName(ApplicationTheme.SELECTED);

			VerticalLayout layout = (VerticalLayout) getRoot().getContent();

			if(currentComponent != null)
				layout.removeComponent(currentComponent);

			findNCreateCurrentComponent();

			if (currentComponent != null) {
				layout.setSpacing(false);
				layout.setMargin(false);
				layout.addComponent(currentComponent);
				layout.setExpandRatio(currentComponent, 3);
			}
		}
	}

	private void findNCreateCurrentComponent() {
		JHeaderItem item = JHeaderItem.getDisplayNameEquivlent(I18NHelper.getKey(current));
		if(item != null) {
			currentComponent = CDIComponent.getInstance(item.getModel());
		} else {
			currentComponent = null;
		}
	}
}
