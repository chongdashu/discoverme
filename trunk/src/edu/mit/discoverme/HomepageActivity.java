package edu.mit.discoverme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class HomepageActivity extends MapActivity {

	private String popup;
	private TextView p;
	private ImageButton b;
	private int poped;

	private ImageButton friend;
	private ImageButton event;
	private ImageButton notif;

	private ListView lv;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_list);

		friend = (ImageButton) (findViewById(R.id.friendButton));
		friend.setOnClickListener(onFriendClick);

		event = (ImageButton) (findViewById(R.id.eventButton));
		event.setOnClickListener(onEventClick);

		notif = (ImageButton) (findViewById(R.id.notificationButton));
		notif.setOnClickListener(onNotificationClick);

		b = (ImageButton) findViewById(R.id.buttonAdd);
		b.setOnClickListener(onAddClick);

		p = (TextView) findViewById(R.id.popupName);
		popup = "none";
		poped = 0;
		hideEverything();

		//

		// friend.setSelected(true);
		// event.setSelected(true);
		// notif.setSelected(true);

		lv = (ListView) findViewById(R.id.home_activity_list);
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
					hideEverything();
					startActivity(intent);

					// finishActivity(-1);
				} else if (popup.equals("eventss")) {
					// this will actually go to view event page
					
					// TODO get the event from State Manager
					StateManager stateManager = (StateManager) getApplicationContext();
					Intent intent = new Intent(HomepageActivity.this,
							ViewEventActivity.class);
					intent.putExtra("eventId", position);
					hideEverything();
					startActivity(intent);

				} else if (popup.equals("notifss")) {
					// this will actually go to view event or view profile page
					// depending on type
					Intent intent = new Intent(HomepageActivity.this,
							CreateEventActivity.class);
					intent.putExtra("eventName", ((TextView) view).getText());
					intent.putExtra("type", ((TextView) view).getText());
					hideEverything();
					startActivity(intent);

				}

			}
		});

		initializeMap();

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
						SearchActivity.class);// NotificationsActivity.class);
				intent.putExtra("popupCode", popup);
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
		friend.setSelected(false);
		event.setSelected(false);
		notif.setSelected(false);
	}

	private final void showEverything() {

		poped = 1;
		RelativeLayout popupArea = (RelativeLayout) (findViewById(R.id.popup_area));
		popupArea.setVisibility(View.VISIBLE);
		fillPopup();
		// setLayoutAnim_slidedown(popupArea, this);

	}

	private final void fillPopup() {
		StateManager appState = ((StateManager) getApplicationContext());
		String[] friends = appState.getFriends();
		String[] events = appState.getEvents();

		LinearLayout popupPage = (LinearLayout) findViewById(R.id.popup_layout_page);
		Drawable newMarker = HomepageActivity.this.getResources().getDrawable(
				R.drawable.photo);// _friend_popup);
		popupPage.setBackgroundDrawable(newMarker);

		if (popup.equals("friendss")) {
			// String[] friends = getResources().getStringArray(
			// R.array.friends_array);
			//
			lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, friends));
			b.setVisibility(View.VISIBLE);

			p.setText("Friends");

			friend.setSelected(true);
			event.setSelected(false);
			notif.setSelected(false);

		} else if (popup.equals("eventss")) {

			// String[] events = getResources().getStringArray(
			// R.array.events_array);
			lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					events));
			b.setVisibility(View.VISIBLE);
			p.setText("Events");

			friend.setSelected(false);
			event.setSelected(true);
			notif.setSelected(false);
		} else if (popup.equals("notifss")) {

			String[] notifs = getResources().getStringArray(
					R.array.notifs_array);
			lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					notifs));
			b.setVisibility(View.GONE);
			p.setText("Notifications");
			friend.setSelected(false);
			event.setSelected(false);
			notif.setSelected(true);

		}

	}

	private void initializeMap() {

		// Get the map view
		MapView mapView = (MapView) (findViewById(R.id.mapview));

		// Get the map controller
		MapController mapController = mapView.getController();

		// Get to current location
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);

		LocationManager locationManager;
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		String bestProvider = locationManager.getBestProvider(criteria, true);

		Location locLast = locationManager.getLastKnownLocation(bestProvider);
		GeoPoint test;

		float lat = 42.360383f;
		float lng = -71.090899f;
		if (locLast != null) {
			lat = (float) locLast.getLatitude();
			lng = (float) locLast.getLongitude();

			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		} else {
			lat = 42.360383f;
			lng = -71.090899f;

			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		}

		// Pan to user's current location
		mapController.setZoom(18);
		mapController.animateTo(test);

		List<Overlay> mapOverlays = mapView.getOverlays();

		// Create overlay for User
		HomepageMapOverlay itemizedoverlay = new HomepageMapOverlay(this,
				mapView);
		mapOverlays.add(itemizedoverlay);

		OverlayItem overlayitem = new OverlayItem(test, "Chong-U Lim",
				getAddressAt(test));
		itemizedoverlay.addOverlay(overlayitem);

		// Create overlay for Friends
		HomepageMapOverlay friendsOverlay = new HomepageMapOverlay(
				getResources().getDrawable(R.drawable.marker2), this, mapView);
		mapOverlays.add(friendsOverlay);

		String[] friends = getResources().getStringArray(R.array.friends_array);
		Vector<GeoPoint> friendpoints = getRandomGeopointsAround(lat, lng, 5);
		int f = 0;
		for (GeoPoint geoPoint : friendpoints) {
			OverlayItem item = new OverlayItem(geoPoint, friends[f],
					getAddressAt(geoPoint));
			friendsOverlay.addOverlay(item);
			f++;
		}

	}

	private Vector<GeoPoint> getRandomGeopointsAround(float lat, float lon,
			int n) {
		Vector<GeoPoint> geopoints = new Vector<GeoPoint>();

		for (int i = 0; i < n; i++) {
			float newlat;
			float newlon;
			if (i % 2 == 0) {
				newlat = lat * 1000000 + 1000 * (float) (Math.random());
				newlon = lon * 1000000 + 1000 * (float) (Math.random());
			} else {
				newlat = lat * 1000000 + 10000 * (float) (Math.random());
				newlon = lon * 1000000 - 10000 * (float) (Math.random());
			}

			GeoPoint newpoint = new GeoPoint((int) (newlat), (int) (newlon));
			geopoints.add(newpoint);
		}

		return geopoints;
	}

	private String getAddressAt(GeoPoint p) {
		String add = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					p.getLatitudeE6() / 1E6, p.getLongitudeE6() / 1E6, 1);

			add = "";
			if (addresses.size() > 0) {
				for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
					add += addresses.get(0).getAddressLine(i) + "\n";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return add;

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}