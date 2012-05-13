package edu.mit.discoverme;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

public class ServerLink {
	private static final String URLstring = "http://people.csail.mit.edu/saqib/";
	
	public static int indx = 0;// number of events i created
	public static void loadFriends(String username, MyDataSource datasource) {
		datasource.emptyFriendsTable();
		CharSequence cs = null;
		String exp = "";
		try {
			URL url = new URL(URLstring + "users/" + username.trim()
					+ "_friends.txt");
			cs = Authenticate.getURLContent(url);
		} catch (IOException ioex) {
			exp = ioex.toString();
		}

		// datasource.createFriend("name", "fone", "email", "address");

		if (cs != null) {
			if (!cs.toString().trim().equals("")) {
			String string = cs.toString();
			String[] arg = string.split("\n");
			for (int i = 0; i < arg.length; i = i + 1) {
				String[] one = arg[i].split(";");
				datasource.createFriend(one[0], one[1], one[2], one[3]);
			}
			}

		} else {
			datasource.createFriend(exp, "fone", "email", "address");
			// create new user
			String ret = createNewUser(username);
			ret = ret;

		}
	}

	public static String createNewUser(String username) {
		CharSequence cs = null;
		String exp = "";
		String st = "";
		try {
			URL url = new URL(URLstring + "newUser.php?username="
					+ username.trim());
			cs = Authenticate.getURLContent(url);
			st = cs.toString();
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		return st;
	}
	public static void loadEvents(String eventname, MyDataSource datasource) {
		CharSequence cs = null;
		String exp = "none";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "events/" + eventname.trim() + ".txt");
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

		if (cs != null) {
			String string = cs.toString();
			String[] arg = string.split("\n");
			for (int i = 0; i < arg.length; i = i + 1) {
				String[] one = arg[i].split(";");

				datasource.createEvent(eventname, one[0], one[1], one[2],
						one[3], one[4], one[5], one[6], one[7]);

				// datasource.createEvent(arg[0], "part", "time", "location",
				// "locationLat", "locationLng", "type", "originatoer");
			}
		} else {

		}

	}

