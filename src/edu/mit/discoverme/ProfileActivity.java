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

		Button back = (Button) (findViewById(R.id.backButton));
		back.setOnClickListener(onBackClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.INVISIBLE);

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
		int len = names.length;
		for (int i = 0; i < len; i++)
			// make this number(3) adaptive
			if (names[i].equals(personName.trim())) {
				key = i;
				break;
			}

		String stF = getString(R.string.typeFriend);
		String stS = getString(R.string.typeStranger);
		String stP = getString(R.string.typePending);
		profileType = types[key];
		nameField.setText(personName);
		emailField.setText(emails[key]);
		phoneField.setText(phones[key]);
		addressField.setText(addresss[key]);
		profileType = types[key];
		// this check should be
		if (profileType.equals(stF)) {
			nameField.setText(personName);
			add.setVisibility(View.GONE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
		} else if (profileType.equals(stS)) {
			nameField.setText(personName);
			add.setVisibility(View.VISIBLE);
			approve.setVisibility(View.GONE);
			decline.setVisibility(View.GONE);
		} else if (profileType.equals(stP)) {
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
			// go back to last page
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
