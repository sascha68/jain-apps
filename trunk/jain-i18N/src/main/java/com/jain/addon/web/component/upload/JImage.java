/* 
 * Copyright 2012 Lokesh Jain.
 * 
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
package com.jain.addon.web.component.upload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * <code>JImage<code> is a Image custom component
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public class JImage extends CustomField<Object> implements ClickListener {
	private static final long serialVersionUID = 7490297043002025899L;
	private Embedded image;
	private VerticalLayout layout;
	private JImageUpload uploader;
	private Window subWindow;
	private String width = "100%";

	public void createImage() {
		if(image != null)
			layout.removeComponent(image);

		image = new Embedded();
		image.setWidth(width);
		image.addListener(this);
		layout.addComponent(image);
	}

	public Class<?> getType() {
		return byte [].class;
	}

	public Object getValue() {
		return uploader.getStream().toByteArray();
	}

	public void click(ClickEvent event) {
		subWindow = new Window("Upload Window");
		subWindow.addComponent(uploader);
		subWindow.center();
		layout.getRoot().addWindow(subWindow);
	}

	public void updateImage(final byte [] imageData, String fileName) {
		layout.getRoot().removeWindow(subWindow);
		layout.removeComponent(uploader);

		StreamSource source = new StreamSource() {
			private static final long serialVersionUID = -4905654404647215809L;
			public InputStream getStream() {
				return new ByteArrayInputStream(imageData);
			}
		};
		image.setSource(new StreamResource(source, fileName, getApplication()));
		image.requestRepaint();
	}
	
	@Override
	public void setWidth(String width) {
		this.width = width;
	}

	@Override
	protected Component initContent() {
		layout = new VerticalLayout();
		uploader = new JImageUpload(this);
		layout.addComponent(uploader);
		return layout;
	}
}
