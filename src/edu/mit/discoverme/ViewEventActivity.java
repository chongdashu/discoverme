package edu.mit.discoverme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
		int eventID = intent.getIntExtra("eventId", 0);

		StateManager appState = ((StateManager) getApplicationContext());
		String[] events = appState.getEvents();
		String[] parts = appState.getParticipants();
		String[] types = appState.getEventType();
		String[] locations = appState.getLocations();
		String[] times = appState.getTime();
		String stC = getString(R.string.typeClosed);
		String stO = getString(R.string.typeOpen);

		eventTitle = events[eventID];
		participants = parts[eventID].split(",");
		if (types[eventID] == stO)
			closedEvent = false;
		else
			closedEvent = true;
		String time = times[eventID];
		String[] arg = time.split(" ");
		timeHrs = Integer.valueOf(arg[0]);
		timeMins = Integer.valueOf(arg[1]);

		locationName = locations[eventID];
		// latE6 = intent.getIntExtra("lat", 0);
		// lngE6 = intent.getIntExtra("lng", 0);

		inititialize();
	}

	private void inititialize() {
		
		next.setVisibility(View.INVISIBLE);
		
		editTextTitle.setFocusable(false);
		editTextLocation.setFocusable(false);
		editTextParticipants.setFocusable(false);
		check.setFocusable(false);
		timePicker.setFocusable(false);
		
		editTextTitle.setEnabled(false);
		editTextLocation.setEnabled(false);
		editTextParticipants.setEnabled(false);
		check.setEnabled(false);
		timePicker.setEnabled(false);
		
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
