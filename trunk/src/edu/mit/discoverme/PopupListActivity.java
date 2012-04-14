package edu.mit.discoverme;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PopupListActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_list);

		ImageButton friend = (ImageButton) (findViewById(R.id.friendButton));
		// button.setText(R.string.button_text_friend);
		friend.setOnClickListener(onFriendClick);

		ImageButton event = (ImageButton) (findViewById(R.id.eventButton));
		event.setOnClickListener(onEventClick);

		ImageButton notif = (ImageButton) (findViewById(R.id.notificationButton));
		notif.setOnClickListener(onNotificationClick);

		Intent intent = getIntent();
		String popup = intent.getStringExtra("popupCode");// savedInstanceState.getInt("popupCode");

		Button b = (Button) findViewById(R.id.buttonAdd);
		if (popup.equals("friendss")) {
			String[] friends = getResources().getStringArray(
					R.array.friends_array);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					friends));
			b.setVisibility(View.VISIBLE);

		} else if (popup.equals("eventss")) {
			String[] events = getResources().getStringArray(
					R.array.events_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.VISIBLE);
		} else if (popup.equals("notifss")) {
			String[] events = getResources().getStringArray(
					R.array.notifs_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.GONE);


		}

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			// start an intent here to move to "view friend activity"
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private final OnClickListener onFriendClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked

			Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);
			intent.putExtra("popupCode", "friendss");
			startActivity(intent);

		}
	};

	private final OnClickListener onEventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);// EventsActivity.class);
			intent.putExtra("popupCode", "eventss");
			startActivity(intent);
		}
	};

	private final OnClickListener onNotificationClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);// NotificationsActivity.class);
			intent.putExtra("popupCode", "notifss");
			startActivity(intent);
		}
	};

}
