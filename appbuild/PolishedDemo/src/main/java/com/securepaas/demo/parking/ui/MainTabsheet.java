package com.securepaas.demo.parking.ui;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet.Tab;

/**
 * This is the main view for Parking application. It displays a tabbar via one
 * can choose one of the sub views.
 */
public class MainTabsheet extends TabBarView {
	private static final long serialVersionUID = -2852353948538295758L;

	public MainTabsheet() {
        /*
         * Populate main views
         */
        TicketView ticketView = new TicketView();
        addTab(ticketView, "ticketstab", "Submit Ticket");

        addTab(new MapView(), "maptab", "Test Incidents");

        addTab(new ShiftsView(), "shiftstab", "Shifts");

        addTab(new StatsView(), "statstab", "Stats");

        setSelectedTab(ticketView);
    }

    private void addTab(final Component component, final String styleName,
            final String caption) {
        Tab tab = addTab(component);
        tab.setStyleName(styleName);
        tab.setCaption(caption);
    }

}
