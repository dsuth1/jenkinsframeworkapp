package com.securepaas.demo.parking;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class ParkingUIProvider extends UIProvider {
	private static final long serialVersionUID = -2815808716090516132L;

	@Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return ParkingUI.class;
    }

}