	public static String getEvent(String eventname) {
		CharSequence cs = null;
		String exp = "none";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "events/" + eventname.trim() + ".txt");
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}

		if (cs != null) {
			String string = cs.toString();
			return string;
		} else {
			return "event file not found;part,part;rsvp,rsvp;time;location;locationLat;locationLng;type;";
		}

	}

	public static void updateLocation(String username, String location,
			String locationLat, String locationLng) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "updateMyLocation.php?username="
					+ username + "&location="
					+ URLEncoder.encode(location, "UTF-8") + "&locationLat="
					+ URLEncoder.encode(locationLat, "UTF-8") + "&locationLng="
					+ URLEncoder.encode(locationLng, "UTF-8"));
					
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void sendRSVP(String username, String userFirstname,
			String eventname, String eventID, String rsvp,
			MyDataSource datasource) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "sendRSVP.php?username="
					+ URLEncoder.encode(username, "UTF-8") + "&firstname="
					+ URLEncoder.encode(userFirstname, "UTF-8") + "&eventname="
					+ URLEncoder.encode(eventname, "UTF-8") + "&eventID="
					+ URLEncoder.encode(eventID, "UTF-8") + "&rsvp="
					+ URLEncoder.encode(rsvp, "UTF-8"));

			cs = Authenticate.getURLContent(url);
			exp = cs.toString();
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend(exp, eventname, rsvp, eventID);

	}

	public static void loadNotifs(String username, MyDataSource datasource,
			DirDataSource dirdatasource) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			// URL url = new URL(URLstring + "users/" + username.trim()
			// + "_notif.txt");
			URL url = new URL(URLstring + "loadNotif.php?username="
					+ username.trim());

			cs = Authenticate.getURLContent(url);
			String st = cs.toString();
			st = st;
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}

		if (cs != null) {
			if (!cs.toString().trim().equals("")) {
			String string = cs.toString();
			String[] arg = string.split("\n");
			for (int i = 0; i < arg.length; i = i + 1) {
				String[] one = arg[i].split(":");
					if (one.length == 3) {
						Notif notif = new Notif();
						notif.setId(0);
						notif.setNotif(one[2], one[0], one[1], "no");
						processNotif(notif, datasource, dirdatasource);
						Boolean insert = true;
						if (one[0].equals("EventAccepted")
								|| one[0].equals("EventDeclined")) {
							String[] arg1 = one[1].split(",");
							insert = arg1[1].startsWith(username);

						}
						if (one[0].equals("FriendDel") && insert) {
						} else {
							datasource.createNotification(one[2], one[0],
									one[1], "no");
						}
						// datasource.createNotification(name, type, details,
						// readFlag);
					}
			}
			}
		} else {

		}
		// processAllNotifs(datasource);

	}

	public static final void loadPeople(DirDataSource dirdatasource) {
		CharSequence cs = null;
		String exp = "";

		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "directory.txt");
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}

		// datasource.createFriend("name", "fone", "email", "address");

		if (cs != null) {
			String string = cs.toString();
			String[] arg = string.split("\n");
			for (int i = 0; i < arg.length; i = i + 1) {
				String[] one = arg[i].split(";");
				dirdatasource.createPerson(one[0], one[1], one[2], one[3]);

				// datasource.createFriend(arg[0], "fone", "email", "address");
			}
			exp = arg[0];
		} else {
			dirdatasource.createPerson(exp, "fone", "email", "address");
		}

		dirdatasource.createPerson(exp, "fone", "email", "address");
	}

	public static void deleteFriend(String username, String friendemail) {// TODO
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "deleteFriend.php?username="
					+ username + "&friendID=" + friendemail);

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void sendFriendRequest(String username, String friendemail,
			String firstname) {// TODO
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "sendFriendRequest.php?username="
					+ username + "&friendemail=" + friendemail + "&firstname="
					+ firstname);

			cs = Authenticate.getURLContent(url);
			String st = cs.toString();
			String nothing = "helo";
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void addFriend(String username, String friendemail,
			String firstname) {// TODO
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "addFriend.php?username=" + username
					+ "&friendemail=" + friendemail + "&firstname=" + firstname);

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void createEvent(String username, Event event,
			MyDataSource datasource) {// TODO - delete this datasource thingy
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();
			String eventname = event.getName();
			String part = event.getParticipants();
			String rsvp = event.getResponses();
			String location = event.getLocation();
			String locationLat = event.getLocationLat();
			String locationLng = event.getLocationLng();
			String type = event.getType();
			String time = event.getTime();
			URL url = new URL(URLstring + "createEvent.php?eventid="
					+ URLEncoder.encode(eventID, "UTF-8") + "&eventname="
					+ URLEncoder.encode(eventname, "UTF-8") + "&participants="
					+ URLEncoder.encode(part, "UTF-8") + "&rsvp="
					+ URLEncoder.encode(rsvp, "UTF-8") + "&time="
					+ URLEncoder.encode(time, "UTF-8") + "&location="
					+ URLEncoder.encode(location, "UTF-8") + "&locationLat="
					+ URLEncoder.encode(locationLat, "UTF-8") + "&locationLng="
					+ URLEncoder.encode(locationLng, "UTF-8") + "&type="
					+ URLEncoder.encode(type, "UTF-8"));
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		indx = indx + 1;
		datasource.createFriend(cs.toString(), "fone", "email", "address");

	}

	public static void cancelEvent(Event event) {// TODO
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();
			URL url = new URL(URLstring + "cancelEvent.php?eventid="
					+ URLEncoder.encode(eventID, "UTF-8"));
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void processNotif(Notif notif, MyDataSource dataSource,
			DirDataSource dirdatasource) {
		CharSequence cs = null;
		String type = notif.getType();
		if(type.equals("FriendReq"))
 {/* do nothing */
		} else if (type.equals("FriendRes")) {
			List<Friend> directory = dirdatasource.getAllPeople();
			String newEmail = notif.getDetail().trim();
			Friend newFriend = new Friend();
			for (int person = 0; person < directory.size(); person++) {
				String email = directory.get(person).getEmail().trim();
				String[] arg = email.split("@");
				if (arg.length > 1)
					email = arg[0];

				if (email.equals(newEmail)) {
					newFriend = directory.get(person);
					String name = newFriend.getName();
					String emailAdd = newFriend.getEmail();
					String fone = newFriend.getFone();
					String address = newFriend.getAddress();
					dataSource.createFriend(name, fone, emailAdd, address);
					// dataSource.createFriend(name + newEmail, "fone",
					// "emailAdd", "address");
					// newFriend.getName(),
					// newFriend.getFone(), newFriend.getEmail(),
					// newFriend.getAddress());

					break;
				} else {
					// dataSource.createFriend(email, "nonenoe",
					// notif.getDetail(), "nonenow");

				}
				// dataSource.createFriend(email, "nonenoe", notif.getDetail(),
				// "nonenow");
			}
			// dataSource.createFriend("added",
			// String.valueOf(directory.size()),
			// notif.getDetail(), "nonenow");


		} else if (type.equals("FriendDel")) {
			// delete this notif from the table so it never shows up in the list
			List<Friend> allFriends = dataSource.getAllFriends();

			Friend newFriend = new Friend();
			for (int person = 0; person < allFriends.size(); person++) {
				String email = allFriends.get(person).getEmail().trim();
				String[] arg = email.split("@");
				if (arg.length > 1)
					email = arg[0];
				else
					email = email; // just there for completeness

				if (email.equals(notif.getDetail())) {
					newFriend = allFriends.get(person);
					dataSource.deleteFriend(newFriend);

					break;
				}
			}

		} else if (type.equals("EventInvite"))
		{/*do nothing*/}else if (type.equals("EventCanceled"))
		{
			String eventName = notif.getDetail().trim();
			deleteEventFromMyList(eventName, dataSource);

		} else if (type.equals("EventChanged")) {// delete old instance from
													// users table

			String eventName = notif.getDetail().trim();
			deleteEventFromMyList(eventName, dataSource);


		} else if (type.equals("EventAccepted")) {
			String notifDetail = notif.getDetail();
			String[] arg = notifDetail.split(",");
			String oldrsvp = "mt";
			String newrsvp = "mt";

			if (arg.length == 2) {
				String participantName = arg[0].trim();
				String eventName = arg[1].trim();
				List<Event> events = dataSource.getAllEvents();
				Event theEvent = new Event();
				for (int event = 0; event < events.size(); event++) {
					String eventUID = events.get(event).getEventID();
					if (eventUID.equals(eventName)) {
						theEvent = events.get(event);

						oldrsvp = theEvent.getResponses();
						String newRsvp = changeOneRSVP(
								theEvent.getParticipants(),
								theEvent.getResponses(), participantName, "yes");
						newrsvp = newRsvp;
						dataSource.updateEventRSVP(theEvent.getId(), newRsvp);

						// Event thisEvent =
						// dataSource.getEvent(theEvent.getId());
						// dataSource.createFriend(oldrsvp, "fone", newrsvp,
						// thisEvent.getResponses());

						break;

					}
				}


			}
			// dataSource.createFriend(oldrsvp, "fone", newrsvp, "nonenow");
			
		} else if (type.equals("EventDeclined")) {
			String notifDetail = notif.getDetail();
			String oldrsvp = "mt";
			String newrsvp = "mt";
			String[] arg = notifDetail.split(",");
			if (arg.length == 2) {
				String participantName = arg[0].trim();
				String eventName = arg[1].trim();
				List<Event> events = dataSource.getAllEvents();
				Event theEvent = new Event();
				for (int event = 0; event < events.size(); event++) {
					String eventUID = events.get(event).getEventID();
					if (eventUID.equals(eventName)) {
						theEvent = events.get(event);
						oldrsvp = theEvent.getResponses();
						String newRsvp = changeOneRSVP(
								theEvent.getParticipants(),
								theEvent.getResponses(), participantName, "no");
						newrsvp = newRsvp;
						dataSource.updateEventRSVP(theEvent.getId(), newRsvp);
						// Event thisEvent =
						// dataSource.getEvent(theEvent.getId());
						// dataSource.createFriend(oldrsvp, "fone", newrsvp,
						// thisEvent.getResponses());

						break;
					}
				}
				// }

			}
			// dataSource.createFriend(oldrsvp, "fone", newrsvp, "nonenow");

		} else if (type.equals("EventProposedChange")) {
		}
		
		
	}


	public static int countUnseenNotif(MyDataSource datasource) {
		datasource.open();
		List<Notif> notifs = datasource.getAllNotifs();
		datasource.close();
		int count = 0;
		for (Notif notif : notifs) {
			if (notif.getReadFlag().equals("no"))
				count = count + 1;
		}
		return count;
	}

	public static String changeOneRSVP(String participants, String rsvps,
			String oneParticipant, String oneRsvp) {
		String rsvp = "";
		String[] arg1 = participants.split(",");
		String[] arg2 = rsvps.split(",");
		if (arg1.length == arg2.length) {
			for (int part = 0; part < arg1.length; part = part + 1) {
				if (arg1[part].trim().equals(oneParticipant)) {
					arg2[part] = oneRsvp;
				}

			}
			// participants = "";
			
			for (int part = 0; part < arg1.length; part = part + 1) {
				// participants = participants + "," + arg1[part];
				rsvp = rsvp + arg2[part] + ",";
			}

		} else
			rsvp = rsvps;
		return rsvp;
	}

	public static void deleteEventFromMyList(String eventUniqueID,
			MyDataSource dataSource) {
		List<Event> events = dataSource.getAllEvents();
		Event theEvent = new Event();
		for (int event = 0; event < events.size(); event++) {
			String eventUID = events.get(event).getEventID();
			if (eventUID.equals(eventUniqueID)) {
				theEvent = events.get(event);
				dataSource.deleteEvent(theEvent);
				break;
			}
		}

	}

	public static void proposeChanges(String username, String firstname,
			Event event) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();
			String eventname = event.getName();
			String part = event.getParticipants();
			String rsvp = event.getResponses();
			String location = event.getLocation();
			String locationLat = event.getLocationLat();
			String locationLng = event.getLocationLng();
			String type = event.getType();
			String time = event.getTime();

			URL url = new URL(URLstring + "proposeChange.php?username="
					+ URLEncoder.encode(username, "UTF-8") + "&firstname="
					+ URLEncoder.encode(firstname, "UTF-8") + "&eventid="
					+ URLEncoder.encode(eventID, "UTF-8") + "&eventname="
					+ URLEncoder.encode(eventname, "UTF-8") + "&participants="
					+ URLEncoder.encode(part, "UTF-8") + "&rsvp="
					+ URLEncoder.encode(rsvp, "UTF-8") + "&time="
					+ URLEncoder.encode(time, "UTF-8") + "&location="
					+ URLEncoder.encode(location, "UTF-8") + "&locationLat="
					+ URLEncoder.encode(locationLat, "UTF-8") + "&locationLng="
					+ URLEncoder.encode(locationLng, "UTF-8") + "&type="
					+ URLEncoder.encode(type, "UTF-8"));

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}

	}

	// public static String getProposedChange(String eventname, String nine) {
	// String event = getEvent(eventname.trim() + "_update");
	// return event;
	// }

	public static String getFriendLocation(String friendemail) {
		String retVal = "none;0;0";
			CharSequence cs = null;
			String exp = "";
			try {
				// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(URLstring + "fetchFriendLocation.php?username="
								+ friendemail);


				cs = Authenticate.getURLContent(url);
				// do something with the URL...
				if(cs!=null)
				{
					String []arg =cs.toString().trim().split(";");
					if(arg.length==3)
					retVal = cs.toString().trim();
						
				}
			} catch (IOException ioex) {
				exp = ioex.toString();
			}
		return retVal;
	}
	
	public static Vector<GeoLocation> getLocationForAllFriend(MyDataSource dataSource) {
		Vector<GeoLocation> friendsLocations = new Vector<GeoLocation>();
		dataSource.open();
		
		List<Friend> allFriends = dataSource.getAllFriends();
		for (Friend friend : allFriends) {
			String locationString = ServerLink.getFriendLocation(friend.getMITId());
			String[] locationArray = locationString.split(";");
			GeoLocation geoLocation = new GeoLocation(friend.getMITId(), locationArray[0], locationArray[1], locationArray[2]);
			
			friendsLocations.add(geoLocation);
		}
		
		dataSource.close();
		return friendsLocations;
	}
	
	public static void acceptProposedChange(String username, Event updatedEvent,
			MyDataSource dataSource) {
		
		Event event = updatedEvent;
		String[] arg = event.getEventID().split("_update");
		if (arg.length >= 1) {
			{
				event.setEventID(arg[0]);
				cancelEvent(event);
				deleteEventFromMyList(event.getEventID(), dataSource);
				cancelEvent(updatedEvent);

				createEvent(username, event, dataSource);
				dataSource.createEvent(updatedEvent.getEventID(),
						updatedEvent.getName(),
						updatedEvent.getParticipants(),
						updatedEvent.getResponses(), updatedEvent.getTime(),
						updatedEvent.getLocation(),
						updatedEvent.getLocationLat(),
						updatedEvent.getLocationLng(), updatedEvent.getType());
			}
		}
		

	}
}
