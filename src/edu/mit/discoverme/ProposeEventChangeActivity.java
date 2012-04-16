package edu.mit.discoverme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

public class ProposeEventChangeActivity extends CreateEventActivity {

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

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
	
	protected boolean inEditMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		
		// Begin changes here
		eventTitle = intent.getStringExtra("eventTitle");//events[eventID];
		// participantsString = parts[eventID];
		participants = intent.getStringArrayExtra("participants"); //participantsString.split(",");
		participantsString = Utils.foldParticipantsList(participants);
		//if (types[eventID] == stO)
			//closedEvent = false;
		//else
			closedEvent = true;
		//if(originators[eventID].equals(stM))
		//originatorIsme = true;
		//else 
			originatorIsme= false;
		//String time = times[eventID];
		//String[] arg = time.split(" ");
		timeHrs = intent.getIntExtra("timeHrs", 0);
		timeMins = intent.getIntExtra("timeMins", 0);

		locationName = intent.getStringExtra("locationName"); //locations[eventID];
		latE6 = intent.getIntExtra("lat", (int)(42.360383*1000000)); //(int) (((float) Float.valueOf(locations_lat[eventID]))*1000000);
		lngE6 = intent.getIntExtra("lng", (int)(-71.090899*1000000)); //(int) (((float) Float.valueOf(locations_lng[eventID]))*1000000);
		
		inititialize();

	}

	protected void inititialize() {
		
		next.setVisibility(View.VISIBLE);
		next.setText("RSVP");
		next.setOnClickListener(onRSVPClick);
		
		back.setOnClickListener(onCancelClick);
		
		editTextTitle.setFocusable(false);
		
		editTextParticipants.setOnClickListener(onEditTextParticipantsClick);
		
		check.setFocusable(false);
		
		timePicker.setFocusable(false);
		
		editTextTitle.setEnabled(false);
		
		editTextLocation.setOnClickListener(onEditTextLocationClick);
		
		editTextParticipants.setEnabled(true);
		
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
		
		proposeChangeArea.setVisibility(View.VISIBLE);
		
		proposeChange.setText("Propose Change");
		
		proposeChange.setOnClickListener(onProposeChangeClick);
		//if (originatorIsme)
			proposeChange.setVisibility(View.VISIBLE);
		//else
			//proposeChange.setVisibility(View.INVISIBLE);
		
		proposeChange.setText("Propose changes to Event");
		
		initMode();
	}
	
	protected OnClickListener onProposeChangeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			toggleMode();
			initMode();
			
		}
	}; 
	
	private void toggleMode()
	{
		inEditMode = !inEditMode;
	}
	
	private void initMode()
	{
		if (inEditMode) {
			next.setText("Propose Change");
			back.setText("Cancel");
			proposeChangeArea.setVisibility(View.GONE);
		}
		else {
			next.setText("RSVP");
			back.setText("Back");
			proposeChangeArea.setVisibility(View.VISIBLE);
		}
	}
	
	private final OnClickListener onRSVPClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (inEditMode) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ProposeEventChangeActivity.this);
		    	builder.setMessage("Do you wish to propose this change back to the originator?")
		    	       .setCancelable(false)
		    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	           @Override
						public void onClick(DialogInterface dialog, int id) {
		    	               // Do whatever you want for 'Yes' here. 
		    	        	   dialog.dismiss();
		    	        	   Toast.makeText(getApplicationContext(),
		    	   					getString(R.string.proposedChangeEventMsg), Toast.LENGTH_SHORT)
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
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(ProposeEventChangeActivity.this);
		    	builder.setMessage("Do you wish to accept or decline this invitation?")
		    	       .setCancelable(false)
		    	       .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
		    	           @Override
						public void onClick(DialogInterface dialog, int id) {
		    	               // Do whatever you want for 'Yes' here. 
		    	        	   dialog.dismiss();
		    	        	   Toast.makeText(getApplicationContext(),
		    	   					getString(R.string.RSVPEventMsgAccept), Toast.LENGTH_SHORT)
		    	   					.show();
		    	   				finish();
		    	           }
		    	       })
		    	       .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
		    	           @Override
						public void onClick(DialogInterface dialog, int id) {
		    	        	   // Do whatever you want for 'No' here.  
		    	        	   dialog.dismiss();
		    	        	   Toast.makeText(getApplicationContext(),
		    	   					getString(R.string.RSVPEventMsgDecline), Toast.LENGTH_SHORT)
		    	   					.show();
		    	   				finish();
		    	           }
		    	       });
		    	
		    	AlertDialog alert = builder.create();
		    	alert.show();
			}
			

		}
	};
	
	private final OnClickListener onCancelClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (inEditMode) {
				toggleMode();
				initMode();
			}
			else {
				
				finish();
			}
			

		}
	};
	
	private final OnClickListener onEditTextParticipantsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (inEditMode) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.closedEventNoEdit), Toast.LENGTH_LONG)
						.show();
			}
			else {
				Intent intent = new Intent(ProposeEventChangeActivity.this,
						ViewParticipantsListActivity.class);
				intent.putExtra("participants", participantsString);
				startActivity(intent);
			}
			

		}
	};

	private final OnClickListener onEditTextLocationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// We launch the "Select Location from Map Activity"
			Intent intent = new Intent(ProposeEventChangeActivity.this, SelectEventLocationActivity.class);
			intent.putExtra("lat", latE6);
			intent.putExtra("lng", lngE6);
			
			if (inEditMode) {
				intent.putExtra("mode", SelectEventLocationActivity.MODE_PROPOSE);
			}
			else {
				intent.putExtra("mode", SelectEventLocationActivity.MODE_VIEW);
			}
			
			startActivity(intent);
			

		}
	};

}
