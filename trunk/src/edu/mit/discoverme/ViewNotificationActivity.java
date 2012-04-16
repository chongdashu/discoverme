package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ViewNotificationActivity extends Activity {
	
	private int notifID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Intent intent = getIntent();
		notifID = intent.getIntExtra("notifID",0);
		
		StateManager appState = ((StateManager) getApplicationContext());
		int[] notifsType = appState.getNotifType();
		String[] notifsName = appState.getNotifsNames();
		int notificationType = notifsType[notifID];
		String notificationName = notifsName[notifID];

		if (notificationType == 1) {
		Intent i1 = new Intent(ViewNotificationActivity.this,
				ProfileActivity.class);
		i1.putExtra("personName", notificationName);
		i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i1);
			finish();

		} else if (notificationType == 2) {
		// Intent i1 = new Intent(ViewNotificationActivity.this,
		// ViewEventActivity.class);
		// i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// i1.putExtra("eventId", position);
		// startActivity(intent);

		}

	}


}
