package edu.mit.discoverme;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreateEventActivity extends Activity {

	private EditText editTextParticipants;
	private EditText editTextLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);

		// Set up listeners for "Selecting Participants"
		EditText editTextParticipants = (EditText) (findViewById(R.id.create_event_edittext_participants));
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);

		// Set up listeners for "Selecting Location"
		EditText editTextLocation = (EditText) (findViewById(R.id.create_event_edittext_location));
		editTextLocation.setOnClickListener(onEditTextLocationClick);

		// Set up the Time Picker
		CustomTimePicker timePicker = (CustomTimePicker) (findViewById(R.id.create_event_timepicker));
		Date date = new Date();
		timePicker.setCurrentHour(date.getHours());
		timePicker.setCurrentMinute(date.getMinutes());

	}

	private OnClickListener onEditTextParticipantsClick = new OnClickListener() {
		public void onClick(View v) {
			// Do something when "Select Participants" is clicked.

			// We launch the "Select Participants Activity"
			Intent intent = new Intent(CreateEventActivity.this,
					ParticipantListingActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivity(intent);
		}
	};

	private OnClickListener onEditTextLocationClick = new OnClickListener() {
		public void onClick(View v) {
			// Do something when "Select Location" is clicked.

			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(CreateEventActivity.this,
					MapTestActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivity(intent);

		}
	};

}
