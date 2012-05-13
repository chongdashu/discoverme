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

public class StrangerProfileActivity extends Activity {
	private MyDataSource datasource;
	private DirDataSource dirdatasource;

	long notifID;
	String friendName;
	String friendFone;
	String friendEmail;
	String friendAddress;
	String type;
	private Notif theNotif;
	List<Friend> people;
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
		notifID = intent.getLongExtra("notifID", 0);

		datasource = new MyDataSource(this);
		datasource.open();
		theNotif = datasource.getNotif(notifID);
		datasource.close();

		type = theNotif.getType();

		dirdatasource = new DirDataSource(this);
		dirdatasource.open();
		String details = theNotif.getDetail();
		// String[] arg = details.split(";");
		people = dirdatasource.getAllPeople();
		dirdatasource.close();

		for (Friend person : people) {
			if (person.getMITId().equals(details)) {
				theFriend = person;
				break;
			}
		}

		if (theFriend != null) {
			friendName = theFriend.getName();
			friendFone = theFriend.getFone();
			friendEmail = theFriend.getEmail();
			friendAddress = theFriend.getAddress();
		} else {

			friendName = "you went the wrong way";
			friendFone = "no fone for you";
			friendEmail = "ney no email either";
			friendAddress = "n no address";
		}
		/*
		 * friendID = intent.getLongExtra("friendID", 0); friendName =
		 * intent.getStringExtra("friendName"); friendFone =
		 * intent.getStringExtra("friendFone"); friendEmail =
		 * intent.getStringExtra("friendEmail"); friendAddress =
		 * intent.getStringExtra("friendAddress"); type =
		 * intent.getStringExtra("type");
		 */
		Button add = (Button) findViewById(R.id.addAsFriendButton);
		// add.setOnClickListener(onAddClick);

		Button addedAlready = (Button) findViewById(R.id.pendingResponseButton);

		Button deleteFriend = (Button) findViewById(R.id.deleteFriendButton);
		deleteFriend.setOnClickListener(onDeleteClick);

		Button approve = (Button) findViewById(R.id.addPendingYesButton);
		approve.setOnClickListener(onApproveClick);

		Button decline = (Button) findViewById(R.id.addPendingNoButton);
		decline.setOnClickListener(onDeclineClick);

		TextView nameField = (TextView) findViewById(R.id.personName);
		TextView emailField = (TextView) findViewById(R.id.personEmail);
		TextView phoneField = (TextView) findViewById(R.id.personPhone);
		TextView addressField = (TextView) findViewById(R.id.personAddress);

		profileType = type;
		nameField.setText(friendName);
		emailField.setText(friendEmail);
		phoneField.setText(friendFone);
		addressField.setText(friendAddress);
		// this check should be
		if (profileType.equals("FriendReq")) {
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.GONE);
			approve.setVisibility(View.VISIBLE);
			decline.setVisibility(View.VISIBLE);
			addedAlready.setVisibility(View.GONE);
		} else if (profileType.equals("FriendRes")) {
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.VISIBLE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.GONE);
		} else {
			nameField.setText(R.string.typeFriend);
		}

	}

	private final OnClickListener onApproveClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// server activity
			SharedPreferences prefs = getSharedPreferences("credentials",
					Context.MODE_WORLD_READABLE);
			String username = prefs.getString("username", "none");
			StateManager stm = (StateManager) getApplicationContext();
			String firstname = stm.fullName;

			String email = theFriend.getMITId();
			ServerLink.addFriend(username, email, firstname);
			datasource.open();
			datasource.createFriend(theFriend.getName(), theFriend.getFone(),
					theFriend.getEmail(), theFriend.getAddress());
			Notif notif = datasource.getNotif(notifID);
			datasource.deleteNotif(notif);
			datasource.close();

			// server activity ends here

			// and flash a message on screen saying something
			Toast.makeText(getApplicationContext(),
					getString(R.string.addPendingYesMesg), Toast.LENGTH_SHORT)
					.show();
			finish();
		}
	};
	private final OnClickListener onDeclineClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// change type to stranger in directory list
			// StateManager appState = ((StateManager) getApplicationContext());
			// String[] directorynames = appState.getDirectoryNames();
			// String[] directoryTypes = appState.getDirectory_friendType();
			// int indexPerson = Arrays.binarySearch(directorynames,
			// friendName);
			// String stS = getString(R.string.typeStranger);
			// directoryTypes[indexPerson] = stS;
			// appState.setDirectory_friendType(directoryTypes);
			// go back to last page
			// and flash a message on screen saying something
			datasource.open();
			Notif notif = datasource.getNotif(notifID);
			datasource.deleteNotif(notif);
			datasource.close();

			Toast.makeText(getApplicationContext(),
					getString(R.string.addPendingNoMesg), Toast.LENGTH_SHORT)
					.show();
			finish();
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

	private final OnClickListener onDeleteClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					StrangerProfileActivity.this);
			builder.setMessage("Are you sure you want to delete this friend?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									datasource.deleteFriend(theFriend);
									datasource.close();
									SharedPreferences prefs = getSharedPreferences(
											"credentials",
											Context.MODE_WORLD_READABLE);
									String username = prefs.getString(
											"username", "none");
									ServerLink.deleteFriend(username,
											theFriend.getEmail());

									// something
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

}
