package com.securepaas.demo.parking.object;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = -758781696532264698L;
	/*
     * Default the location to Shadow-Soft HQ
     */
    private static double defaultLatitude = 33.9866580;
    private static double defaultLongitude = -84.3426130;
	private double longitude;
    private double latitude;
    private String address;
    
    public static double getDefaultLatitude()
    {
    	return defaultLatitude;
    }
    
    
    public static double getDefaultLongitude()
    {
    	return defaultLongitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private static final String DELIMITER = "<l-d>";

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(longitude + DELIMITER);
        sb.append(latitude + DELIMITER);
        sb.append(address + DELIMITER);
        return sb.toString();
    }

    public static Location deserialize(final String str) {
        Location result = null;
        if (str != null) {
            result = new Location();
            String[] split = str.split(DELIMITER);
            result.setLongitude(Double.parseDouble(split[0]));
            result.setLatitude(Double.parseDouble(split[1]));
            if (split.length > 2) {
                result.setAddress(split[2]);
            }
        }
        return result;
    }
    
    @Override
    public boolean equals(Object o)
    {
    	if(o==this)
    		return true;
    	else if(o instanceof Location)
    	{
    		Location test=(Location)o;
    		if (!test.address.equals(this.address))
    			return false;
    		else if (test.latitude!=this.latitude)
    			return false;
    		else if (test.longitude!=this.longitude)
    			return false;
    		else 
    			return true;
    	}
    	else
    		return false;
    }
}
