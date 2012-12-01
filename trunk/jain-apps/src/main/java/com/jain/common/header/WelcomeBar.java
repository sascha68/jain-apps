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
package com.jain.common.header;

import javax.inject.Inject;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.authentication.JNILoginListner;
import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.event.Events;
import com.jain.addon.i18N.I18NChangeEvent;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.i18N.I18NListener;
import com.jain.addon.i18N.component.I18NSelector;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.resource.I18NProvider;
import com.jain.addon.web.layout.segment.ButtonSegment;
import com.jain.common.JAction;
import com.jain.common.JLocale;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.common.authenticate.LoginAction;
import com.jain.theme.ApplicationTheme;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Runo;

@SuppressWarnings("serial")
public class WelcomeBar extends HorizontalLayout implements JNILoginListner, ClickListener, I18NListener {
	@Inject
	private AuthenticatedUser currentUser;
	private Label welcomeMessage;

	@JNIComponentInit
	public void init () {
		setWidth("100%");
		setMargin(new MarginInfo(false, true, false, true));
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
		layout.setMargin(new MarginInfo(false, true, false, true));
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
		I18NProvider provider = DefaultI18NResourceProvider.instance(); 
		getUI().getPage().setTitle(provider.getText(getLocale(), "application.title.name"));
		welcomeMessage.setValue(currentUser.getDisplayIdentity());
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if(action != null) {
			switch (action) {
			case LOGIN :
				LoginAction loginAction = CDIComponent.getInstance(LoginAction.class);
				loginAction.setCaption(JAction.LOGIN.getDisplayName());
				getUI().addWindow(loginAction);
				break;
			case LOGOUT :
				currentUser.setLoggedInUser(null);
				Events.instance().raiseLogoutEvent(getUI());
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
