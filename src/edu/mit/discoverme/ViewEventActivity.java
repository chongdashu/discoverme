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

	protected long eventId;
	protected String eventName;
	protected String eventPart;
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
	protected boolean closedEvent;
	protected int timeHrs;
	protected int timeMins;
	protected GeoPoint location;
	protected String locationName;
	protected int latE6;
	protected int lngE6;
	protected boolean originatorIsme;
	private MyDataSource datasource;
	private Event theEvent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		Intent intent = getIntent();
		eventId = intent.getLongExtra("friendID", 0);

		datasource = new MyDataSource(this);
		datasource.open();
		theEvent = datasource.getEvent(eventId);

		eventName = theEvent.getName();
		eventPart = theEvent.getParticipants();
		eventTime = theEvent.getTime();
		eventLocation = theEvent.getLocation();
		eventLocationLat = theEvent.getLocationLat();
		eventLocationLng = theEvent.getLocationLng();
		eventType = theEvent.getType();
		eventOriginator = theEvent.getOriginator();
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
		participants = participantsString.split(",");

		if (eventType == "open")
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
		
		setAllEnabled();
		
		activityTitle.setText(R.string.activityTitleEventView);
		
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
									// delete this event from the event list
									// TODO Auto-generated method stub
									/*
									 * StateManager appState = ((StateManager)
									 * getApplicationContext());
									 * 
									 * // adding to friends list String[] events
									 * = appState.getEvents();// String[]
									 * participants = appState
									 * .getParticipants();// String[] location =
									 * appState.getLocations();// String[]
									 * locationLNG = appState
									 * .getLocationsLNG(); String[] locationLAT
									 * = appState .getLocationsLAT();// String[]
									 * time = appState.getTime();// String[]
									 * type = appState.getEventType();//
									 * String[] eventOriginator = appState
									 * .getEventOriginator();
									 * 
									 * String[] newEvents = new
									 * String[events.length - 1]; String[]
									 * newParticipants = new
									 * String[events.length - 1]; String[]
									 * newLocation = new String[events.length -
									 * 1]; String[] newLocationLNG = new
									 * String[events.length - 1]; String[]
									 * newLocationLAT = new String[events.length
									 * - 1]; String[] newTime = new
									 * String[events.length - 1]; String[]
									 * newType = new String[events.length - 1];
									 * String[] newOrig = new
									 * String[events.length + 1];
									 * 
									 * int j = 0; for (int i = 0; i <
									 * events.length; i++) { if (i != eventID) {
									 * newEvents[j] = events[i];
									 * newParticipants[j] = participants[i];
									 * newLocation[j] = location[i];
									 * newLocationLNG[j] = locationLNG[i];
									 * newLocationLAT[j] = locationLAT[i];
									 * newTime[j] = time[i]; newType[j] =
									 * type[i]; newOrig[j] = eventOriginator[i];
									 * j++; } }
									 * 
									 * appState.setEvents(newEvents);
									 * appState.setParticipants
									 * (newParticipants);
									 * appState.setLocations(newLocation);
									 * appState.setLocationsLNG(newLocationLNG);
									 * appState.setLocationsLAT(newLocationLAT);
									 * appState.setTime(newTime);
									 * appState.setEventType(newType);
									 * appState.setEventOriginator(newOrig);
									 */
									datasource.open();
									datasource.deleteEvent(theEvent);
									// end of deleting entry
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
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);
		check.setFocusable(false);
		timePicker.setFocusable(false);
		
		editTextTitle.setEnabled(false);
		editTextLocation.setOnClickListener(onEditTextLocationClick);
		// editTextLocation.setEnabled(false);
		editTextParticipants.setEnabled(true);
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
			intent.putExtra("participants", participantsString);
			startActivity(intent);

		}
	};

}
