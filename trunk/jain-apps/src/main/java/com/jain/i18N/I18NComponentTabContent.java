package com.jain.i18N;

import com.jain.addon.cdi.CDIComponent;
import com.jain.addon.web.JNIComponent;
import com.jain.i18N.annotation.PersonAnnotationTabContent;
import com.jain.i18N.definition.PersonDefinitionTabContent;
import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class I18NComponentTabContent extends TabSheet implements JNIComponent {
	public void init() {
		PersonAnnotationTabContent personAnnotationTabContent = CDIComponent.getInstance(PersonAnnotationTabContent.class);
		addTab(personAnnotationTabContent, "annotaion.approach.name");
		
		PersonDefinitionTabContent definitionTabContent = CDIComponent.getInstance(PersonDefinitionTabContent.class);
		addTab(definitionTabContent, "definition.approach.name");
	}
}
