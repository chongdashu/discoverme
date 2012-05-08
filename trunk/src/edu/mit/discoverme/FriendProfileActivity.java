package edu.mit.discoverme;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FriendProfileActivity extends Activity {
	private MyDataSource datasource;

	long friendID;
	String friendName;
	String friendFone;
	String friendEmail;
	String friendAddress;
	String type;
	private Friend theFriend;

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
		friendID = intent.getLongExtra("friendID", 0);
		type = intent.getStringExtra("type");

		datasource = new MyDataSource(this);
		datasource.open();
		theFriend = datasource.getFriend(friendID);

		friendName = theFriend.getName();
		friendFone = theFriend.getFone();
		friendEmail = theFriend.getEmail();
		friendAddress = theFriend.getAddress();

		/*
		 * friendID = intent.getLongExtra("friendID", 0); friendName =
		 * intent.getStringExtra("friendName"); friendFone =
		 * intent.getStringExtra("friendFone"); friendEmail =
		 * intent.getStringExtra("friendEmail"); friendAddress =
		 * intent.getStringExtra("friendAddress"); type =
		 * intent.getStringExtra("type");
		 */
		Button add = (Button) findViewById(R.id.addAsFriendButton);
		add.setOnClickListener(onAddClick);

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
			add.setVisibility(View.GONE);
			deleteFriend.setVisibility(View.VISIBLE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
			addedAlready.setVisibility(View.GONE);


	}

	private final OnClickListener onAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			StateManager appState = ((StateManager) getApplicationContext());

			// adding to friends list
			String[] friends = appState.getFriends();//
			String[] newFriends = new String[friends.length + 1];
			int indexFriends = 0;
			int added = 0;
			for (int i = 0; i < friends.length; i++)
				newFriends[i] = friends[i];
			newFriends[friends.length] = friendName;
			Arrays.sort(newFriends);
			appState.setFriends(newFriends);

			// change type to pending res in directory list
			String[] directorynames = appState.getDirectoryNames();
			String[] directoryTypes = appState.getDirectory_friendType();
			int indexPerson = Arrays.binarySearch(directorynames, friendName);
			String stPres = getString(R.string.typePendingRes);
			directoryTypes[indexPerson] = stPres;
			appState.setDirectory_friendType(directoryTypes);

			// go back to last page
			// and flash a message on screen saying friend added
			Toast.makeText(getApplicationContext(),
					getString(R.string.addAsFriendMesg), Toast.LENGTH_SHORT)
					.show();
			finish();

		}
	};
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

	private final OnClickListener onDeleteClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					FriendProfileActivity.this);
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
	private final OnClickListener onBackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};

}
