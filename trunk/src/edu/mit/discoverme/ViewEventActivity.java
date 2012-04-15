package edu.mit.discoverme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

public class ViewEventActivity extends CreateEventActivity {

	protected String eventTitle;
	protected String[] participants;
	protected String participantsString;
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
		participantsString = parts[eventID];
		participants = participantsString.split(",");

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

	protected void inititialize() {
		
		next.setVisibility(View.INVISIBLE);
		
		setAllEnabled(false);
		
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
		
		proposeChangeArea.setVisibility(View.VISIBLE);
		proposeChange.setText("Delete Event");
		
		proposeChange.setOnClickListener(onProposeChangeClick);
		
		onEditTextLocationClick =  new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Do something when "Select Location" is clicked.

				// We launch the "Select Location from Map Activity"
				Intent intent = new Intent(ViewEventActivity.this, SelectEventLocationActivity.class);
				intent.putExtra("readOnly", true);
				startActivity(intent);
				// startActivityForResult(intent, 1000);

			}
		};
		
	}
	
	protected OnClickListener onProposeChangeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventActivity.this);
	    	builder.setMessage("Are you sure you to delete this event?")
	    	       .setCancelable(false)
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	               // Do whatever you want for 'Yes' here. 
	    	        	   dialog.dismiss();
	    	        	   Toast.makeText(getApplicationContext(),
	    	   					getString(R.string.cancelEventMsg), Toast.LENGTH_SHORT)
	    	   					.show();
	    	   				finish();
	    	           }
	    	       })
	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   // Do whatever you want for 'No' here.  
	    	        	   dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert = builder.create();
	    	alert.show();
			

		}
	}; 
	
	protected void setAllEnabled(boolean enabled) 
	{
		editTextTitle.setFocusable(false);
		// editTextLocation.setFocusable(false);
		editTextLocation.setOnClickListener(onEditTextLocationClick);

		// editTextParticipants.setFocusable(false);
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);
		check.setFocusable(false);
		timePicker.setFocusable(false);
		
		editTextTitle.setEnabled(false);
		editTextLocation.setEnabled(false);
		editTextParticipants.setEnabled(true);
		check.setEnabled(false);
		timePicker.setEnabled(false);
	}

	private final OnClickListener onEditTextParticipantsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Do something when "Select Participants" is clicked.

			// We launch the "Select Participants Activity"
			Intent intent = new Intent(ViewEventActivity.this,
					ViewParticipantsListActivity.class);
			intent.putExtra("participants", participantsString);
			startActivity(intent);

		}
	};

	protected  OnClickListener onEditTextLocationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Do something when "Select Location" is clicked.

			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(ViewEventActivity.this,
					SelectEventLocationActivity.class);
			// intent.putExtra("popupCode", "eventss");
			startActivityForResult(intent, 1000);

		}
	};

}
