package com.jain.common.listeners;

import java.io.Serializable;

import com.jain.addon.JNINamed;

public interface JNICrudLocal extends Serializable {
	JNINamed getSelected();
	void view ();
	void create ();
	void update ();
	void delete ();
}
