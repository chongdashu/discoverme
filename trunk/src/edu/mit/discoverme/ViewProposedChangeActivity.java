package edu.mit.discoverme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewProposedChangeActivity extends ProposeEventChangeActivity {
	private Event theEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
//		back.setText("Reject");
		next.setText("Respond");
		next.setVisibility(View.VISIBLE);
		next.setOnClickListener(onRespondClick);
		theEvent = new Event();
		theEvent.setId(0);
		theEvent.setEvent(eventuid, eventTitle, participantsString, rsvpString,
				timeString, locationName, String.valueOf(latE6),
				String.valueOf(lngE6), eventType);
	}
	
	
	protected OnClickListener onRespondClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewProposedChangeActivity.this);
			builder.setMessage("Do you want to accept these changes?")
			.setCancelable(true)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

									SharedPreferences prefs = getSharedPreferences(
											"credentials",
											Context.MODE_WORLD_READABLE);
									String username = prefs.getString(
											"username", "none");

									Event updatedEvent = theEvent;

									String newParticipants = editTextParticipants
											.getText().toString();
									updatedEvent
											.setParticipants(newParticipants);

									String[] arg = newParticipants.split(",");
									String newRsvp = "yes,";
									for (int i = 0; i < arg.length - 1; i++)
										newRsvp = newRsvp + "pending,";
									String newLocation = (editTextLocation
											.getText()).toString();
									String newLocationLNG = locationLng;
									String newLocationLAT = locationLat;
									updatedEvent.setLocation(newLocation);
									updatedEvent.setLocationLat(newLocationLAT);
									updatedEvent.setLocationLng(newLocationLNG);
									String newTime = (String.valueOf(timePicker
											.getCurrentHour()))
											+ " "
											+ (String.valueOf(timePicker
													.getCurrentMinute()));
									updatedEvent.setTime(newTime);

									datasource.open();

									ServerLink.acceptProposedChange(username,
											theEvent, datasource);// TODO

									datasource.close();
									dialog.dismiss();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
			
		}
	};

}
