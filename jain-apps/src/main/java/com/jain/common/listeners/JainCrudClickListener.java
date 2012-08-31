package com.jain.common.listeners;

import com.jain.addon.i18N.I18NHelper;
import com.jain.common.JAction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public final class JainCrudClickListener implements ClickListener {
	private JNICrudLocal listener;

	public JainCrudClickListener(JNICrudLocal listener) {
		this.listener = listener;
	}

	public void buttonClick(ClickEvent event) {
		JAction action = JAction.getDisplayNameEquivlent(I18NHelper.getKey(event.getButton()));
		if (action != null) {
			if(listener.getSelected() == null && action != JAction.ADD) {
				System.out.println("!!!!!!!!!!!!!!!!!!! Selection Required !!!!!!!!!!!!!!!!!!");
				return;
			} else { 
				switch (action) {
				case VIEW:
					listener.view();
					break;
				case ADD:
					listener.create();
					break;
				case EDIT:
					listener.update();
					break;
				case DELETE:
					listener.delete();
					break;
				default:
					break;
				}
			}
		}
	}
}
