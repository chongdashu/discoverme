package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		String profileType;
		String personName;

		Intent intent = getIntent();
		personName = intent.getStringExtra("personName");

		Button add = (Button) findViewById(R.id.addAsFriendButton);
		add.setOnClickListener(onAddClick);
		//
		Button approve = (Button) findViewById(R.id.addPendingYesButton);
		approve.setOnClickListener(onApproveClick);

		Button decline = (Button) findViewById(R.id.addPendingNoButton);
		decline.setOnClickListener(onDeclineClick);

		TextView nameField = (TextView) findViewById(R.id.personName);
		TextView emailField = (TextView) findViewById(R.id.personEmail);
		TextView phoneField = (TextView) findViewById(R.id.personPhone);
		TextView addressField = (TextView) findViewById(R.id.personAddress);

		// android sqlight ???
		String[] names = getResources().getStringArray(R.array.name_array);
		String[] emails = getResources().getStringArray(R.array.email_array);
		String[] phones = getResources().getStringArray(R.array.phone_array);
		String[] addresss = getResources()
				.getStringArray(R.array.address_array);
		String[] types = getResources().getStringArray(
				R.array.friend_type_array);

		int key = 0;
		for (int i = 0; i < 3; i++)
			// make this number(3) adaptive
			if (names[i].equals(personName.trim())) {
				key = i;
				break;
			}

		profileType = types[key];
		nameField.setText(personName);
		emailField.setText(emails[key]);
		phoneField.setText(phones[key]);
		addressField.setText(addresss[key]);
		profileType = types[key];
		// this check should be
		if (profileType.equals("friend")) {
			nameField.setText(personName);
			add.setVisibility(View.GONE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
		} else if (profileType.equals("stranger")) {
			nameField.setText(personName);
			add.setVisibility(View.VISIBLE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
		} else if (profileType.equals("pending")) {
			nameField.setText(personName);
			add.setVisibility(View.GONE);
			approve.setVisibility(View.VISIBLE);
			decline.setVisibility(View.VISIBLE);
		}
 else {
			nameField.setText(R.string.typeFriend);
		}

	}

	private final OnClickListener onAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
				finishActivity(-1);
			// and flash a message on screen saying friend added
			Toast.makeText(getApplicationContext(),
					"added as Friend", Toast.LENGTH_SHORT).show();
			// and then go back to previous page

		}
	};
	private final OnClickListener onApproveClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
				finishActivity(-1);
			// and flash a message on screen saying something
			Toast.makeText(getApplicationContext(),
					"Approved as Friend", Toast.LENGTH_SHORT).show();
			// and then go back to previous page
		}
	};
	private final OnClickListener onDeclineClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
				finishActivity(-1);
			// and flash a message on screen saying something
			Toast.makeText(getApplicationContext(),
					"Declined Friend Request", Toast.LENGTH_SHORT).show();
			// and then go back to previous page
		}
	};

}
