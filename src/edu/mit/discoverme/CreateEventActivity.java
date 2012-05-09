package edu.mit.discoverme;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateEventActivity extends Activity {// implements
													// OnClickListener{
	private MyDataSource datasource;
	Button next;
	Button back;

	protected EditText editTextTitle;
	protected EditText editTextParticipants;
	protected EditText editTextLocation;
	protected TextView viewTextParticipants;
	protected TextView viewTextMap;
	protected TextView viewTextTitle;
	protected String locationLng;
	protected String locationLat;
	protected CheckedTextView check;
	protected CustomTimePicker timePicker;
	protected Button proposeChange;
	protected Button viewParticipantsButton;
	protected Button viewMapButton;
	protected LinearLayout proposeChangeArea;
	protected TextView activityTitle;

	protected CheckedTextView food;
	protected CheckedTextView silence;
	protected CheckedTextView it;
	protected TextView locationSuggestionLabel;

	Boolean[] selection = { false, false, false }; // [0]: food, [1]: silence,
													// [2]: it

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);

		back = (Button) (findViewById(R.id.backButton));
		back.setText("Back");
		back.setOnClickListener(onCancelClick);

		next = (Button) (findViewById(R.id.nextButton));
		next.setText("Send Invite");
		next.setOnClickListener(onPublishClick);

		activityTitle = (TextView) findViewById(R.id.navbar_title);
		activityTitle.setText(R.string.activityTitleEventCreateNew);

		check = (CheckedTextView) (findViewById(R.id.checkBox1));
		check.setOnClickListener(onCheckTap);

		// Set up the "Event Title"
		editTextTitle = (EditText) (findViewById(R.id.create_event_editview_title));

		// Set up listeners for "Selecting Participants"
		editTextParticipants = (EditText) (findViewById(R.id.create_event_edittext_participants));
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);

		// Set up listeners for "Selecting Location"
		editTextLocation = (EditText) (findViewById(R.id.create_event_edittext_location));
		editTextLocation.setOnClickListener(onEditTextLocationClick);

		proposeChange = (Button) (findViewById(R.id.create_event_propose_change_button));
		proposeChangeArea = (LinearLayout) (findViewById(R.id.create_event_propose_change_area));

		// Set up the Time Picker
		timePicker = (CustomTimePicker) (findViewById(R.id.create_event_timepicker));
		Date date = new Date();
		timePicker.setCurrentHour(date.getHours());
		timePicker.setCurrentMinute(date.getMinutes());

		// get buttons
		food = (CheckedTextView) findViewById(R.id.foodButton);
		silence = (CheckedTextView) findViewById(R.id.silenceButton);
		it = (CheckedTextView) findViewById(R.id.itButton);
		locationSuggestionLabel = (TextView) findViewById(R.id.locations_req_text);
		
		viewTextParticipants = (TextView)findViewById(R.id.view_event_view_participants);
		viewParticipantsButton = (Button)findViewById(R.id.view_event_participants_button);
		viewTextMap = (TextView)findViewById(R.id.view_event_view_map);
		viewMapButton = (Button)findViewById(R.id.view_event_map_button);
		viewTextTitle = (TextView)findViewById(R.id.view_event_view_title);

		food.setOnClickListener(onFoodRequestClick);
		silence.setOnClickListener(onSilenceRequestClick);
		it.setOnClickListener(onITRequestClick);
		
		viewTextParticipants.setVisibility(View.GONE);
		viewParticipantsButton.setVisibility(View.GONE);
		viewTextMap.setVisibility(View.GONE);
		viewMapButton.setVisibility(View.GONE);
		viewTextTitle.setVisibility(View.GONE);

		// friends = getResources().getStringArray(R.array.friends_array);

		// selected = new int[friends.length];

		// ListView l = (ListView) findViewById(R.id.meeting_reqs_view);
		// l.setBackgroundColor(Color.WHITE);
		// l.setCacheColorHint(Color.WHITE);
		// l.setAdapter(new MyAdapter(this, R.layout.suggested_location,
		// meetingReqs));

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (1000): {
			if (resultCode == Activity.RESULT_OK) {
				String location = data.getStringExtra("LocationName");
				locationLat = data.getStringExtra("LocationLat");
				locationLng = data.getStringExtra("LocationLat");

				editTextLocation = (EditText) (findViewById(R.id.create_event_edittext_location));
				editTextLocation.setText(location);
			}
			break;
		}
		case (2000): {
			if (resultCode == Activity.RESULT_OK) {
				String participants = data.getStringExtra("participants");
				editTextParticipants = (EditText) (findViewById(R.id.create_event_edittext_participants));
				editTextParticipants.setText(participants);
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
			Intent intent = new Intent(CreateEventActivity.this,
					ParticipantListingActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivityForResult(intent, 2000);

		}
	};

	protected OnClickListener onEditTextLocationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Do something when "Select Location" is clicked.
			
			// Get participants (and sanitize)
			String participantsString = editTextParticipants.getText().toString().replace(" ", "");
			String participants[] = participantsString.split(",");
			
			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(CreateEventActivity.this,
					SelectEventLocationActivity.class);
			intent.putExtra("participants", participants);
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

			String textTitle = editTextTitle.getText().toString();
			String textParticipants = editTextParticipants.getText().toString();
			String textLocation = editTextLocation.getText().toString();

			if (textTitle.length() == 0) {
				Toast.makeText(getApplicationContext(),
						"Please enter an Event Title", Toast.LENGTH_SHORT)
						.show();
			} else if (textParticipants.length() == 0) {
				Toast.makeText(getApplicationContext(),
						"Please select Participants for the event.",
						Toast.LENGTH_SHORT).show();
			} else if (textLocation.length() == 0) {
				Toast.makeText(getApplicationContext(),
						"Please select an Event Location.", Toast.LENGTH_SHORT)
						.show();
			} else {
				// TODO Auto-generated method stub
				datasource = new MyDataSource(CreateEventActivity.this);
				datasource.open();

				SharedPreferences prefs = getSharedPreferences("credentials",
						Context.MODE_WORLD_READABLE);
				String username = prefs.getString("username", "none");
				String eventUniqueID =username+ServerLink.indx; 
				String newEvent = (editTextTitle.getText()).toString();
				String newParticipants = (editTextParticipants.getText())
						.toString();
				String[] arg = newParticipants.split(",");
				String newRsvp = "";
				for (int i = 0; i < arg.length; i++)
					newRsvp = newRsvp + ",";
				String newLocation = (editTextLocation.getText()).toString();
				String newLocationLNG = locationLng;
				String newLocationLAT = locationLat;
				String newTime = (String.valueOf(timePicker.getCurrentHour()))
						+ " " + (String.valueOf(timePicker.getCurrentMinute()));
				String newType="";
				if (check.isChecked())
					newType = "closed";
				else
					newType = "open";
				Event theEvent = datasource.createEvent(eventUniqueID,
						newEvent,
						newParticipants, newRsvp, newTime, newLocation,
						newLocationLAT, newLocationLNG, newType);
				datasource.close();

				ServerLink.createEvent(username, theEvent);
				Toast.makeText(getApplicationContext(),
						getString(R.string.publishEventMesg),
						Toast.LENGTH_SHORT).show();
				finish();
			}

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

	private final OnClickListener onFoodRequestClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selection[0] = !selection[0];
			if (!food.isChecked()) {
				food.setChecked(true);
				food.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
			} else {
				food.setChecked(false);
				food.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
			}
		}
	};

	private final OnClickListener onSilenceRequestClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selection[1] = !selection[1];
			if (!silence.isChecked()) {
				silence.setChecked(true);
				silence.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
			} else {
				silence.setChecked(false);
				silence.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
			}
		}

	};

	private final OnClickListener onITRequestClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selection[2] = !selection[2];
			if (!it.isChecked()) {
				it.setChecked(true);
				it.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
			} else {
				it.setChecked(false);
				it.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
			}
		}
	};

	// private final void getSelected() {
	// if (selected != null && friends != null) {
	// selectedList = "";
	// for (int i = 0; i < selected.length; i++) {
	// if (selected[i] == 1)
	// selectedList = selectedList + friends[i] + ", ";
	//
	// }
	// } else
	// selectedList = "84038y";
	// }

	// @Override
	// public void onClick(View v) {
	// // get the CheckedTextView
	// CheckedTextView ct = mCheckedList.get(v.getTag());
	// if (ct != null) {
	// // change the state and colors
	// ct.toggle();
	// if (ct.isChecked()) {
	// ct.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
	// selection[(Integer)v.getTag()] = 1;
	// } else {
	// ct.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
	// selection[(Integer)v.getTag()] = 0;
	// }
	// // add current state to map
	// mIsChecked.put((Integer) v.getTag(), ct.isChecked());
	// }
	// }
	//
	// private class MyAdapter extends ArrayAdapter<String> {
	//
	// private String[] items;
	//
	// public MyAdapter(Context context, int textViewResourceId, String[] items)
	// {
	// super(context, textViewResourceId, items);
	// this.items = items;
	// }
	//
	// public View getView(final int position, View view, ViewGroup parent) {
	// LayoutInflater vi =
	// (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// view = vi.inflate(R.layout.suggested_location, null);
	//
	// CheckedTextView ct = (CheckedTextView)
	// view.findViewById(R.id.suggested_locations_view);
	// ct.setText(items[position]);
	// if (mIsChecked.get(position) != null) {
	// if (mIsChecked.get(position)) {
	// ct.setChecked(true);
	// ct.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
	// } else {
	// ct.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
	// }
	// }
	// // tag it
	// ct.setTag(position);
	// mCheckedList.put(position, ct);
	// ct.setOnClickListener(CreateEventActivity.this);
	//
	// return view;
	// }
	// }

}
