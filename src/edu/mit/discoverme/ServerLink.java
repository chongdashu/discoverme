package edu.mit.discoverme;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ServerLink {
	
	public static int indx = 0;// number of events i created
	public static void loadFriends(String username, MyDataSource datasource) {
		datasource.emptyFriendsTable();
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/users/"
							+ username.trim() + "_friends.txt");
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
				datasource.createFriend(one[0], one[1], one[2], one[3]);
				// datasource.createFriend(arg[0], "fone", "email", "address");
			}
		} else {
			datasource.createFriend(exp, "fone", "email", "address");
		}
	}

	public static void loadEvents(String eventname, MyDataSource datasource) {
		CharSequence cs = null;
		String exp = "none";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/events/"
							+ eventname.trim() + ".txt");
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
			datasource.createEvent(exp, "part", "rsvp", "time", "location",
					"locationLat", "locationLng", "type", "originatoer");
		}

	}

	public static String getEvent(String eventname) {
		CharSequence cs = null;
		String exp = "none";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/events/"
							+ eventname.trim() + ".txt");
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
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?username="
							+ username + "&location=" + location
							+ "&locationLat=" + locationLat + "&locationLng="
							+ locationLat);
					
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void loadNotifs(String username, MyDataSource datasource) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/users/"
							+ username.trim() + "_notif.txt");

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
				String[] one = arg[i].split(":");
				datasource.createNotification(one[2], one[0], one[1], "no",
						"no");
				// datasource.createNotification(name, type, details, readFlag);
			}
		} else {
			datasource.createEvent(exp, "part", "rsvp", "time", "location",
					"locationLat", "locationLng", "type", "originatoer");
		}
		processAllNotifs(datasource);

	}

	public static final void loadPeople(DirDataSource dirdatasource) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/directory.txt");
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
		} else {
			dirdatasource.createPerson(exp, "fone", "email", "address");
		}
	}

	public static void deleteFriend(String username, String friendemail) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?username="
							+ username + "&friendemail=" + friendemail);

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void addFriend(String username, String friendemail) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?username="
							+ username + "&friendemail=" + friendemail);

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void createEvent(String username, Event event) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();
			String part = event.getParticipants();
			String rsvp = event.getResponses();
			String location = event.getLocation();
			String locationLat = event.getLocationLat();
			String locationlng = event.getLocationLng();
			String type = event.getType();
			String time = event.getTime();

			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?eventid="
							+ eventID + "&participants=" + part + "&rsvp="
							+ rsvp + "&time=" + time + "&location=" + location
							+ "&locationLat=" + locationLat + "&locationLng="
							+ locationLat + "&type=" + type);

			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		indx = indx + 1;
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void cancelEvent(Event event) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?eventid="
							+ eventID);
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void rsvpEvent(String username, String response, Event event) {
		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			String eventID = event.getEventID();

			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/script.php?eventid="
							+ eventID + "&usename=" + username + "&rsvp="
							+ response);
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		// datasource.createFriend("name", "fone", "email", "address");

	}

	public static void processAllNotifs(MyDataSource datasource) {
		datasource.open();
		List<Notif> notifs = datasource.getAllNotifs();
		datasource.close();
		for (Notif notif : notifs) {
			processNotif(notif, datasource);
		}
	}

	public static void processNotif(Notif notif, MyDataSource dataSource) {
		CharSequence cs = null;
		String type = notif.getType();
		if(type.equals("FriendReq"))
 {/* do nothing */
		} else if (type.equals("FriendRes"))
 {

		} else if (type.equals("FriendDel")) {
			// delete this notif from the table so it never shows up in the list
			// dataSource.deleteNotif(notif);
		} else if (type.equals("EventInvite"))
		{/*do nothing*/}else if (type.equals("EventCanceled"))
		{
			
		}else if (type.equals("EventChanged"))
 {/* do nothing */
		} else if (type.equals("EventAccepted"))
		{}else if (type.equals("EventDeclined"))
		{}else if (type.equals("EventProposedChange")){}
		
			
		
	}
}
