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

public class ProposeEventChangeActivity extends CreateEventActivity {

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected String eventuid;
	protected String eventTitle;
	protected String[] participants;
	protected String participantsString;
	protected String[] rsvp;
	protected String rsvpString;
	protected boolean closedEvent;
	protected String eventType;
	protected String timeString;
	protected int timeHrs;
	protected int timeMins;
	protected String locationLatString;
	protected String locationLngString;
	protected GeoPoint location;
	protected String locationName;
	protected int latE6;
	protected int lngE6;
	protected boolean originatorIsme;
	protected boolean inEditMode;
	protected MyDataSource datasource;
	long notifID;

	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		username = prefs.getString("username", "none");

		Intent intent = getIntent();
		notifID = intent.getLongExtra("notifID", 0);

		datasource = new MyDataSource(this);
		datasource.open();
		Notif notif = datasource.getNotif(notifID);
		datasource.close();

		String details = notif.getDetail();
		String[] detailArr = details.split(",");
		if (detailArr.length == 2)
			eventuid = detailArr[1];
		else eventuid = details;
		// eventuid = details;// "saqib01";
		String eventRow = ServerLink.getEvent(eventuid);

		
		// Begin changes here
		String [] arg = eventRow.split(";");
		eventTitle = arg[0];
		participantsString = arg[1];
		participants = participantsString.trim().split(",");
		rsvpString = arg[2];
		rsvp = rsvpString.split(",");
		timeString = arg[3];
		eventType = arg[7];
		if (arg[7].trim().equals("closed"))
			closedEvent = true;
		else
			closedEvent = false;
		originatorIsme = false;
		timeHrs = Integer.valueOf((timeString.split(" "))[0]);
		timeMins = Integer.valueOf((timeString.split(" "))[1]);
		locationName = arg[4];
		latE6 = (int) (Float.valueOf(arg[5]) * 1000000);
																		// (((float)
																		// Float.valueOf(locations_lat[eventID]))*1000000);
		lngE6 = (int) (Float.valueOf(arg[6]) * 1000000);

