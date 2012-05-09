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
import android.os.Handler;
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

	public static final boolean NO_LOCATION_SEARCH = true;
	
	private String popup;
	private TextView p;
	private ImageButton b;
	private int poped;

	private ImageButton friend;
	private ImageButton event;
	private ImageButton notif;

	private ListView lv;
	private MyDataSource datasource;
	
	private Handler backgroundNotificationsHandler;


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

		datasource = new MyDataSource(this);
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
							FriendProfileActivity.class);
					Friend onefriend = (Friend) lv.getItemAtPosition(position);

					intent.putExtra("friendID", onefriend.getId());
					hideEverything();
					startActivity(intent);
				} else if (popup.equals("eventss")) {
					// this will actually go to view event page

					Intent intent = new Intent(HomepageActivity.this,
							ViewEventActivity.class);
					Event oneEvent = (Event) lv.getItemAtPosition(position);

					intent.putExtra("eventID", oneEvent.getId());
					hideEverything();
					startActivity(intent);
				} else if (popup.equals("notifss")) {
					Notif oneNotif = (Notif) lv.getItemAtPosition(position);
					String type = oneNotif.getType();

					if (type.equals("event")) {

					Intent intent = new Intent(HomepageActivity.this,
								ProposeEventChangeActivity.class);
						intent.putExtra("notifID", oneNotif.getId());
						intent.putExtra("eventTitle", "Quick snack!");
						String[] participants = { "annie", "henna" };
						intent.putExtra("participants", participants);
						intent.putExtra("timeHrs", 5);
						intent.putExtra("timeMins", 30);
						intent.putExtra("locationName", "MIT");
						hideEverything();
						startActivity(intent);

					} else {
						Intent intent = new Intent(HomepageActivity.this,
								StrangerProfileActivity.class);
						intent.putExtra("notifID", oneNotif.getId());
						hideEverything();
						startActivity(intent);

					}

				}
			}
		});

		initializeMap();
		
		// Background thread that runs to check for notifications
		backgroundNotificationsHandler = new Handler();
		backgroundNotificationsHandler.removeCallbacks(checkNotificationsTask);
		backgroundNotificationsHandler.postDelayed(checkNotificationsTask, 5000);
	}
	
	private final Runnable checkNotificationsTask = new Runnable() {
		   @Override
		public void run() {
		       long nextUpdateTime = System.currentTimeMillis() + 1000;
		       System.out.println("checkNotificationsTask update");

		       backgroundNotificationsHandler.postDelayed(this, 5000);
		   }
		};
	
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
				hideEverything();
				startActivity(intent);
			}
			if (popup.equals("eventss")) {
				Intent intent = new Intent(HomepageActivity.this,
						CreateEventActivity.class);// NotificationsActivity.class);
				hideEverything();
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
			datasource.open();
			List<Friend> values = datasource.getAllFriends();
			datasource.close();
			ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(this,
					R.layout.list_item, values);
			lv.setAdapter(adapter);
			b.setVisibility(View.VISIBLE);

			p.setText("Friends");

			friend.setSelected(true);
			event.setSelected(false);
			notif.setSelected(false);

		} else if (popup.equals("eventss")) {

			datasource.open();
			List<Event> values = datasource.getAllEvents();
			datasource.close();
			ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
					R.layout.list_item, values);

			lv.setAdapter(adapter);
			b.setVisibility(View.VISIBLE);
			p.setText("Events");

			friend.setSelected(false);
			event.setSelected(true);
			notif.setSelected(false);
		} else if (popup.equals("notifss")) {

			datasource.open();
			List<Notif> values = datasource.getAllNotifs();
			datasource.close();
			ArrayAdapter<Notif> adapter = new ArrayAdapter<Notif>(this,
					R.layout.list_item, values);

			lv.setAdapter(adapter);

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
		StateManager stateManager = (StateManager) getApplicationContext();
		
		// GeoPoint test;

		float lat = 42.360383f;
		float lng = -71.090899f;
		if (locLast != null) {
			lat = (float) locLast.getLatitude();
			lng = (float) locLast.getLongitude();

			stateManager.userGeoPoint = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		} else {
			lat = 42.360383f;
			lng = -71.090899f;

			stateManager.userGeoPoint = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		}
		
		stateManager.userLat = lat;
		stateManager.userLon = lng;

		// Pan to user's current location
		mapController.setZoom(18);
		mapController.animateTo(stateManager.userGeoPoint);

		List<Overlay> mapOverlays = mapView.getOverlays();

		// Create overlay for User
		HomepageMapOverlay itemizedoverlay = new HomepageMapOverlay(this,
				mapView);
		mapOverlays.add(itemizedoverlay);
		
		stateManager.userAddress = getAddressAt(stateManager.userGeoPoint);
		OverlayItem overlayitem = new OverlayItem(stateManager.userGeoPoint, stateManager.userName,
				stateManager.userAddress);
		itemizedoverlay.addOverlay(overlayitem);

		// Create overlay for Friends
		HomepageMapOverlay friendsOverlay = new HomepageMapOverlay(
				getResources().getDrawable(R.drawable.marker2), this, mapView);
		mapOverlays.add(friendsOverlay);

		String[] friends = getResources().getStringArray(R.array.friends_array);
		stateManager.friendPoints = getRandomGeopointsAround(lat, lng, 5);
		stateManager.friendAddresses = new Vector<String>();
		int f = 0;
		for (GeoPoint geoPoint : stateManager.friendPoints) {
			
			String address = getAddressAt(geoPoint);
			stateManager.friendAddresses.add(address);
			
			OverlayItem item = new OverlayItem(geoPoint, friends[f], address);
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
		if (NO_LOCATION_SEARCH)
		{
			return "Fake Location...";
		}
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
	
	@Override
	public void onBackPressed(){
		if(poped != 0){
			hideEverything();
		}
		else{
			super.onBackPressed();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
