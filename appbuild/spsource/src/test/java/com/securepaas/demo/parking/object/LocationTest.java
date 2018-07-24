package com.securepaas.demo.parking.object;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
	Location loc;
	String testserial="-84.342613<l-d>33.986658<l-d>111 leftover lane<l-d>";
	@Before
	public void setUp() {
		loc=new Location();
		loc.setAddress("111 leftover lane");
		loc.setLongitude(-84.3426130);
		loc.setLatitude(33.9866580);
	}

	@Test
	public void test_serialize() throws IllegalArgumentException, IllegalAccessException {
		String serial=loc.serialize();
		assertEquals(testserial, serial);
	}

	@Test
	public void test_deserialize() throws IllegalArgumentException, IllegalAccessException {
		Location test=Location.deserialize(testserial);
		assertEquals(test, loc);
	}
}
