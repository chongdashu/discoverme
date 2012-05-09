package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_FRIENDS = "friends";
	public static final String COLUMN_FID = "_fid";
	public static final String COLUMN_FNAME = "friendname";
	public static final String COLUMN_FONE = "fone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_ADDRESS = "address";

	public static final String TABLE_EVENTS = "events";
	public static final String COLUMN_EID = "_eid";
	public static final String COLUMN_ENAME = "eventname";
	public static final String COLUMN_PART = "participants";
	public static final String COLUMN_RSVP = "rsvp";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_LOCATION_LAT = "locationlat";
	public static final String COLUMN_LOCATION_LNG = "locationlng";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_ORIGINATOR = "originator";

	public static final String TABLE_NOTIFS = "notifications";
	public static final String COLUMN_NID = "_nid";
	public static final String COLUMN_NNAME = "notifname";
	public static final String COLUMN_NTYPE = "ntype";
	public static final String COLUMN_NDETAIL = "details";
	public static final String COLUMN_READ_FLAG = "readflag";

	private static final String DATABASE_NAME = "dm.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String EVENT_TABLE_CREATE = "create table "
			+ TABLE_EVENTS + "( " + COLUMN_EID
			+ " integer primary key autoincrement, " + COLUMN_ENAME
			+ " text not null," + COLUMN_PART + " text not null," + COLUMN_RSVP
			+ " text not null," + COLUMN_TIME
			+ " text not null," + COLUMN_LOCATION + " text not null,"
			+ COLUMN_LOCATION_LAT + " text not null," + COLUMN_LOCATION_LNG
			+ " text not null," + COLUMN_TYPE + " text not null,"
			+ COLUMN_ORIGINATOR + " text not null );";

	// Database creation sql statement
	private static final String FRIEND_TABLE_CREATE = "create table "
			+ TABLE_FRIENDS + "( " + COLUMN_FID
			+ " integer primary key autoincrement, " + COLUMN_FNAME
			+ " text not null," + COLUMN_FONE + " text not null,"
			+ COLUMN_EMAIL + " text not null," + COLUMN_ADDRESS
			+ " text not null);";

	// Database creation sql statement
	private static final String NOTIF_TABLE_CREATE = "create table "
			+ TABLE_NOTIFS + "( " + COLUMN_NID
			+ " integer primary key autoincrement, " + COLUMN_NNAME
			+ " text not null," + COLUMN_NTYPE + " text not null,"
			+ COLUMN_NDETAIL + " text not null,"
			+ COLUMN_READ_FLAG + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(EVENT_TABLE_CREATE);
		database.execSQL(FRIEND_TABLE_CREATE);
		database.execSQL(NOTIF_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFS);
		onCreate(db);
	}

}
