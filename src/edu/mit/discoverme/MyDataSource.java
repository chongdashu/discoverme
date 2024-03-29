package edu.mit.discoverme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDataSource {

	// Database fields
	private SQLiteDatabase database;
	private final MySQLiteHelper dbHelper;
	private final String[] allFriendColumns = { MySQLiteHelper.COLUMN_FID,
			MySQLiteHelper.COLUMN_FNAME, MySQLiteHelper.COLUMN_FONE,
			MySQLiteHelper.COLUMN_EMAIL, MySQLiteHelper.COLUMN_ADDRESS };

	private final String[] allEventColumns = { MySQLiteHelper.COLUMN_EID,
			MySQLiteHelper.COLUMN_EUID, MySQLiteHelper.COLUMN_ENAME,
			MySQLiteHelper.COLUMN_PART, MySQLiteHelper.COLUMN_RSVP,
			MySQLiteHelper.COLUMN_TIME, MySQLiteHelper.COLUMN_LOCATION,
			MySQLiteHelper.COLUMN_LOCATION_LAT,
			MySQLiteHelper.COLUMN_LOCATION_LNG, MySQLiteHelper.COLUMN_TYPE };

	private final String[] allNotifColumns = { MySQLiteHelper.COLUMN_NID,
			MySQLiteHelper.COLUMN_NNAME, MySQLiteHelper.COLUMN_NTYPE,
			MySQLiteHelper.COLUMN_NDETAIL, MySQLiteHelper.COLUMN_READ_FLAG };


	public MyDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Friend createFriend(String name, String fone, String email,
			String address) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_FNAME, name);
		values.put(MySQLiteHelper.COLUMN_FONE, fone);
		values.put(MySQLiteHelper.COLUMN_EMAIL, email);
		values.put(MySQLiteHelper.COLUMN_ADDRESS, address);

		long insertId = database.insert(MySQLiteHelper.TABLE_FRIENDS,
				null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_FRIENDS,
				allFriendColumns, MySQLiteHelper.COLUMN_FID + " = "
						+ insertId,
				null,
				null, null, null);
		cursor.moveToFirst();
		Friend newFriend = cursorToFriend(cursor);
		cursor.close();
		return newFriend;
	}

	public Friend getFriend(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_FRIENDS,
				allFriendColumns, MySQLiteHelper.COLUMN_FID + " = " + id, null,
				null, null, null);
		cursor.moveToFirst();
		Friend theFriend = cursorToFriend(cursor);
		cursor.close();
		return theFriend;
	}

	public void deleteFriend(Friend friend) {
		long id = friend.getId();
		System.out.println("Friend deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_FRIENDS, MySQLiteHelper.COLUMN_FID
				+ " = " + id, null);
	}

	public void emptyFriendsTable() {
		System.out.println("All Friends deleted with id: ");
		database.delete(MySQLiteHelper.TABLE_FRIENDS, null, null);
	}
	public List<Friend> getAllFriends() {
		List<Friend> friends = new ArrayList<Friend>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_FRIENDS,
				allFriendColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Friend friend = cursorToFriend(cursor);
			friends.add(friend);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return friends;
	}

	private Friend cursorToFriend(Cursor cursor) {
		Friend friend = new Friend();
		friend.setId(cursor.getLong(0));
		friend.setFriend(cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));
		return friend;
	}

	public Event createEvent(String eventID, String name, String participants,
			String responses, String time,
			String location, String locationLat, String locationLng,
 String type) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_EUID, eventID);
		values.put(MySQLiteHelper.COLUMN_ENAME, name);
		values.put(MySQLiteHelper.COLUMN_PART, participants);
		values.put(MySQLiteHelper.COLUMN_RSVP, responses);
		values.put(MySQLiteHelper.COLUMN_TIME, time);
		values.put(MySQLiteHelper.COLUMN_LOCATION, location);
		values.put(MySQLiteHelper.COLUMN_LOCATION_LAT, locationLat);
		values.put(MySQLiteHelper.COLUMN_LOCATION_LNG, locationLng);
		values.put(MySQLiteHelper.COLUMN_TYPE, type);
		long insertId = database.insert(MySQLiteHelper.TABLE_EVENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS,
				allEventColumns, MySQLiteHelper.COLUMN_EID + " = "
						+ insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Event newEvent = cursorToEvent(cursor);
		cursor.close();
		return newEvent;
	}

	public Event getEvent(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS,
				allEventColumns, MySQLiteHelper.COLUMN_EID + " = " + id,
				null, null, null, null);
		cursor.moveToFirst();
		Event theEvent = cursorToEvent(cursor);
		cursor.close();
		return theEvent;
	}
	public void deleteEvent(Event event) {
		long id = event.getId();
		System.out.println("Event deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_EVENTS, MySQLiteHelper.COLUMN_EID
				+ " = " + id, null);
	}

	public void updateEventRSVP(long id, String rsvp) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_RSVP, rsvp);
		database.update(MySQLiteHelper.TABLE_EVENTS, values,
				MySQLiteHelper.COLUMN_EID + " = " + id, null);
	}


	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<Event>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS,
				allEventColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Event event = cursorToEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return events;
	}

	private Event cursorToEvent(Cursor cursor) {
		Event event = new Event();
		event.setId(cursor.getLong(0));
		event.setEvent(cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5),
				cursor.getString(6), cursor.getString(7), cursor.getString(8),
				cursor.getString(9));
		return event;
	}

	public void emptyEventTable() {
		System.out.println("All events deleted with id: ");
		database.delete(MySQLiteHelper.TABLE_EVENTS, null, null);
	}

	public Notif createNotification(String name, String type, String details,
			String readFlag) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NNAME, name);
		values.put(MySQLiteHelper.COLUMN_NTYPE, type);
		values.put(MySQLiteHelper.COLUMN_NDETAIL, details);
		values.put(MySQLiteHelper.COLUMN_READ_FLAG, readFlag);

		long insertId = database.insert(MySQLiteHelper.TABLE_NOTIFS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTIFS,
				allNotifColumns, MySQLiteHelper.COLUMN_NID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Notif newNotif = cursorToNotif(cursor);
		cursor.close();
		return newNotif;
	}

	public Notif getNotif(long id) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTIFS,
				allNotifColumns, MySQLiteHelper.COLUMN_NID + " = " + id, null,
				null, null, null);
		cursor.moveToFirst();
		Notif newNotif = cursorToNotif(cursor);
		cursor.close();
		return newNotif;
	}

	public void updateProcessedNotif(Notif notif) {

		long id = notif.getId();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NNAME, notif.getName());
		values.put(MySQLiteHelper.COLUMN_NTYPE, notif.getType());
		values.put(MySQLiteHelper.COLUMN_NDETAIL, notif.getDetail());
		values.put(MySQLiteHelper.COLUMN_READ_FLAG, notif.getReadFlag());
		database.update(MySQLiteHelper.TABLE_NOTIFS, values,
				MySQLiteHelper.COLUMN_NID + " = " + id, null);
	}

	public void updateSeenNotif(Notif notif) {

		long id = notif.getId();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NNAME, notif.getName());
		values.put(MySQLiteHelper.COLUMN_NTYPE, notif.getType());
		values.put(MySQLiteHelper.COLUMN_NDETAIL, notif.getDetail());
		values.put(MySQLiteHelper.COLUMN_READ_FLAG, "yes");
		database.update(MySQLiteHelper.TABLE_NOTIFS, values,
				MySQLiteHelper.COLUMN_NID + " = " + id, null);
	}

	public void readAllNotif() {

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_READ_FLAG, "yes");
		database.update(MySQLiteHelper.TABLE_NOTIFS, values, null, null);
	}

	public void deleteNotif(Notif notif) {
		long id = notif.getId();
		System.out.println("Notif deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_NOTIFS, MySQLiteHelper.COLUMN_NID
				+ " = " + id, null);
	}

	public List<Notif> getAllNotifs() {
		List<Notif> notifs = new ArrayList<Notif>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTIFS,
				allNotifColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Notif notif = cursorToNotif(cursor);
			notifs.add(notif);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return notifs;
	}

	private Notif cursorToNotif(Cursor cursor) {
		Notif notif = new Notif();
		notif.setId(cursor.getLong(0));
		notif.setNotif(cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));
		return notif;
	}

	public void emptyNotifTable() {
		System.out.println("All notifs deleted with id: ");
		database.delete(MySQLiteHelper.TABLE_NOTIFS, null, null);
	}

}
