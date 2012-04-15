package edu.mit.discoverme;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEventActivity extends Activity {

	Button next;
	
	protected EditText editTextTitle;
	protected EditText editTextParticipants;
	protected EditText editTextLocation;
	protected CheckedTextView check;
	protected CustomTimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		
		Button back = (Button) (findViewById(R.id.backButton));
		back.setText("Back");
		back.setOnClickListener(onCancelClick);

		next = (Button) (findViewById(R.id.nextButton));
		next.setText("Send Invite");
		next.setOnClickListener(onPublishClick);

		check = (CheckedTextView) (findViewById(R.id.checkBox1));
		check.setOnClickListener(onCheckTap);

		// Set up the "Event Title"
		editTextTitle = (EditText)(findViewById(R.id.create_event_editview_title));
		
		// Set up listeners for "Selecting Participants"
		editTextParticipants = (EditText) (findViewById(R.id.create_event_edittext_participants));
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);

		// Set up listeners for "Selecting Location"
		editTextLocation = (EditText) (findViewById(R.id.create_event_edittext_location));
		editTextLocation.setOnClickListener(onEditTextLocationClick);

		// Set up the Time Picker
		timePicker = (CustomTimePicker) (findViewById(R.id.create_event_timepicker));
		Date date = new Date();
		timePicker.setCurrentHour(date.getHours());
		timePicker.setCurrentMinute(date.getMinutes());

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (1000): {
			if (resultCode == Activity.RESULT_OK) {
				String location = data.getStringExtra("LocationName");
				editTextLocation = (EditText)(findViewById(R.id.create_event_edittext_location));
				editTextLocation.setText(location);
			}
			break;
		}
		case (2000): {
			if (resultCode == Activity.RESULT_OK) {
				String location = data.getStringExtra("participants");
				editTextParticipants = (EditText) (findViewById(R.id.create_event_edittext_participants));
				editTextParticipants.setText(location);
			}
			break;
		}
		}
	}

	private final OnClickListener onEditTextParticipantsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Do something when "Select Participants" is clicked.

			// We launch the "Select Participants Activity"
			Intent intent = new Intent(CreateEventActivity.this, ParticipantListingActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivityForResult(intent, 2000);

		}
	};

	private final OnClickListener onEditTextLocationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Do something when "Select Location" is clicked.

			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(CreateEventActivity.this, SelectEventLocationActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivityForResult(intent, 1000);

		}
	};

	private final OnClickListener onCancelClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};
	private final OnClickListener onPublishClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.publishEventMesg), Toast.LENGTH_SHORT)
					.show();
			finish();

		}
	};

	private final OnClickListener onCheckTap = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			if (!check.isChecked()) {
				check.setChecked(true);
				check.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);

			} else {
				check.setChecked(false);
				check.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
			}

		}
	};

}
