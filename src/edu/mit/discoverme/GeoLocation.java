package edu.mit.discoverme;

import com.google.android.maps.GeoPoint;

public class GeoLocation {
	
	public String username;
	public String addressName;
	public float lon;
	public float lat;

	public GeoLocation(String username, String name, String lat, String lon) {
		this.username = username;
		this.addressName = name;
		this.lat = Float.parseFloat(lat);
		this.lon = Float.parseFloat(lon);
	}
	
	public GeoPoint getGeoPoint() {
		int lonE6 = (int) (lon*1000000);
		int latE6 = (int) (lat*1000000);
		
		return new GeoPoint(latE6, lonE6);
	}
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
