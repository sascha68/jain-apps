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

import java.io.ByteArrayOutputStream;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>JImageUpload<code> default Image unloader
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JImageUpload extends VerticalLayout implements SucceededListener, StartedListener {
	private ByteArrayOutputStream stream;
	private JFileReceiver receiver;
	private Upload upload;
	private JImage jImage;
	private JProgressIndicator pi;

	public JImageUpload(JImage jImage) {
		this.jImage = jImage;

		setWidth("100%");
		setMargin(true, false, true, false);
		setStyleName("image-upload");
		
		upload = new Upload();
		upload.setImmediate(true);
		upload.setButtonCaption("Upload");

		addComponent(upload);
		stream = new ByteArrayOutputStream(10240);
		stream.reset();
		
		pi = new JProgressIndicator();
		upload.addListener(pi);
		addComponent(pi);
		pi.setVisible(false);

		receiver = new JFileReceiver(stream);
		upload.setReceiver(receiver);

		upload.addListener((SucceededListener) this);
		upload.addListener((StartedListener) this);
	}

	public Object getValue() {
		return stream.toByteArray();
	}

	public ByteArrayOutputStream getStream() {
		return stream;
	}

	public void setStream(ByteArrayOutputStream stream) {
		this.stream = stream;
	}

	protected void interruptUpload() {
		upload.interruptUpload();
		Notification n = new Notification("Larger Size", Notification.TYPE_TRAY_NOTIFICATION);
		n.setPosition(Notification.POSITION_CENTERED);
		n.setDescription("Please provide file with smaller size");
		n.show(getRoot().getPage());
	}

	public void uploadStarted(StartedEvent event) {
		pi.setVisible(true);
		upload.setVisible(false);
		long l = event.getContentLength();
		if(l > 1000 * 1000 * 3) {
			interruptUpload();
		} else {
			getStream().reset();
			jImage.createImage();
		}
	}

	public void uploadSucceeded(SucceededEvent event) {
		final ByteArrayOutputStream stream = getStream();
		jImage.updateImage(stream.toByteArray(), receiver.getFileName());
		pi.setVisible(false);
		upload.setVisible(true);
	}
}
