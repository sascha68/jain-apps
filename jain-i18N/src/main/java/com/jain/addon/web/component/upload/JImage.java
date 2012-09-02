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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jain.addon.web.component.JStreamSource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>JImage<code> is a Image custom component
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public class JImage extends CustomField<Object> {
	private static final long serialVersionUID = 7490297043002025899L;
	private Embedded image;
	private HorizontalLayout layout;
	private JImageUpload uploader;

	public void createImage() {
		if(image != null)
			layout.removeComponent(image);

		image = new Embedded();
		image.setWidth("100%");
		layout.addComponent(image, 0);
		layout.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
	}

	public Class<?> getType() {
		return byte [].class;
	}

	public Object getValue() {
		return uploader.getStream().toByteArray();
	}

	protected Object getInternalValue() {
		return uploader == null ? null : uploader.getStream() == null ? null : uploader.getStream().toByteArray();
	}

	public void updateImage(final byte [] imageData, String fileName) {
		JStreamSource source = new JStreamSource(new ByteArrayInputStream(imageData));
		image.setSource(new StreamResource(source, fileName, getApplication()));
		image.requestRepaint();
	}

	@Override
	protected Component initContent() {
		layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setWidth("100%");
		uploader = new JImageUpload(this);
		
		if (super.getInternalValue() != null) {
			createImage();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = "myfilename-" + df.format(new Date()) + ".png";
			updateImage((byte [])super.getInternalValue(), fileName);
		} 

		if (!isReadOnly()) {
			layout.addComponent(uploader);
			layout.setComponentAlignment(uploader, Alignment.MIDDLE_RIGHT);
		}
		return layout;
	}
}
