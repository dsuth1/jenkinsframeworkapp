package com.securepaas.demo.parking.object;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TicketTest {

	Ticket ticket;
	
	@Before
	public void setUp() {
		ticket=createRandomTicket(null,'a');
	}
	
	@Test
	public void test_deserialize() throws IllegalArgumentException, IllegalAccessException {
		String test=ticket.serialize();
		Ticket testticket=Ticket.deserialize(test);
		assertEquals(testticket, ticket);
	}
	
    private static Ticket createRandomTicket(final Location location, char zone) {
        final Random random = new Random();
        final Ticket ticket = new Ticket();

        Calendar cal = Calendar.getInstance();

        if (location == null) {
            cal.add(Calendar.HOUR, -(30 + random.nextInt(70)));
            ticket.setNotes("Dummy notes");
            ticket.setLocation(createDummyLocation());
        } else {
            cal.add(Calendar.HOUR, -random.nextInt(24));
            ticket.setLocation(location);
            ticket.setNotes("Notes for " + location.getAddress());
        }
        cal.set(Calendar.MINUTE, 0);
        ticket.setTimeStamp(cal.getTime());

        ticket.setImageUrl("VAADIN/themes/parking/tickets/" + 1 + ".jpg");
        ticket.setThumbnailUrl("VAADIN/themes/parking/tickets/" + 1 + "thumbnail.jpg");
        ticket.setImageIncluded(true);
        ticket.setRegisterPlateNumber("ABC-" + (random.nextInt(800) + 100));

        ticket.setViolation(Violation.values()[random.nextInt(Violation.values().length)]);

        ticket.setMyTicket(random.nextDouble() < 0.1);
        ticket.setArea(zone + String.valueOf(random.nextInt(4) + 1));
        return ticket;
    }
    
    private static Location createDummyLocation() {
        final Random random = new Random();

        double lat = Location.getDefaultLatitude();
        double lon = Location.getDefaultLongitude();

        Location location = new Location();
        location.setAddress("Test");

        double latitude = lat + (random.nextDouble() - 0.5) * 0.1;
        double longitude = lon + (random.nextDouble() - 0.5) * 0.1;
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }
	
}
