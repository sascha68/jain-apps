package com.jain.common.header;

import javax.inject.Inject;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.event.Events;
import com.jain.addon.i18N.I18NChangeEvent;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.i18N.I18NListener;
import com.jain.addon.i18N.component.I18NSelector;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.resource.I18NResourceProvider;
import com.jain.addon.web.JNIComponent;
import com.jain.addon.web.layout.segment.ButtonSegment;
import com.jain.addon.web.marker.authentication.JNILoginListner;
import com.jain.common.JAction;
import com.jain.common.JLocale;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.common.authenticate.LoginAction;
import com.jain.theme.ApplicationTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Runo;

@SuppressWarnings("serial")
public class WelcomeBar extends HorizontalLayout implements JNILoginListner, ClickListener, JNIComponent, I18NListener {
	@Inject
	private AuthenticatedUser currentUser;
	private Label welcomeMessage;

	public void init () {
		setWidth("100%");
		setMargin(false, true, false, true);
		setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		createWelcomeMessage();

		createRightActions();
	}

	private void createWelcomeMessage() {
		welcomeMessage = new Label (currentUser.getDisplayIdentity());
		welcomeMessage.setStyleName(ApplicationTheme.WELCOME_MESSAGE);
		addComponent(welcomeMessage);
	}

	private void createRightActions() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(false, true, false, true);
		I18NSelector localSelection = new I18NSelector();
		localSelection.setInputPrompt("module.locale.name");

		for (JLocale locale : JLocale.values()) {
			localSelection.addItem(locale.getLocale(), locale.getDisplayName());
		}

		layout.addComponent(localSelection);

		ButtonSegment segment = new ButtonSegment (Runo.BUTTON_LINK);
		segment.setSpacing(true);
		segment.createSegment(this, JAction.HELP, JAction.LOGIN, JAction.LOGOUT);
		layout.addComponent(segment);

		addComponent(layout);
		setComponentAlignment(layout, Alignment.TOP_RIGHT);
	}

	public void onLogin() {
		welcomeMessage.setValue(currentUser.getDisplayIdentity());
	}

	public void localeChanged(I18NChangeEvent event) {
		currentUser.setLocale(event.getLocale());
		I18NProvider provider = I18NResourceProvider.instance(); 
		getRoot().getPage().setTitle(provider.getText(getLocale(), "application.title.name"));
		welcomeMessage.setValue(currentUser.getDisplayIdentity());
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if(action != null) {
			switch (action) {
			case LOGIN :
				LoginAction loginAction = CDIComponent.getInstance(LoginAction.class);
				loginAction.setCaption(JAction.LOGIN.getDisplayName());
				getRoot().addWindow(loginAction);
				break;
			case LOGOUT :
				currentUser.setLoggedInUser(null);
				Events.instance().raiseLogoutEvent(getRoot());
				break;
			case HELP :
				System.out.println("not implemented yet");
				break;
			default:
				break;
			}
		}
	}
}
