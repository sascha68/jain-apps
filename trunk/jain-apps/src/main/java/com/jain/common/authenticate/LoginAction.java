package com.jain.common.authenticate;

import javax.inject.Inject;

import com.jain.addon.event.Events;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.i18N.component.I18NWindow;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.addon.web.JNIComponent;
import com.jain.addon.web.layout.segment.ButtonSegment;
import com.jain.common.JAction;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LoginAction extends I18NWindow  implements ClickListener, JNIComponent {
	@Inject
	private AuthenticatedUser currentUser;
	
	private TextField userName;
	private PasswordField password;
	
	public void init () {
		setModal(true);
		setWidth("30%");

		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		
		layout.setWidth("100%");
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		createFieldGroup(layout);

		createActions(layout);
	}

	private void createActions(VerticalLayout layout) {
		ButtonSegment hLayout = new ButtonSegment (ApplicationTheme.FIRST, ApplicationTheme.LAST);
		hLayout.setStyleName(ApplicationTheme.HEADER_SEGMENT_SMALL);
		hLayout.createSegment(this, JAction.LOGIN, JAction.CANCEL);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeUndefined();
		vLayout.setStyleName(ApplicationTheme.VIEW);
		vLayout.addComponent(hLayout);

		layout.addComponent(vLayout);
		layout.setComponentAlignment(vLayout, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(vLayout, 1);
	}

	private void createFieldGroup(VerticalLayout layout) {
		I18NProvider provider = I18NResourceProvider.instance();
		
		userName = new TextField("user.name");
		userName.setCursorPosition(0);
		userName.setRequired(true);
		userName.setRequiredError(provider.getMessage(getLocale(), "common.something.required", "user.name.title", JAction.LOGIN.getDisplayName()));
		userName.setDescription("user.name");
		userName.setSizeFull();
		
		password = new PasswordField("password");
		password.setRequired(true);
		password.setRequiredError(provider.getMessage(getLocale(), "common.something.required", "password.title", JAction.LOGIN.getDisplayName()));
		password.setDescription("password");
		password.setSizeFull();
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setStyleName(ApplicationTheme.VIEW);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);
		verticalLayout.setWidth("100%");
		
		FormLayout formLayout = new FormLayout();
		formLayout.setStyleName(ApplicationTheme.ALTERNATE_VIEW);
		formLayout.setSpacing(true);
		formLayout.setMargin(true);
		formLayout.setWidth("100%");
		formLayout.addComponent(userName);
		formLayout.addComponent(password);
		
		verticalLayout.addComponent(formLayout);
		
		layout.addComponent(verticalLayout);
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if(action != null) {
			switch (action) {
			case LOGIN :
				userName.commit();
				password.commit();
				System.out.println(userName.getValue());
				System.out.println(password.getValue());
				System.out.println(this);
				currentUser.setLoggedInUser(userName.getValue());
				Events.instance().raiseLoginEvent(getRoot());
				getRoot().removeWindow(this);
				break;
			case CANCEL :
				getRoot().removeWindow(this);
				break;
			default:
				break;
			}
		}
	}
}
