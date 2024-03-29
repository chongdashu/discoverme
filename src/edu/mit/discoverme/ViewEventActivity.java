package edu.mit.discoverme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

public class ViewEventActivity extends CreateEventActivity {

	protected long eventId;
	protected String eventUniqueId;
	protected String eventName;
	protected String eventPart;
	protected String eventResponses;
	protected String eventTime;
	protected String eventLocation;
	protected String eventLocationLat;
	protected String eventLocationLng;
	protected String eventType;
	protected String eventOriginator;

	protected int eventID;
	protected String eventTitle;
	protected String[] participants;
	protected String participantsString;
	protected String[] responses;
	protected String responsesString;
	protected boolean closedEvent;
	protected int timeHrs;
	protected int timeMins;
	protected GeoPoint location;
	protected String locationName;
	protected int latE6;
	protected int lngE6;
	protected boolean originatorIsme;
	protected MyDataSource datasource;
	protected Event theEvent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		Intent intent = getIntent();
		eventId = intent.getLongExtra("eventID", 0);

		datasource = new MyDataSource(this);
		datasource.open();
		theEvent = datasource.getEvent(eventId);

		eventUniqueId = theEvent.getEventID();
		eventName = theEvent.getName();
		eventPart = theEvent.getParticipants();
		eventResponses = theEvent.getResponses();
		eventTime = theEvent.getTime();
		eventLocation = theEvent.getLocation();
		eventLocationLat = theEvent.getLocationLat();
		eventLocationLng = theEvent.getLocationLng();
		eventType = theEvent.getType();

		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		String username = prefs.getString("username", "none");
		if (eventUniqueId.startsWith(username))
			eventOriginator = "me";
		else
			eventOriginator = "notme";
		datasource.close();

		/*
		 * eventId = intent.getLongExtra("eventID", 0); eventName =
		 * intent.getStringExtra("eventName"); eventPart =
		 * intent.getStringExtra("eventPart"); eventTime =
		 * intent.getStringExtra("eventTime"); eventLocation =
		 * intent.getStringExtra("eventLocation"); eventLocationLat =
		 * intent.getStringExtra("eventLocationLat"); eventLocationLng =
		 * intent.getStringExtra("eventLocationLng"); eventType =
		 * intent.getStringExtra("eventType"); eventOriginator =
		 * intent.getStringExtra("eventOriginator");
		 */
		/*
		 * StateManager appState = ((StateManager) getApplicationContext());
		 * String[] events = appState.getEvents(); String[] parts =
		 * appState.getParticipants(); String[] types = appState.getEventType();
		 * String[] locations = appState.getLocations(); String[] locations_lat
		 * = appState.getLocationsLAT(); String[] locations_lng =
		 * appState.getLocationsLNG(); String[] times = appState.getTime();
		 * String[] originators = appState.getEventOriginator();
		 * 
		 * String stC = getString(R.string.typeClosed); String stO =
		 * getString(R.string.typeOpen);
		 * 
		 * String stM = getString(R.string.typeMe); String stNM =
		 * getString(R.string.typeNotMe);
		 * 
		 * eventTitle = events[eventID]; participantsString = parts[eventID];
		 * participants = participantsString.split(",");
		 * 
		 * if (types[eventID] == stO) closedEvent = false; else closedEvent =
		 * true; if(originators[eventID].equals(stM)) originatorIsme = true;
		 * else originatorIsme= false; String time = times[eventID]; String[]
		 * arg = time.split(" "); timeHrs = Integer.valueOf(arg[0]); timeMins =
		 * Integer.valueOf(arg[1]);
		 * 
		 * locationName = locations[eventID]; latE6 = (int)
		 * (Float.valueOf(locations_lat[eventID])*1000000); lngE6 = (int)
		 * (Float.valueOf(locations_lng[eventID])*1000000);
		 */
		//

		eventID = (int) eventId;
		eventTitle = eventName;
		participantsString = eventPart;
		//participantsString.trim();
		participants = participantsString.trim().split(",");
		
		responsesString = eventResponses;
		responses = responsesString.split(",");

		if (eventType.equals("open"))
			closedEvent = false;
		else
			closedEvent = true;
		if (eventOriginator.equals("me"))
			originatorIsme = true;
		else
			originatorIsme = false;
		String time = eventTime;
		String[] arg = time.split(" ");
		timeHrs = Integer.valueOf(arg[0]);
		timeMins = Integer.valueOf(arg[1]);

