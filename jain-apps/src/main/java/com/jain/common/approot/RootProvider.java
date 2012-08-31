package com.jain.common.approot;

import javax.inject.Inject;

import com.vaadin.Application;
import com.vaadin.RootRequiresMoreInformationException;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;

public class RootProvider extends Application {
	private static final long serialVersionUID = 45678369L;

	@Inject
	private ApplicationRoot rootProvider;

	protected Root getRoot(WrappedRequest request) throws RootRequiresMoreInformationException {
		return rootProvider;
	}
}