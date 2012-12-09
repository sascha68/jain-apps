package com.jain.i18N.component;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.action.ActionMenuBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.action.JNActionGroup;
import com.jain.addon.action.JNActionGroups;
import com.jain.common.VaadinHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@JNActionGroups (actionGroups = {@JNActionGroup (name = "file.action.group.name"), 
			@JNActionGroup (name = "new.action.group.name", parent = "file.action.group.name"), 
			@JNActionGroup (name = "edit.action.group.name")})
public class ComponentTabContent extends VerticalLayout  {
	@JNIComponentInit
	public void init () {
		ActionMenuBar<ComponentTabContent> menuBar = new ActionMenuBar<ComponentTabContent>(null, this);
		addComponent(menuBar);
		setComponentAlignment(menuBar, Alignment.TOP_CENTER);
		setExpandRatio(menuBar, 1);
	}

	@JNAction (name = "open.file.action.name", actionGroup = "file.action.group.name", tabIndex = 10)
	public void openFile() {
		callActionMessage("open.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "close.action.name", actionGroup = "file.action.group.name", tabIndex = 11)
	public void newFile() {
		callActionMessage("close.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "close.all.action.name", actionGroup = "file.action.group.name", tabIndex = 12, separator = true)
	public void close() {
		callActionMessage("close.all.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.action.name", actionGroup = "file.action.group.name", tabIndex = 13)
	public void save() {
		callActionMessage("save.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.as.action.name", actionGroup = "file.action.group.name", tabIndex = 14)
	public void saveAs() {
		callActionMessage("save.as.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "save.all.action.name", actionGroup = "file.action.group.name", tabIndex = 15)
	public void saveAll() {
		callActionMessage("save.all.file.action.name", "file.action.group.name");
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
