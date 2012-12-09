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
			@JNActionGroup (name = "edit.action.group.name"),
			@JNActionGroup (name = "find.replace.action.group.name"),
			@JNActionGroup (name = "view.action.group.name"),})
public class ComponentTabContent extends VerticalLayout  {
	@JNIComponentInit
	public void init () {
		ActionMenuBar<ComponentTabContent> menuBar = new ActionMenuBar<ComponentTabContent>(null, this);
		addComponent(menuBar);
		setComponentAlignment(menuBar, Alignment.TOP_CENTER);
		setExpandRatio(menuBar, 1);
	}

	@JNAction (name = "open.file.action.name", actionGroup = "file.action.group.name")
	public void openFile() {
		callActionMessage("open.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "new.action.name", actionGroup = "file.action.group.name")
	public void newFile() {
		callActionMessage("new.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "close.action.name", actionGroup = "file.action.group.name")
	public void close() {
		callActionMessage("close.file.action.name", "file.action.group.name");
	}
	
	@JNAction (name = "file.action.name", actionGroup = "new.action.group.name")
	public void file() {
		callActionMessage("file.action.name", "new.action.group.name");
	}
	
	@JNAction (name = "folder.action.name", actionGroup = "new.action.group.name")
	public void folder() {
		callActionMessage("folder.action.name", "new.action.group.name");
	}

	private void callActionMessage(String actionName, String groupName) {
		Notification notification = VaadinHelper.createNotificationMessage("action.invoked.from", actionName, groupName);
		notification.show(getUI().getPage());
	}
	
	//	
	//	final MenuBar.MenuItem file = menubar.addItem("File", null);
	//    final MenuBar.MenuItem newItem = file.addItem("New", null);
	//    file.addItem("Open file...", menuCommand);
	//    file.addSeparator();
	//
	//    newItem.addItem("File", menuCommand);
	//    newItem.addItem("Folder", menuCommand);
	//    newItem.addItem("Project...", menuCommand);
	//
	//    file.addItem("Close", menuCommand);
	//    file.addItem("Close All", menuCommand);
	//    file.addSeparator();
	//
	//    file.addItem("Save", menuCommand);
	//    file.addItem("Save As...", menuCommand);
	//    file.addItem("Save All", menuCommand);
	//
	//    final MenuBar.MenuItem edit = menubar.addItem("Edit", null);
	//    edit.addItem("Undo", menuCommand);
	//    edit.addItem("Redo", menuCommand).setEnabled(false);
	//    edit.addSeparator();
	//
	//    edit.addItem("Cut", menuCommand);
	//    edit.addItem("Copy", menuCommand);
	//    edit.addItem("Paste", menuCommand);
	//    edit.addSeparator();
	//
	//    final MenuBar.MenuItem find = edit.addItem("Find/Replace", menuCommand);
	//
	//    // Actions can be added inline as well, of course
	//    find.addItem("Google Search", new Command() {
	//        public void menuSelected(MenuItem selectedItem) {
	//            getWindow().open(new ExternalResource("http://www.google.com"));
	//        }
	//    });
	//    find.addSeparator();
	//    find.addItem("Find/Replace...", menuCommand);
	//    find.addItem("Find Next", menuCommand);
	//    find.addItem("Find Previous", menuCommand);
	//
	//    final MenuBar.MenuItem view = menubar.addItem("View", null);
	//    view.addItem("Show/Hide Status Bar", menuCommand);
	//    view.addItem("Customize Toolbar...", menuCommand);
	//    view.addSeparator();
	//
	//    view.addItem("Actual Size", menuCommand);
	//    view.addItem("Zoom In", menuCommand);
	//    view.addItem("Zoom Out", menuCommand);
	//
	//    addComponent(menubar);
}
