package com.jain.common;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public final class VaadinLayoutHelper {
	private VaadinLayoutHelper() {
	}

	public static void styleLayout(AbstractOrderedLayout layout, String style, Component... components) {
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setWidth("100%");

		if (style != null)
			layout.setStyleName(style);

		for (Component component : components) {
			layout.addComponent(component);
		}
	}

	public static HorizontalLayout createHorizontalLayout(String style, Component... components) {
		HorizontalLayout layout = new HorizontalLayout();
		styleLayout(layout, style, components);
		return layout;
	}

	public static VerticalLayout createVerticalLayout(String style, Component... components) {
		VerticalLayout layout = new VerticalLayout();
		styleLayout(layout, style, components);
		return layout;
	}
	
	public static FormLayout createFormLayout(String style, Component... components) {
		FormLayout layout = new FormLayout();
		styleLayout(layout, style, components);
		return layout;
	}

	public static HorizontalLayout createHorizontalLayout(Component... components) {
		return createHorizontalLayout(null, components);
	}

	public static VerticalLayout createVerticalLayout(Component... components) {
		return createVerticalLayout(null, components);
	}
}