		locationName = eventLocation;
		latE6 = (int) (Float.valueOf(eventLocationLat) * 1000000);
		lngE6 = (int) (Float.valueOf(eventLocationLng) * 1000000);

		inititialize();

	}

	protected void inititialize() {
		
		next.setVisibility(View.INVISIBLE);
		
		editTextLocation.setVisibility(View.GONE);
		editTextParticipants.setVisibility(View.GONE);
		editTextTitle.setVisibility(View.GONE);
		
		viewTextParticipants.setVisibility(View.VISIBLE);
		viewParticipantsButton.setVisibility(View.VISIBLE);
		viewTextMap.setVisibility(View.VISIBLE);
		viewMapButton.setVisibility(View.VISIBLE);
		viewTextTitle.setVisibility(View.VISIBLE);
		
		setAllEnabled();
		
		activityTitle.setText(R.string.activityTitleEventView);
		
		viewTextTitle.setText(eventTitle);
		viewTextParticipants.setText(Utils.foldParticipantsList(participants));
		if (closedEvent) {
			check.setChecked(true);
			check.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		}
		else
		{
			check.setChecked(false);
			check.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		}
		viewTextMap.setText(locationName);
		timePicker.setCurrentHour(timeHrs);
		timePicker.setCurrentMinute(timeMins);
		
		proposeChangeArea.setVisibility(View.VISIBLE);
		proposeChange.setText("Delete Event");
		
		proposeChange.setOnClickListener(onProposeChangeClick);
		if (originatorIsme)
			proposeChange.setVisibility(View.VISIBLE);
		else
			proposeChange.setVisibility(View.INVISIBLE);
		proposeChange.setText(R.string.cancelEventButtonText);
//		food.setVisibility(View.GONE);
//		silence.setVisibility(View.GONE);
//		it.setVisibility(View.GONE);
//		locationSuggestionLabel.setVisibility(View.GONE);
	}
	
	protected OnClickListener onProposeChangeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventActivity.this);
	    	builder.setMessage("Are you sure you to delete this event?")
	    	       .setCancelable(false)
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    	           @Override
					public void onClick(DialogInterface dialog, int id) {
	    	               // Do whatever you want for 'Yes' here. 
	    	        	   dialog.dismiss();

									datasource.open();
									datasource.deleteEvent(theEvent);
									// end of deleting entry
									SharedPreferences prefs = getSharedPreferences(
											"credentials",
											Context.MODE_WORLD_READABLE);
									String username = prefs.getString(
											"username", "none");

									ServerLink.cancelEvent(username, theEvent);
									datasource.close();
	    	        	   Toast.makeText(getApplicationContext(),
	    	   					getString(R.string.cancelEventMsg), Toast.LENGTH_SHORT)
	    	   					.show();
	    	   				finish();
	    	           }
	    	       })
	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    	           @Override
					public void onClick(DialogInterface dialog, int id) {
	    	        	   // Do whatever you want for 'No' here.  
	    	        	   dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert = builder.create();
	    	alert.show();
		}
	}; 
	
	protected void setAllEnabled() 
	{
		editTextTitle.setFocusable(false);
		// editTextLocation.setFocusable(false);
		// editTextLocation.setOnClickListener(onEditTextLocationClick);

		// editTextParticipants.setFocusable(false);
		viewParticipantsButton.setOnClickListener(onEditTextParticipantsClick);
		check.setFocusable(false);
		timePicker.setFocusable(false);
		
		editTextTitle.setEnabled(false);
		viewMapButton.setOnClickListener(onEditTextLocationClick);
		// editTextLocation.setEnabled(false);
//		editTextParticipants.setEnabled(true);
		check.setEnabled(false);
		timePicker.setEnabled(false);
		food.setEnabled(false);
		silence.setEnabled(false);
		it.setEnabled(false);
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

	private final OnClickListener onEditTextLocationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(ViewEventActivity.this, SelectEventLocationActivity.class);
			intent.putExtra("mode", SelectEventLocationActivity.MODE_VIEW);
			intent.putExtra("lat", latE6);
			intent.putExtra("lng", lngE6);
			intent.putExtra("participants", participants);
			intent.putExtra("responses", responses);
			startActivity(intent);

		}
	};

}
