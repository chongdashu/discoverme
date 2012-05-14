package edu.mit.discoverme;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	String personName;
	String email;

	MyDataSource mydatasource;
	DirDataSource dirdatasource;

	List<Friend> directory;
	List<Friend> friends;

	String[] emails;
	String[] names;
	String[] addresss;
	String[] phones;
	Friend theFriend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Button back = (Button) (findViewById(R.id.backButton));
		back.setOnClickListener(onBackClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.INVISIBLE);

		String profileType;

		TextView activityTitle = (TextView) findViewById(R.id.navbar_title);
		activityTitle.setText("Profile Page");

		Intent intent = getIntent();
		personName = intent.getStringExtra("personName");

		Button add = (Button) findViewById(R.id.addAsFriendButton);
		add.setOnClickListener(onAddClick);

		Button addedAlready = (Button) findViewById(R.id.pendingResponseButton);

		Button deleteFriend = (Button) findViewById(R.id.deleteFriendButton);
		deleteFriend.setOnClickListener(onDeleteClick);

		Button approve = (Button) findViewById(R.id.addPendingYesButton);
		Button decline = (Button) findViewById(R.id.addPendingNoButton);


		TextView nameField = (TextView) findViewById(R.id.personName);
		TextView emailField = (TextView) findViewById(R.id.personEmail);
		TextView phoneField = (TextView) findViewById(R.id.personPhone);
		TextView addressField = (TextView) findViewById(R.id.personAddress);

		StateManager appState = ((StateManager) getApplicationContext());

		populateFields();

		int key = 0;
		int len = names.length;
		for (int i = 0; i < len; i++)
			// make this number(3) adaptive
			if (names[i].equals(personName.trim())) {
				key = i;
				break;
			}

		String stF = getString(R.string.typeFriend);
		String stS = getString(R.string.typeStranger);
		String stPreq = getString(R.string.typePendingReq);
		String stPres = getString(R.string.typePendingRes);
		
		email = emails[key];

		// profileType = types[key];
		nameField.setText(personName);
		emailField.setText(emails[key]);
		phoneField.setText(phones[key]);
		addressField.setText(addresss[key]);
		// profileType = types[key];
		profileType = getProfileType(key);
		// this check should be
		if (profileType.equals(stF)) {
			nameField.setText(personName);
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.VISIBLE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.GONE);
		} else if (profileType.equals(stS)) {
			nameField.setText(personName);
			add.setVisibility(View.VISIBLE);
			deleteFriend.setVisibility(View.GONE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.GONE);
		}
		
		else {
			nameField.setText(R.string.typeFriend);
		}
		
		String username = email.trim().split("@")[0];
		StateManager stateManager= (StateManager) getApplicationContext();
		if (username.equals(stateManager.userName)) {
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.GONE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.GONE);
		}
		
		// else if (profileType.equals(stPreq)) {
		// nameField.setText(personName);
		// add.setVisibility(View.GONE);
		// deleteFriend.setVisibility(View.GONE);
		// approve.setVisibility(View.VISIBLE);
		// decline.setVisibility(View.VISIBLE);
		// addedAlready.setVisibility(View.GONE);
		// } else if (profileType.equals(stPres)) {
		// nameField.setText(personName);
		// add.setVisibility(View.GONE);
		// deleteFriend.setVisibility(View.GONE);
		// approve.setVisibility(View.GONE);
		// decline.setVisibility(View.GONE);
		// addedAlready.setVisibility(View.VISIBLE);
		// }
	

	}

	private String getProfileType(int index) {
		// TODO Auto-generated method stub
		String result;
		Friend direct = directory.get(index);
		int i = friends.indexOf(direct);
		Boolean rt = friends.contains(direct);
		Boolean other = friends.equals(direct);
		result = "stranger";
		for (Friend friend : friends) {
			if (friend.getEmail().split("@")[0].equals(direct.getEmail().split(
					"@")[0])) {
				result = "friend";
				theFriend = friend;
				break;
			}
		}

		return result;
	}

	private void populateFields() {
		// TODO Auto-generated method stub

		// initialize friends list
		mydatasource = new MyDataSource(this);
		mydatasource.open();
		friends = mydatasource.getAllFriends();
		mydatasource.close();

		// initialize directory
		dirdatasource = new DirDataSource(this);
		dirdatasource.open();
		directory = dirdatasource.getAllPeople();
		dirdatasource.close();

		// set size of String[]
		phones = new String[directory.size()];
		emails = new String[directory.size()];
		names = new String[directory.size()];
		addresss = new String[directory.size()];

		for (int person = 0; person < directory.size(); person++) {
			phones[person] = directory.get(person).getFone();
			emails[person] = directory.get(person).getEmail();
			names[person] = directory.get(person).getName();
			addresss[person] = directory.get(person).getAddress();
		}

	}

	private final OnClickListener onAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// server activity
			SharedPreferences prefs = getSharedPreferences("credentials",
					Context.MODE_WORLD_READABLE);

			String username = prefs.getString("username", "none");

			StateManager stm = (StateManager) getApplicationContext();
			String firstname = stm.fullName;

			TextView emailField = (TextView) findViewById(R.id.personEmail);
			String email = emailField.getText().toString();
			String[] arg = email.split("@");
			if (arg.length > 1)
				email = arg[0];
			ServerLink.sendFriendRequest(username, email, firstname);
			// server activity ends here

			// go back to last page
			// and flash a message on screen saying friend added
			Toast.makeText(getApplicationContext(),
					getString(R.string.addAsFriendMesg),
					Toast.LENGTH_SHORT)
					.show();
			finish();

		}
	};

	private final OnClickListener onDeleteClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ProfileActivity.this);
			builder.setMessage("Are you sure you want to delete this friend?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// server activity
									SharedPreferences prefs = getSharedPreferences(
											"credentials",
											Context.MODE_WORLD_READABLE);
									String username = prefs.getString(
											"username", "none");
									StateManager stm = (StateManager) getApplicationContext();

									String firstname = stm.fullName;

									ServerLink.deleteFriend(username,
											theFriend.getMITId());
									mydatasource.open();
									mydatasource.deleteFriend(theFriend);
									mydatasource.close();
									// server activity ends here
									Toast.makeText(
											getApplicationContext(),
											getString(R.string.deleteFriendMesg),
											Toast.LENGTH_SHORT).show();
									finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	};
	private final OnClickListener onBackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};

}
