package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DirSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_DIRECTORY = "directory";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_FONE = "fone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_ADDRESS = "address";


	private static final String DATABASE_NAME = "directory.db";
	private static final int DATABASE_VERSION = 1;


	// Database creation sql statement
	private static final String DIR_TABLE_CREATE = "create table "
			+ TABLE_DIRECTORY + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null," + COLUMN_FONE + " text not null,"
			+ COLUMN_EMAIL + " text not null," + COLUMN_ADDRESS
			+ " text not null);";


	public DirSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DIR_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DirSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIRECTORY);
		onCreate(db);
	}

}
