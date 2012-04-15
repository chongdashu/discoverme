package edu.mit.discoverme;

import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;

public class ViewEventActivity extends CreateEventActivity {

	protected String eventTitle;
	protected String[] participants;
	protected boolean closedEvent;
	protected int timeHrs;
	protected int timeMins;
	protected GeoPoint location;
	protected String locationName;
	protected int latE6;
	protected int lngE6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		eventTitle = intent.getStringExtra("eventTitle");

		participants = intent.getStringArrayExtra("participants");
		closedEvent = intent.getBooleanExtra("closedEvent", true);

		timeHrs = intent.getIntExtra("timeHrs", 0);
		timeMins = intent.getIntExtra("timeMins", 0);

		locationName = intent.getStringExtra("locationName");
		latE6 = intent.getIntExtra("lat", 0);
		lngE6 = intent.getIntExtra("lng", 0);

		inititialize();
	}

	private void inititialize() {

		editTextTitle.setText(eventTitle);
		editTextParticipants.setText(Utils.foldParticipantsList(participants));
		if (closedEvent) {
			check.setChecked(true);
			check.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		}
		else
		{
			check.setChecked(false);
			check.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		}
		editTextLocation.setText(locationName);
		timePicker.setCurrentHour(timeHrs);
		timePicker.setCurrentMinute(timeMins);

	}

}
