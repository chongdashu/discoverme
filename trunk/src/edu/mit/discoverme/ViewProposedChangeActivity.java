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
		proposeChange.setVisibility(View.GONE);
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

									String[] arg = newParticipants.trim().split(",");
									String newRsvp = "yes,";
									for (int i = 0; i < arg.length - 1; i++)
										newRsvp = newRsvp + "pending,";
									String newLocation = (editTextLocation
											.getText()).toString();

									float lat = latE6;
									float lng = lngE6;
									lat = lat / 1000000;
									lng = lng / 1000000;

									String newLocationLNG = String.valueOf(lat);
									String newLocationLAT = String.valueOf(lng);
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
									StateManager stm = (StateManager) getApplicationContext();
									String firstname = stm.fullName;
									ServerLink
											.acceptProposedChange(username,
													firstname, updatedEvent,
													datasource);// TODO

									Notif notif = datasource.getNotif(notifID);
									datasource.deleteNotif(notif);


									datasource.close();
									dialog.dismiss();
									finish();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					SharedPreferences prefs = getSharedPreferences(
							"credentials",
							Context.MODE_WORLD_READABLE);
					String username = prefs.getString(
							"username", "none");
					StateManager stm = (StateManager) getApplicationContext();
					String firstname = stm.fullName;


									datasource.open();
									Notif notif = datasource.getNotif(notifID);
									String details = notif.getDetail();
									String[] arg = details.split(",");
									String friend = "";
									String eventname = "";
									if (arg.length == 2)
 {
										friend = arg[0];
										eventname = arg[1];
										eventname = eventname.trim().split(
												"_update")[0];
									}
									ServerLink.notifyChangeRejection(friend,
											username, theEvent.getName(),
											firstname, eventname);
									datasource.deleteNotif(notif);
									datasource.close();
									dialog.cancel();
									finish();
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
			
		}
	};

}
