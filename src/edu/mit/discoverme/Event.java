package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

public class Event {
	private long id;
	private String name;
	private String participants;
	private String responses;
	private String time;
	private String location;
	private String locationLat;
	private String locationLng;
	private String type;
	private String originator;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}
	
	public String getResponses() {
		return responses;
	}

	public void setResponses(String responses) {
		this.responses = responses;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(String locationLat) {
		this.locationLat = locationLat;
	}

	public String getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(String locationLng) {
		this.locationLng = locationLng;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}


	public void setEvent(String name, String participants, String time,
			String location, String locationLat, String locationLng,
			String type, String originator) {
		this.name = name;
		this.participants = participants;
		this.time = time;
		this.location = location;
		this.locationLat = locationLat;
		this.locationLng = locationLng;
		this.type = type;
		this.originator = originator;

	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
}
