package edu.mit.discoverme;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomepageActivity extends ListActivity {

	String popup;
	TextView p;
	Button b;
	int poped;

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

		b = (Button) findViewById(R.id.buttonAdd);
		b.setOnClickListener(onAddClick);

		p = (TextView) findViewById(R.id.popupName);
		popup = "none";
		poped = 0;
		hideEverything();



	}

	private final OnClickListener onFriendClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked

			System.out.println("friend clicked");
			System.out.println(" while the pop is " + popup);
			if (poped == 1 && popup.equals("friendss")) {
				hideEverything();
			} else {
				popup = "friendss";
				showEverything();
			}

		}
	};

	private final OnClickListener onEventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("event clicked");
			System.out.println(" while the pop is " + popup);
			if (poped == 1 && popup.equals("eventss")) {
				hideEverything();
			} else {

				popup = "eventss";
				showEverything();

			}
		}
	};

	private final OnClickListener onNotificationClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("notif clicked");
			System.out.println(" while the pop is " + popup);
			if (poped == 1 && popup.equals("notifss")) {
				hideEverything();

			} else {
				// make everything visible here
				popup = "notifss";
				showEverything();

			}
		}
	};

	private final OnClickListener onAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (popup.equals("friendss")) {
				// it should go to search page
				Intent intent = new Intent(HomepageActivity.this,
						CreateEventActivity.class);// NotificationsActivity.class);
				startActivity(intent);
			}
			if (popup.equals("eventss")) {
				Intent intent = new Intent(HomepageActivity.this,
						CreateEventActivity.class);// NotificationsActivity.class);
				startActivity(intent);
			}

		}
	};

	private final void hideEverything() {

		poped = 0;
		RelativeLayout popupArea = (RelativeLayout) (findViewById(R.id.popup_area));
		popupArea.setVisibility(View.GONE);
		LinearLayout popupPage = (LinearLayout) findViewById(R.id.popup_layout_page);
		popupPage.setBackgroundDrawable(null);
	}

	private final void showEverything() {

		poped = 1;
		RelativeLayout popupArea = (RelativeLayout) (findViewById(R.id.popup_area));
		popupArea.setVisibility(View.VISIBLE);
		fillPopup();

	}

	private final void fillPopup() {

		LinearLayout popupPage = (LinearLayout) findViewById(R.id.popup_layout_page);
		Drawable newMarker = HomepageActivity.this.getResources().getDrawable(
				R.drawable.photo);// _friend_popup);
		popupPage.setBackgroundDrawable(newMarker);
		if (popup.equals("friendss")) {
			String[] friends = getResources().getStringArray(
					R.array.friends_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					friends));
			b.setVisibility(View.VISIBLE);
			p.setText("Friends");
		} else if (popup.equals("eventss")) {

			String[] events = getResources().getStringArray(
					R.array.events_array);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.VISIBLE);
			p.setText("Events");
		} else if (popup.equals("notifss")) {

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
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (popup.equals("friendss")) {
					Intent intent = new Intent(HomepageActivity.this,
							ProfileActivity.class);
					intent.putExtra("personName", ((TextView) view).getText());
					startActivity(intent);

					// finishActivity(-1);
				} else if (popup.equals("eventss")) {
					// this will actually go to view event page
					Intent intent = new Intent(HomepageActivity.this,
							EventsActivity.class);
					intent.putExtra("eventName", ((TextView) view).getText());
					startActivity(intent);

				}

			}
		});

	}

}
