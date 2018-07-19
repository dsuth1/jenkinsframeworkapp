package com.securepaas.demo.parking.ui;

import java.util.Locale;
import java.util.TimeZone;

import org.vaadin.teemu.switchui.Switch;

import com.securepaas.demo.parking.object.Ticket;
import com.securepaas.demo.parking.object.Violation;
import com.securepaas.demo.parking.util.DataUtil;
import com.securepaas.demo.parking.util.NotificationWindow;
import com.securepaas.demo.parking.object.Location;
import com.vaadin.addon.touchkit.extensions.Geolocator;
import com.vaadin.addon.touchkit.extensions.PositionCallback;
import com.vaadin.addon.touchkit.gwt.client.vcom.Position;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

public class TicketView extends NavigationView{
	private static final long serialVersionUID = -9046593843583900135L;
	private final TextField licensePlate;
	private final PopupDateField date;
	private final NativeSelect area;
	private final NativeSelect violation;
	private final Switch resolveLocation;
	private final TextField location;
	public TicketView() {
        setSizeFull();
        setCaption("Submit Ticket");
        setRightComponent(new Button("Save", new ClickListener() {
			private static final long serialVersionUID = -4980441197109061242L;

			@Override
            public void buttonClick(ClickEvent event) {
				//lat="unknown";
				//lon="unknown";
				if(resolveLocation.getValue()==true)
				{
					Geolocator.detect(new PositionCallback() {

						@Override
						public void onSuccess(Position position) {	
							Location local=new Location();
							local.setLatitude(position.getLatitude());
							local.setLongitude(position.getLongitude());
							
							Ticket ticket=new Ticket();
							ticket.setArea(area.getValue().toString());
							ticket.setImageIncluded(false);
							ticket.setNotes("");
							ticket.setRegisterPlateNumber(licensePlate.getValue());
							ticket.setLocation(local);
							ticket.setViolation((Violation)violation.getValue());
							
							if(DataUtil.persistTicket(ticket))
							{
								location.clear();
								resolveLocation.setValue(true);
								violation.clear();
								area.clear();
								date.clear();
								licensePlate.clear();
								NotificationWindow.showSuccessBar("Successfully submitted ticket");
							}
							else
								NotificationWindow.showSuccessBar("Failed to save ticket.  Backend webservice communication blocked.");
						}

						@Override
						public void onFailure(int errorCode) {
							
							NotificationWindow.showErrorBar("Failed to save ticket, unable to resolve location");
						}
						
					});
				} else
				{
					NotificationWindow.showSuccessBar("Unsupported save");
				}
            }
        }));
        
        //VerticalLayout layout = new VerticalLayout();
        FormLayout ticketInfo = new FormLayout();
        ticketInfo.addStyleName("v-touchkit-navbar");
        ticketInfo.setSizeFull();
        ticketInfo.setSpacing(true);
        ticketInfo.setMargin(true);
        ticketInfo.addStyleName("light");
         	
    	resolveLocation=new Switch("Auto Location");
    	resolveLocation.setValue(true);
    	resolveLocation.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2872493700145376127L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue().equals(false))
				{
					resolveLocation.setValue(true);
					NotificationWindow.showSuccessBar("Not currently supported");
				}
				/*else
					location.setEnabled(false);*/
				
			}
    		
    	});
    	ticketInfo.addComponent(resolveLocation);
    	
    	//geo=new GeoLocation();
        //geo.addListener(this);
        
        location=new TextField("Location");
        location.setWidth(100f,Unit.PERCENTAGE);
        location.setEnabled(false);
        ticketInfo.addComponent(location);
             
        date= new PopupDateField("Date & Time");
        date.setWidth(100f,Unit.PERCENTAGE);
        date.setTextFieldEnabled(true);
        date.setTimeZone(TimeZone.getDefault());
        date.setLocale(Locale.getDefault());
        date.setResolution(Resolution.SECOND);
        ticketInfo.addComponent(date);
        
        licensePlate=new TextField("License Plate");
        licensePlate.setWidth(100f,Unit.PERCENTAGE);
        ticketInfo.addComponent(licensePlate);
        
    	area = new NativeSelect("Area");
    	area.setWidth(100f,Unit.PERCENTAGE);
    	area.addItem("A1");
    	area.addItem("A2");
    	area.addItem("A3");
    	area.addItem("A4");
    	area.addItem("B1");
    	area.addItem("B2");
    	area.addItem("B3");
    	area.addItem("B4");
    	area.addItem("C1");
    	area.addItem("C3");
    	area.addItem("C2");
    	area.addItem("C4");
    	ticketInfo.addComponent(area);
    	
    	violation = new NativeSelect("Violation");
    	violation.setWidth(100f,Unit.PERCENTAGE);
    	for(Violation value:Violation.values())
    		violation.addItem(value);
    	ticketInfo.addComponent(violation);
        
        setContent(ticketInfo);
	}
}
