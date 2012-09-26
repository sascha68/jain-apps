package com.jain.xml.editor;

import com.jain.addon.web.JNIComponent;
import com.jain.common.VaadinLayoutHelper;
import com.jain.theme.ApplicationTheme;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class XMLEditorFrom extends VerticalLayout implements JNIComponent {
	
	public void init() {
		setSpacing(true);
		setMargin(true);
		setStyleName(ApplicationTheme.ALTERNATE_VIEW);

		//VerticalLayout editorFormLayout = createEditorForm();
		VerticalLayout descriptionLayout = addDescription();

		HorizontalLayout layout = VaadinLayoutHelper.createHorizontalLayout(descriptionLayout);//, editorFormLayout);
		addComponent(layout);

		//addButtonLayout(); 
	}
	
	private VerticalLayout addDescription() {
		VerticalLayout description = createDescription();
		VerticalLayout actionDescription = createActionDescription ();
		VerticalLayout sourceCodeDescription = createSourceCodeDescription ();
		return VaadinLayoutHelper.createVerticalLayout(ApplicationTheme.VIEW, description, actionDescription, sourceCodeDescription);

	}

	private VerticalLayout createDescription() {
		Label description = new Label("xml.editor.applications.description");
		description.setContentMode(ContentMode.HTML);
		VerticalLayout descriptionLayout = VaadinLayoutHelper.createVerticalLayout(ApplicationTheme.ALTERNATE_VIEW, description);
		return descriptionLayout;
	}

	private VerticalLayout createActionDescription() {
		Label rootTagDesc = new Label("xml.editor.file.root.tag.description");
		rootTagDesc.setContentMode(ContentMode.HTML);

		Label uploadFileDesc = new Label("xml.editor.upload.file.description");
		uploadFileDesc.setContentMode(ContentMode.HTML);

		Label xlsContentDesc = new Label("xml.editor.file.content.description");
		xlsContentDesc.setContentMode(ContentMode.HTML);

		VerticalLayout actionLayout = VaadinLayoutHelper.createVerticalLayout(ApplicationTheme.ALTERNATE_VIEW, rootTagDesc, uploadFileDesc, xlsContentDesc);
		return actionLayout;
	}

	private VerticalLayout createSourceCodeDescription() {
		Label mavenUrl = new Label("application.binary.maven.url.name:com.jain.apps.application.binary.maven.url");
		mavenUrl.setContentMode(ContentMode.HTML);

		Label sourceUrl = new Label("application.binary.source.url.name:com.jain.apps.application.binary.source.url");
		sourceUrl.setContentMode(ContentMode.HTML);

		HorizontalLayout layout = VaadinLayoutHelper.createHorizontalLayout(mavenUrl, sourceUrl);
		layout.setSpacing(false);
		layout.setMargin(false);

		Label mavenArchType = new Label("application.binary.maven.archtype.name:com.jain.apps.xml.editor.binary.maven.archtype");
		mavenArchType.setContentMode(ContentMode.HTML);

		VerticalLayout sourceLayout = VaadinLayoutHelper.createVerticalLayout(ApplicationTheme.ALTERNATE_VIEW, layout, mavenArchType);
		return sourceLayout;
	}
}
