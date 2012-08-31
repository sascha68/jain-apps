package com.jain.common.listeners;

import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.web.marker.JNIEditLocal;
import com.jain.common.JAction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public final class JainEditClickListener implements ClickListener {
	private JNIEditLocal listener;

	public JainEditClickListener(JNIEditLocal listener) {
		this.listener = listener;
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if(action != null) {
			switch (action) {
			case SAVE:
				listener.save();
				break;
			case CANCEL:
				listener.cancel();
				break;
			default:
				break;
			}
		}
	}
}
