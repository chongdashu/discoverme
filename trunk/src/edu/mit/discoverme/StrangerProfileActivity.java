package edu.mit.discoverme;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StrangerProfileActivity extends Activity {
	private MyDataSource datasource;

	long notifID;
	String friendName;
	String friendFone;
	String friendEmail;
	String friendAddress;
	String type;
	private Notif theNotif;

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

		type = theNotif.getType();
		String details = theNotif.getDetail();
		String[] arg = details.split(";");
		if (arg != null) {
		friendName = arg[0];
		friendFone = arg[1];
		friendEmail = arg[2];
		friendAddress = arg[3];
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
		// deleteFriend.setOnClickListener(onDeleteClick);

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
		if (profileType.equals("friendReq")) {
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.GONE);
			approve.setVisibility(View.VISIBLE);
			decline.setVisibility(View.VISIBLE);
			addedAlready.setVisibility(View.GONE);
		} else if (profileType.equals("friendRes")) {
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.GONE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.VISIBLE);
		} else {
			nameField.setText(R.string.typeFriend);
		}


	}

	private final OnClickListener onApproveClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			StateManager appState = ((StateManager) getApplicationContext());

			// add to friends list
			String[] friends = appState.getFriends();//
			String[] newFriends = new String[friends.length + 1];
			for (int i = 0; i < friends.length; i++)
				newFriends[i] = friends[i];
			newFriends[friends.length] = friendName;
			Arrays.sort(newFriends);
			appState.setFriends(newFriends);

			// change type to friends in the directory
			String[] directorynames = appState.getDirectoryNames();
			String[] directoryTypes = appState.getDirectory_friendType();
			int indexPerson = Arrays.binarySearch(directorynames, friendName);
			String stF = getString(R.string.typeFriend);
			directoryTypes[indexPerson] = stF;
			appState.setDirectory_friendType(directoryTypes);

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
			StateManager appState = ((StateManager) getApplicationContext());
			String[] directorynames = appState.getDirectoryNames();
			String[] directoryTypes = appState.getDirectory_friendType();
			int indexPerson = Arrays.binarySearch(directorynames, friendName);
			String stS = getString(R.string.typeStranger);
			directoryTypes[indexPerson] = stS;
			appState.setDirectory_friendType(directoryTypes);
			// go back to last page
			// and flash a message on screen saying something
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

}
