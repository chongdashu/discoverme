package edu.mit.discoverme;

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

		Button addedAlready = (Button) findViewById(R.id.pendingResponseButton);

		Button deleteFriend = (Button) findViewById(R.id.deleteFriendButton);
		deleteFriend.setOnClickListener(onDeleteClick);

		Button approve = (Button) findViewById(R.id.addPendingYesButton);

		Button decline = (Button) findViewById(R.id.addPendingNoButton);

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
									SharedPreferences prefs = getSharedPreferences("credentials",
											Context.MODE_WORLD_READABLE);
									String username = prefs.getString("username", "none");
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
	private final OnClickListener onBackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			datasource.close();
			finish();

		}
	};

}
