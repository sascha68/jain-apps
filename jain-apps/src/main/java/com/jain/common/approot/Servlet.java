package com.jain.common.approot;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@WebServlet(urlPatterns = "/*")
public class Servlet extends AbstractApplicationServlet {
    private static final long serialVersionUID = 2298654353565L;

	@Inject
    private Instance <RootProvider> applicationProvider;

    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        return applicationProvider.get();
    }

	protected Class<RootProvider>  getApplicationClass() throws ClassNotFoundException {
        return RootProvider.class;
    }
}