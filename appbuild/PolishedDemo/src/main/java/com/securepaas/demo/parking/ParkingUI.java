package com.securepaas.demo.parking;

import com.securepaas.demo.parking.object.Location;
import com.securepaas.demo.parking.object.Ticket;
import com.securepaas.demo.parking.ui.MainTabsheet;
import com.securepaas.demo.parking.util.DataUtil;
import com.vaadin.addon.responsive.Responsive;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The UI class for Parking demo.
 */
@Theme("parking")
@Widgetset("com.securepaas.demo.parking.widgetset.ParkingWidgetset")
@Title("Vaadin Parking Demo")
public class ParkingUI extends UI {
	private static final long serialVersionUID = 7366391184898922657L;
    private double currentLatitude = Location.getDefaultLatitude();
    private double currentLongitude = Location.getDefaultLongitude();
    private BeanItemContainer<Ticket> ticketContainer;

    @Override
    public void init(VaadinRequest request) {
        // Set a nice default for user for demo purposes.
        String username="Guest";
        if(request.getUserPrincipal()!=null)
        	username=request.getUserPrincipal().getName();
        ticketContainer=DataUtil.initData();
        
        setContent(new MainTabsheet());
        new Responsive(this);
        showWelcomeNotification(username);
    }
    /**
     * The location information is stored in Application instance to be
     * available for all components. It is detected by the map view during
     * application init, but also used by other maps in the application.
     *
     * @return the current latitude as degrees
     */
    public double getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     * @return the current longitude as degrees
     * @see #getCurrentLatitude()
     */
    public double getCurrentLongitude() {
        return currentLongitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public static BeanItemContainer<Ticket> getTicketContainer()
    {
    	return getApp().ticketContainer;
    }
    
    /**
     * A typed version of {@link UI#getCurrent()}
     *
     * @return the currently active Parking UI.
     */
    public static ParkingUI getApp() {
        return (ParkingUI) UI.getCurrent();
    }
    
    private void showWelcomeNotification(String username) {
        Label label= new Label("Welcome "+username);

        VerticalLayout qrCodeLayout = new VerticalLayout(label);
        qrCodeLayout.setSizeFull();
        qrCodeLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

        final Window window = new Window(null, qrCodeLayout);
        window.setWidth(500.0f, Unit.PIXELS);
        window.setHeight(200.0f, Unit.PIXELS);
        window.addStyleName("qr-code");
        window.setModal(true);
        window.setClosable(false);
        window.setResizable(false);
        window.setDraggable(false);        
        addWindow(window);
        window.center();
        window.focus();
        window.addBlurListener(new BlurListener() {
			private static final long serialVersionUID = 6540512092787340357L;

			@Override
			public void blur(BlurEvent event) {
				window.close();			
			}
        	
        });
        window.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1010090280989720198L;

			@Override
			public void click(ClickEvent event) {
				window.close();
				
			}
        	
        });
    }
}
