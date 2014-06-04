package com.jain.i18N.component;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.action.ActionBar;
import com.jain.addon.action.ActionMenuBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.action.JNActionGroup;
import com.jain.addon.action.JNActionGroups;
import com.jain.addon.action.confirm.JNConfirm;
import com.jain.addon.component.upload.JImage;
import com.jain.common.VaadinHelper;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@JNActionGroups (actionGroups = {@JNActionGroup (name = "file.action.group.name"), 
			@JNActionGroup (name = "new.action.group.name", parent = "file.action.group.name"), 
			@JNActionGroup (name = "edit.action.group.name", icon = "edit.action.icon")})
public class ComponentTabContent extends VerticalLayout  {
	@JNIComponentInit
	public void init () {
		setMargin(true);
		setSpacing(true);
		setWidth("100%");
		setHeight("100%");
		
		Label label = new Label("menu.bar.component.name");
		addComponent(label);
		ActionMenuBar<ComponentTabContent> menuBar = new ActionMenuBar<ComponentTabContent>(null, this);
		addComponent(menuBar);
		setExpandRatio(menuBar, 2);
		
		label = new Label("action.bar.component.name");
		addComponent(label);
		ActionBar<ComponentTabContent> actionBar = new ActionBar<ComponentTabContent>(null, this);
		addComponent(actionBar);
		setExpandRatio(actionBar, 2);
		
		label = new Label("picture.title");
		addComponent(label);
		JImage image = new JImage();
		image.setInterruptionMessage("picture.upload.interruption");
		image.setUploadButtonCaption("picture.upload.button.caption");
		addComponent(image);
		setExpandRatio(image, 2);
	}

	@JNAction (name = "open.file.action.name", actionGroup = "file.action.group.name", tabIndex = 10)
	public void openFile() {
		callActionMessage("open.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "close.action.name", actionGroup = "file.action.group.name", tabIndex = 11)
	@JNConfirm(title = "confirm.window.title", message = "close.action.confirm.message", 
			icon = "confirm.window.message.icon")
	public void newFile() {
		callActionMessage("close.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "close.all.action.name", actionGroup = "file.action.group.name", tabIndex = 12, separator = true)
	@JNConfirm(title = "confirm.window.title", message = "close.all.action.confirm.message", 
			icon = "confirm.window.message.icon")
	public void close() {
		callActionMessage("close.all.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.action.name", actionGroup = "file.action.group.name", tabIndex = 13, icon="save.action.icon")
	public void save() {
		callActionMessage("save.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.as.action.name", actionGroup = "file.action.group.name", tabIndex = 14)
	public void saveAs() {
		callActionMessage("save.as.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.all.action.name", actionGroup = "file.action.group.name", tabIndex = 15)
	public void saveAll() {
		callActionMessage("save.all.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "file.action.name", actionGroup = "new.action.group.name", tabIndex = 1)
	public void file() {
		callActionMessage("file.action.name", "new.action.group.name");
	}
	
	@JNAction (name = "folder.action.name", actionGroup = "new.action.group.name", tabIndex = 2)
	public void folder() {
		callActionMessage("folder.action.name", "new.action.group.name");
	}
	
	@JNAction (name = "project.action.name", actionGroup = "new.action.group.name", tabIndex = 3)
	public void project() {
		callActionMessage("project.action.name", "new.action.group.name");
	}
	
	@JNAction (name = "undo.action.name", actionGroup = "edit.action.group.name", tabIndex = 20)
	public void undo() {
		callActionMessage("undo.action.name", "edit.action.group.name");
	}
	
	@JNAction (name = "redo.action.name", actionGroup = "edit.action.group.name", tabIndex = 21, separator = true)
	public void redo() {
		callActionMessage("redo.action.name", "edit.action.group.name");
	}
	
	@JNAction (name = "cut.action.name", actionGroup = "edit.action.group.name", tabIndex = 22)
	public void cut() {
		callActionMessage("cut.action.name", "edit.action.group.name");
	}
	
	@JNAction (name = "copy.action.name", actionGroup = "edit.action.group.name", tabIndex = 23)
	public void copy() {
		callActionMessage("copy.action.name", "edit.action.group.name");
	}
	
	@JNAction (name = "paste.action.name", actionGroup = "edit.action.group.name", tabIndex = 24)
	public void paste() {
		callActionMessage("paste.action.name", "edit.action.group.name");
	}
	
	private void callActionMessage(String actionName, String groupName) {
		Notification notification = VaadinHelper.createNotificationMessage("action.invoked.from", "action.invoked.from", actionName, groupName);
		notification.show(getUI().getPage());
	}
}
