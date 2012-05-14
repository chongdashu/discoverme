package edu.mit.discoverme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DirDataSource {

	// Database fields
	private SQLiteDatabase database;
	private final DirSQLiteHelper dbHelper;
	private final String[] allDirColumns = { DirSQLiteHelper.COLUMN_ID,
			DirSQLiteHelper.COLUMN_NAME, DirSQLiteHelper.COLUMN_FONE,
			DirSQLiteHelper.COLUMN_EMAIL, DirSQLiteHelper.COLUMN_ADDRESS };

	public DirDataSource(Context context) {
		dbHelper = new DirSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Friend createPerson(String name, String fone, String email,
			String address) {
		ContentValues values = new ContentValues();
		values.put(DirSQLiteHelper.COLUMN_NAME, name);
		values.put(DirSQLiteHelper.COLUMN_FONE, fone);
		values.put(DirSQLiteHelper.COLUMN_EMAIL, email);
		values.put(DirSQLiteHelper.COLUMN_ADDRESS, address);

		long insertId = database.insert(DirSQLiteHelper.TABLE_DIRECTORY,
				null,
				values);
		Cursor cursor = database.query(DirSQLiteHelper.TABLE_DIRECTORY,
				allDirColumns, DirSQLiteHelper.COLUMN_ID + " = "
						+ insertId,
				null,
				null, null, null);
		cursor.moveToFirst();
		Friend newFriend = cursorToFriend(cursor);
		cursor.close();
		return newFriend;
	}

	public Friend getPerson(long id) {
		Cursor cursor = database.query(DirSQLiteHelper.TABLE_DIRECTORY,
				allDirColumns, DirSQLiteHelper.COLUMN_ID + " = " + id,
				null,
				null, null, null);
		cursor.moveToFirst();
		Friend theFriend = cursorToFriend(cursor);
		cursor.close();
		return theFriend;
	}

	public void deletePerson(Friend friend) {
		long id = friend.getId();
		System.out.println("Friend deleted with id: " + id);
		database.delete(DirSQLiteHelper.TABLE_DIRECTORY,
				DirSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Friend> getAllPeople() {
		List<Friend> friends = new ArrayList<Friend>();

		Cursor cursor = database.query(DirSQLiteHelper.TABLE_DIRECTORY,
				allDirColumns, null, null, null, null, null);

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

	public void emptyNotifTable() {
		System.out.println("directory deleted with id: ");
		database.delete(DirSQLiteHelper.TABLE_DIRECTORY, null, null);
	}

}
