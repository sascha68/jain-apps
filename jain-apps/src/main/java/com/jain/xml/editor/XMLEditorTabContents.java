package com.jain.xml.editor;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.cdi.CDIComponent;
import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class XMLEditorTabContents extends TabSheet {
	
	@JNIComponentInit
	public void init() {
		XMLEditorFrom xmlEditorFrom = CDIComponent.getInstance(XMLEditorFrom.class);
		addTab(xmlEditorFrom, "xml.editor.tab.name");
	}
}
