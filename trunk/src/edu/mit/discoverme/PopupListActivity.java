package edu.mit.discoverme;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PopupListActivity extends ListActivity {
	
	String popup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_list);

		ImageButton friend = (ImageButton) (findViewById(R.id.friendButton));
		friend.setOnClickListener(onFriendClick);

		ImageButton event = (ImageButton) (findViewById(R.id.eventButton));
		event.setOnClickListener(onEventClick);

		ImageButton notif = (ImageButton) (findViewById(R.id.notificationButton));
		notif.setOnClickListener(onNotificationClick);
		
		LinearLayout popupPage = (LinearLayout) findViewById(R.id.popup_layout_page);

		Intent intent = getIntent();
		popup = intent.getStringExtra("popupCode");// savedInstanceState.getInt("popupCode");

		Button b = (Button) findViewById(R.id.buttonAdd);
		TextView p = (TextView) findViewById(R.id.popupName);

		b.setOnClickListener(onAddClick);

		if (popup.equals("friendss")) {
			// Drawable newMarker = PopupListActivity.this.getResources()
			// .getDrawable(R.drawable.photo);// _friend_popup);
			// popupPage.setBackgroundDrawable(newMarker);

			p.setText("Friends");

			String[] friends = getResources().getStringArray(
					R.array.friends_array);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					friends));
			b.setVisibility(View.VISIBLE);


		} else if (popup.equals("eventss")) {
			// Drawable newMarker = PopupListActivity.this.getResources()
			// .getDrawable(R.drawable.photo);// _event_popup);
			// popupPage.setBackgroundDrawable(newMarker);

			String[] events = getResources().getStringArray(
					R.array.events_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.VISIBLE);
			p.setText("Events");
		} else if (popup.equals("notifss")) {
			// Drawable newMarker = PopupListActivity.this.getResources()
			// .getDrawable(R.drawable.photo);// _notif_popup);
			// popupPage.setBackgroundDrawable(newMarker);

			String[] events = getResources().getStringArray(
					R.array.notifs_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.GONE);
			p.setText("Notifications");

		}

		ListView lv = getListView();
		lv.setBackgroundColor(Color.WHITE);
		lv.setCacheColorHint(Color.WHITE);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			// start an intent here to move to "view friend activity"
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				// Toast.makeText(getApplicationContext(),
				// ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

				if (popup.equals("friendss")) {
				Intent intent = new Intent(PopupListActivity.this,
						ProfileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("personName", ((TextView) view).getText());
				startActivity(intent);

				finishActivity(-1);
				} else if (popup.equals("eventss")) {
					// this will actually go to viiew event page
					Intent intent = new Intent(PopupListActivity.this,
							EventsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					intent.putExtra("eventName", ((TextView) view).getText());
					startActivity(intent);

					finishActivity(-1);
				}

			}
		});
	}

	private final OnClickListener onFriendClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked

			if (popup.equals("friendss")) {
				finishActivity(-1);
			} else {
				Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);

			intent.putExtra("popupCode", "friendss");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent);

				finishActivity(-1);
			}

		}
	};

	private final OnClickListener onEventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (popup.equals("eventss")) {
				finishActivity(-1);
			} else {

				Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);// EventsActivity.class);
			intent.putExtra("popupCode", "eventss");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(intent);
				finishActivity(-1);
			}
		}
	};

	private final OnClickListener onNotificationClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (popup.equals("notifss")) {
				finishActivity(-1);
			} else {
			Intent intent = new Intent(PopupListActivity.this,
					PopupListActivity.class);// NotificationsActivity.class);
			intent.putExtra("popupCode", "notifss");
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
				finishActivity(-1);
			}
		}
	};

	private final OnClickListener onAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (popup.equals("friendss")) {
				// it should go to search page
				Intent intent = new Intent(PopupListActivity.this,
						CreateEventActivity.class);// NotificationsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finishActivity(-1);
			}
			if (popup.equals("eventss")) {
				Intent intent = new Intent(PopupListActivity.this,
						CreateEventActivity.class);// NotificationsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finishActivity(-1);
			}

		}
	};

}
