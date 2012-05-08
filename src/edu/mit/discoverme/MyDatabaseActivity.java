package edu.mit.discoverme;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class MyDatabaseActivity extends ListActivity {
	private MyDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
			 
		String username = prefs.getString("username", "none");
		String password = prefs.getString("password", "none");
		datasource = new MyDataSource(this);
		datasource.open();

		CharSequence cs = null;
		String exp = "";

		// loading friends
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);

			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/friend.txt");
			// URL url = new URL("http://www.google.com/search?q=" + username);
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		if (cs != null) {
			String string = cs.toString();
			Integer indx = string.indexOf(username);
			// String substring = string.substring(indx, indx + 1);
			// datasource.createFriend(substring, "fone", "email", "address");

			String[] arg = string.split("\n");
			datasource.createFriend(arg[2], "fone", "email", "address");

		}else {
			datasource.createFriend(exp, "fone", "email", "address");
		}

		// loading events
		try {
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/event.txt");
			cs = Authenticate.getURLContent(url);
			// do something with the URL...
		} catch (IOException ioex) {
			exp = ioex.toString();
		}
		if (cs != null) {
			String string = cs.toString();
			Integer indx = string.indexOf(username);
			// String substring = string.substring(indx, indx + 10);

			// datasource.createEvent(substring, "part", "time", "location",
			// "locationLat", "locationLng", "type", "originator");

			String[] arg = string.split("\n");
			// datasource.createEvent(arg[2], "part", "time", "location",
			// "locationLat", "locationLng", "type", "originator");

		}
		List<Friend> values = datasource.getAllFriends();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Friend> adapter = (ArrayAdapter<Friend>) getListAdapter();
		Friend friend = null;
		switch (view.getId()) {
		case R.id.add:
			datasource.close();
			Intent intent = new Intent(MyDatabaseActivity.this,
					HomepageActivity.class);
			startActivity(intent);
			finishActivity(-1);

			/*
			 * String[] comments = new String[] { "Cool", "Very nice", "Hate it"
			 * }; int nextInt = new Random().nextInt(3); // Save the new comment
			 * to the database friend =
			 * datasource.createFriend(comments[nextInt], "fone", "email",
			 * "address"); adapter.add(friend);
			 */
			break;
		case R.id.delete:

			if (getListAdapter().getCount() > 0) {
				friend = (Friend) getListAdapter().getItem(0);
				datasource.deleteFriend(friend);
				adapter.remove(friend);
			}

			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}