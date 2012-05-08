package edu.mit.discoverme;

import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AuthenticationActivity extends Activity {
	MyDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication);
		Button cancelB = (Button) (findViewById(R.id.cancel));
		cancelB.setOnClickListener(onCancelClick);

		Button enterB = (Button) (findViewById(R.id.enter));
		enterB.setOnClickListener(onEnterClick);

	}

	private final OnClickListener onEnterClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked

			TextView username = (TextView) findViewById(R.id.username);
			TextView password = (TextView) findViewById(R.id.password);

			String usr = username.getText().toString();
			String pswrd = password.getText().toString();

			Boolean auth = Authenticate.check(usr, pswrd);
			if (auth) {
				SharedPreferences prefs = getSharedPreferences("credentials",
						Context.MODE_WORLD_READABLE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("username", usr);
				editor.putString("password", pswrd);
				editor.commit();
				datasource = new MyDataSource(AuthenticationActivity.this);
				datasource.open();
				loadFriends();
				loadEvents();
				datasource.close();
				Toast.makeText(getApplicationContext(), "valid credentials!!",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(AuthenticationActivity.this,
						GDDiscoverMeActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "invalid credentials",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private final OnClickListener onCancelClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("event clicked");
		}
	};

	private final void loadFriends() {
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		String username = prefs.getString("username", "none");
		String password = prefs.getString("password", "none");

		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/friend.txt");
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

	private final void loadEvents() {
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		String username = prefs.getString("username", "none");
		String password = prefs.getString("password", "none");

		CharSequence cs = null;
		String exp = "";
		try {
			// URL url = new URL("http://www.google.com/search?q=" + username);
			URL url = new URL(
					"http://people.csail.mit.edu/culim/projects/discoverme/event.txt");
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
				datasource.createEvent(one[0], one[1], one[2], one[3], one[4],
						one[5], one[6], one[7]);
				// datasource.createEvent(arg[0], "part", "time", "location",
				// "locationLat", "locationLng", "type", "originatoer");
			}
		} else {
			datasource.createEvent(exp, "part", "time", "location",
					"locationLat", "locationLng", "type", "originatoer");
		}

	}

}
