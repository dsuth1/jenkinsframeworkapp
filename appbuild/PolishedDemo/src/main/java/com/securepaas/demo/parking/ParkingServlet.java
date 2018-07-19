package com.securepaas.demo.parking;

import javax.servlet.ServletException;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;

public class ParkingServlet extends TouchKitServlet {
	private static final long serialVersionUID = 5405245975670012223L;

	@Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        TouchKitSettings s = getTouchKitSettings();

        String contextPath = getServletConfig().getServletContext().getContextPath();
        s.getApplicationIcons().addApplicationIcon(contextPath + "/VAADIN/themes/parking/icon.png");
    }
}
