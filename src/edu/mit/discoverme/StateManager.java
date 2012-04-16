package edu.mit.discoverme;

import java.util.Vector;

import android.app.Application;

import com.google.android.maps.GeoPoint;

public class StateManager extends Application {

	// user info
	public String userName = "John Doe";
	public GeoPoint userGeoPoint;
	public float userLat;
	public float userLon;
	public String userAddress;
	public Vector<GeoPoint> friendPoints;
	public Vector<String> friendAddresses;
	
	// people
	private String[] friends;
	private String[] pendingReq;// request came n now is waiting for your
								// response
	private String[] pendingRes;// you added as friend and now waiting fr
								// response
	private String[] directory_names;
	private String[] directory_friendType;

	// events
	private String[] events;
	private String[] participants;
	private String[] locations;
	private String[] locationsLAT;
	private String[] locationsLNG;
	private String[] eventType;
	private String[] time;
	private String[] eventOriginator;

	// notification
	private String[] notifs;
	private int[] notifType;
	private String[] notifsNames;

	
	// event in notification is replied to already
	private int eventDone;
	

	public void start() {
		setFriendsFromHD();
		setDirectoryFromHD();

		setEventsFromHD();
		setParticipantsFromHD();
		setLocationsFromHD();
		setLocationsLATfromHD();
		setLocationsLNGfromHD();
		setEventTypeFromHD();
		setTimeFromHD();
		setEventOriginatorFromHD();

		setNotifsFromHD();
		setNotifsNamesFromHD();
		setNotifTypeFromHD();
		eventDone = 0;
	}
	/* people thing implemented below */

	public void setDirectoryFromHD() {
		this.directory_names = getResources()
				.getStringArray(R.array.directory_array);
		this.directory_friendType = getResources().getStringArray(
				R.array.friend_type_array);
	}

	public String[] getDirectoryNames() {
		return directory_names;
	}

	public String[] getDirectory_friendType() {
		return directory_friendType;
	}

	public void setDirectory_friendType(String[] directory_friendType) {
		this.directory_friendType = directory_friendType;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriendsFromHD() {
		this.friends = getResources().getStringArray(R.array.friends_array);
	}

	public void setFriends(String[] newFriends) {
		this.friends = newFriends;

	}
	/* people thing implemented above */
	/* events thing implemented below */
	public String[] getEvents() {
		return events;
	}

	public void setEvents(String[] events) {
		this.events = events;
	}

	public String[] getParticipants() {
		return participants;
	}

	public void setParticipants(String[] participants) {
		this.participants = participants;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public String[] getLocationsLAT() {
		return locationsLAT;
	}

	public void setLocationsLAT(String[] locationsLAT) {
		this.locationsLAT = locationsLAT;
	}

	public String[] getLocationsLNG() {
		return locationsLNG;
	}

	public void setLocationsLNG(String[] locationsLNG) {
		this.locationsLNG = locationsLNG;
	}
	public String[] getEventType() {
		return eventType;
	}

	public void setEventType(String[] eventType) {
		this.eventType = eventType;
	}

	public String[] getTime() {
		return time;
	}

	public void setTime(String[] time) {
		this.time = time;
	}

	public String[] getEventOriginator() {
		return eventOriginator;
	}

	public void setEventOriginator(String[] eventOriginator) {
		this.eventOriginator = eventOriginator;
	}

	public void setEventsFromHD() {
		this.events = getResources().getStringArray(R.array.events_array);
	}

	public void setParticipantsFromHD() {
		this.participants = getResources().getStringArray(
				R.array.participants_array);
	}

	public void setLocationsFromHD() {
		this.locations = getResources().getStringArray(R.array.location_array);
	}

	public void setLocationsLATfromHD() {
		this.locationsLAT = getResources().getStringArray(
				R.array.location_lat_array);
	}

	public void setLocationsLNGfromHD() {
		this.locationsLNG = getResources().getStringArray(
				R.array.location_lng_array);
	}

	public void setEventTypeFromHD() {
		this.eventType = getResources()
				.getStringArray(R.array.event_type_array);
	}

	public void setTimeFromHD() {
		this.time = getResources().getStringArray(R.array.time_array);
	}

	public void setEventOriginatorFromHD() {
		this.eventOriginator = getResources().getStringArray(
				R.array.event_originator);
	}
	/* events thing implemented above */
	/* notif thing implemented below */
	public int[] getNotifType() {
		return notifType;
	}

	public void setNotifType(int[] notifType) {
		this.notifType = notifType;
	}

	public String[] getNotifs() {
		return notifs;
	}

	public void setNotifs(String[] notifs) {
		this.notifs = notifs;
	}

	public String[] getNotifsNames() {
		return notifsNames;
	}

	public void setNotifsNames(String[] notifsNames) {
		this.notifsNames = notifsNames;
	}

	public void setNotifTypeFromHD() {
		this.notifType = getResources().getIntArray(R.array.notif_type_array);
	}

	public void setNotifsFromHD() {
		this.notifs = getResources().getStringArray(R.array.notifs_array);
	}

	public void setNotifsNamesFromHD() {
		this.notifsNames = getResources().getStringArray(
				R.array.notifs_names_array);
	}
	/* notif thing implemented above */
}
