package com.jain.common.header;

import com.jain.addon.web.JNINamedResourceVisible;
import com.jain.i18N.I18NComponentTabContent;
import com.jain.xls.comparator.XLSComparatorTabContents;
import com.jain.xml.editor.XMLEditorTabContents;
import com.vaadin.ui.Component;

public enum JHeaderItem implements JNINamedResourceVisible {
	I18N_COMPONENT ("i18n.components.name", I18NComponentTabContent.class),
	XML_EDITOR ("xml.editor.name", XMLEditorTabContents.class),
	XLS_COMPARATOR ("xls.comparator.name", XLSComparatorTabContents.class);
	
	private final String displayNameKey;
	private final String resource;
	private final Class <? extends Component> model;
	
	private JHeaderItem (final String displayName, final Class <? extends Component> model) {
		this(displayName, null, model);
	}
	
	private JHeaderItem (final String displayName, final String resource, final Class <? extends Component> model) {
		this.displayNameKey = displayName;
		this.resource = resource;
		this.model = model;
	}
	
	public String getName() {
		return displayNameKey;
	}

	public String getDisplayName() {
		return displayNameKey;
	}

	public String getDescription() {
		return getDisplayName();
	}

	public String getResource() {
		return resource;
	}
	
	public boolean isVisible() {
		return true;
	}
	
	public Class<? extends Component> getModel() {
		return model;
	}

	public static JHeaderItem getDisplayNameEquivlent(String displayName) {
		for (JHeaderItem item : JHeaderItem.values()) {
			if(item.getDisplayName() != null && item.getDisplayName().equals(displayName)) 
				return item;
		}
		return null;
	}
}