																			// (((float)
																			// Float.valueOf(locations_lng[eventID]))*1000000);

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
		} else {
			check.setChecked(false);
			check.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		}

		editTextLocation.setText(locationName);

		timePicker.setCurrentHour(timeHrs);
		timePicker.setCurrentMinute(timeMins);

		proposeChangeArea.setVisibility(View.VISIBLE);

		proposeChange.setText("Propose Change");

		proposeChange.setOnClickListener(onProposeChangeClick);
		// if (originatorIsme)
		proposeChange.setVisibility(View.VISIBLE);
		// else
		// proposeChange.setVisibility(View.INVISIBLE);

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

	private void toggleMode() {
		inEditMode = !inEditMode;
	}

	private void initMode() {
		if (inEditMode) {
			next.setText("Propose Change");
			back.setText("Cancel");
			proposeChangeArea.setVisibility(View.GONE);
			activityTitle.setText(R.string.activityTitleEventPropose);
			food.setEnabled(true);
			silence.setEnabled(true);
			it.setEnabled(true);
//			timePicker.setFocusable(true);
			timePicker.setEnabled(true);
		} else {
			next.setText("RSVP");
			back.setText("Back");
			proposeChangeArea.setVisibility(View.VISIBLE);
			activityTitle.setText(R.string.activityTitleEventRSVP);
			food.setEnabled(false);
			silence.setEnabled(false);
			it.setEnabled(false);
//			timePicker.setFocusable(false);
			timePicker.setEnabled(false);
		}
	}

	private final OnClickListener onRSVPClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (inEditMode) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ProposeEventChangeActivity.this);
				builder.setMessage(
						"Do you wish to propose this change back to the originator?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// Do whatever you want for 'Yes' here.
										dialog.dismiss();
										SharedPreferences prefs = getSharedPreferences(
												"credentials",
												Context.MODE_WORLD_READABLE);
										String username = prefs.getString(
												"username", "none");
										StateManager stm = (StateManager) getApplicationContext();
										String firstname = stm.fullName;

										String eventUniqueID = eventuid;
										String newEventTitle = (editTextTitle
												.getText()).toString();
										String newParticipants = (editTextParticipants
												.getText())
														.toString();
										newParticipants.replace(" ", "");
										newParticipants.replace(",,", ",");
										String[] arg = newParticipants
												.split(",");
										// String newRsvp = "";
										String newRsvp = "";
										for (int i = 0; i < arg.length - 1; i++)
											newRsvp = newRsvp + "pending,";
										String newLocation = (editTextLocation
												.getText()).toString();
										float lat = latE6;
										float lng = lngE6;
										lat = lat / 1000000;
										lng = lng / 1000000;

										String newLocationLNG = String
												.valueOf(lng);
										String newLocationLAT = String
												.valueOf(lat);
										String newTime = (String
												.valueOf(timePicker
														.getCurrentHour()))
												+ " "
												+ (String.valueOf(timePicker
														.getCurrentMinute()));
										String newType = "";
										if (check.isChecked())
											newType = "closed";
										else
											newType = "open";
										Event theEvent = new Event();
										theEvent.setId(0);
										theEvent.setEvent(eventUniqueID,
												newEventTitle,
														newParticipants,
														newRsvp, newTime,
														newLocation,
														newLocationLAT,
														newLocationLNG, newType);
										datasource.open();


										ServerLink.proposeChanges(username,
												firstname, theEvent);// TODO

										Notif notif = datasource
												.getNotif(notifID);
										datasource.deleteNotif(notif);
										datasource.close();
										Toast.makeText(
												getApplicationContext(),
												getString(R.string.proposedChangeEventMsg),
												Toast.LENGTH_SHORT).show();
										finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// Do whatever you want for 'No' here.
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ProposeEventChangeActivity.this);
				builder.setMessage(
						"Do you wish to accept or decline this invitation?")
						.setCancelable(false)
						.setPositiveButton("Accept",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// Do whatever you want for 'Yes' here.
										dialog.dismiss();
										StateManager stm = (StateManager) getApplicationContext();
										String firstname = stm.fullName;
										datasource.open();
										ServerLink.sendRSVP(username,
												firstname, eventTitle,
												eventuid, "yes", datasource);
										String newrsvpString = ServerLink
												.changeOneRSVP(
														participantsString,
														rsvpString, username,
														"yes");

										datasource.createEvent(eventuid,
												eventTitle, participantsString,
												newrsvpString, timeString,
												locationName,
												String.valueOf(latE6),
												String.valueOf(latE6),
												eventType);
										Notif notif = datasource
												.getNotif(notifID);
										datasource.deleteNotif(notif);
										datasource.close();
										Toast.makeText(
												getApplicationContext(),
												getString(R.string.RSVPEventMsgAccept),
												Toast.LENGTH_SHORT).show();

										finish();
									}
								})
						.setNegativeButton("Decline",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// Do whatever you want for 'No' here.
										dialog.dismiss();
										StateManager stm = (StateManager) getApplicationContext();
										String firstname = stm.fullName;

										datasource.open();
										ServerLink.sendRSVP(username,
												firstname, eventTitle,
												eventuid, "no", datasource);// TODO


										ServerLink.deleteEventFromMyList(
												eventuid, datasource);
										Notif notif = datasource
												.getNotif(notifID);
										datasource.deleteNotif(notif);
										datasource.close();
										Toast.makeText(
												getApplicationContext(),
												getString(R.string.RSVPEventMsgDecline),
												Toast.LENGTH_SHORT).show();
										finish();
									}
								})
								
						.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						})
						;

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
			} else {

				finish();
			}

		}
	};

	private final OnClickListener onEditTextParticipantsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (inEditMode) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.closedEventNoEdit),
						Toast.LENGTH_LONG).show();
			} else {
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
			Intent intent = new Intent(ProposeEventChangeActivity.this,
					SelectEventLocationActivity.class);
			intent.putExtra("lat", latE6);
			intent.putExtra("lng", lngE6);
			intent.putExtra("participants", participants);
			intent.putExtra("responses", rsvp);

			if (inEditMode) {
				intent.putExtra("mode",
						SelectEventLocationActivity.MODE_PROPOSE);
			} else {
				intent.putExtra("mode", SelectEventLocationActivity.MODE_VIEW);
			}

			startActivity(intent);

		}
	};

}
