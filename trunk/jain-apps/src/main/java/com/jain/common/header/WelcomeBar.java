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
import com.jain.addon.JNStyleConstants;
import com.jain.addon.action.ActionBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.action.JNActionGroup;
import com.jain.addon.authentication.JNILoginListner;
import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.event.Events;
import com.jain.addon.i18N.I18NChangeEvent;
import com.jain.addon.i18N.I18NListener;
import com.jain.addon.i18N.component.I18NSelector;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.resource.I18NProvider;
import com.jain.common.JAction;
import com.jain.common.JLocale;
import com.jain.common.authenticate.AuthenticatedUser;
import com.jain.common.authenticate.LoginAction;
import com.jain.theme.ApplicationTheme;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Runo;

@SuppressWarnings("serial")
@JNActionGroup (name = "welcome.bar.action", style = JNStyleConstants.J_ACTION_LINK_GROUP, 
	actionStyle=Runo.BUTTON_LINK, firstActionStyle = Runo.BUTTON_LINK, lastActionStyle = Runo.BUTTON_LINK)
public class WelcomeBar extends HorizontalLayout implements JNILoginListner, I18NListener {
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

		ActionBar <WelcomeBar> hLayout = new ActionBar <WelcomeBar> (currentUser, this);
		layout.addComponent(hLayout);

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


	//Actions
	@JNAction (name = "help.action.name", tabIndex = 1,  permission = "help.action.permission", 
			description = "help.action.description",  icon="help.action.icon")
	public void help() {
		System.out.println("not implemented yet");
	}

	@JNAction (name = "login.action.name", tabIndex = 2, permission = "login.action.permission", 
			description = "login.action.description", icon="login.action.icon")
	public void login() {
		LoginAction loginAction = CDIComponent.getInstance(LoginAction.class);
		loginAction.setCaption(JAction.LOGIN.getDisplayName());
		getUI().addWindow(loginAction);
	}

	@JNAction (name = "logout.action.name", tabIndex = 3, permission = "logout.action.permission", 
			description = "logout.action.description",  icon="logout.action.icon")
	public void logout() {
		currentUser.setLoggedInUser(null);
		Events.instance().raiseLogoutEvent(getUI());
	}
}
