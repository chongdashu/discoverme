package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

public class Event {
	private long id;
	private String eventID;// globaly unique name
	private String name;
	private String participants;
	private String responses;
	private String time;
	private String location;
	private String locationLat;
	private String locationLng;
	private String type;


	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

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



	public void setEvent(String eventID, String name, String participants,
			String responses,
			String time,
			String location, String locationLat, String locationLng,
 String type) {
		this.eventID = eventID;
		this.name = name;
		this.participants = participants;
		this.responses = responses;
		this.time = time;
		this.location = location;
		this.locationLat = locationLat;
		this.locationLng = locationLng;
		this.type = type;
	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
}
