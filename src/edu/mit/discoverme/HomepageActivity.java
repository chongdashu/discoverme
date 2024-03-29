package edu.mit.discoverme;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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

	public static final boolean NO_LOCATION_SEARCH = false;

	private String popup;
	private TextView p;
	private ImageButton b;
	private int poped;

	private ImageButton friend;
	private ImageButton event;
	private ImageButton notif;

	private ListView lv;
	private MyDataSource datasource;
	private DirDataSource dirdatasource;

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
		dirdatasource = new DirDataSource(this);
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

					if (type.equals("FriendReq") || type.equals("FriendRes")) {
						Intent intent = new Intent(HomepageActivity.this,
								StrangerProfileActivity.class);
						intent.putExtra("email", oneNotif.getDetail());
						intent.putExtra("notifID", oneNotif.getId());
						hideEverything();
						startActivity(intent);

					} else if (type.equals("EventInvite")
							|| type.equals("EventChanged")) {

						Intent intent = new Intent(HomepageActivity.this,
								ProposeEventChangeActivity.class);
						intent.putExtra("notifID", oneNotif.getId());
						hideEverything();
						startActivity(intent);
					} else if (type.equals("EventAccepted")
							|| type.equals("EventDeclined")
							|| type.equals("EventCanceled")
							|| type.equals("FriendDel")
							|| type.equals("ProposedChangeRejected")) {
						// do nothing
					} else if (type.equals("EventProposedChange")) {
						Intent intent = new Intent(HomepageActivity.this,
								ViewProposedChangeActivity.class);
						intent.putExtra("notifID", oneNotif.getId());
						hideEverything();
						startActivity(intent);
						// go to this new page now which ius not there yet ...
						// which will read the modified fiel in view event
						// format
					}

					/*
					 * if (type.equals("event")) {
					 * 
					 * Intent intent = new Intent(HomepageActivity.this,
					 * ProposeEventChangeActivity.class);
					 * intent.putExtra("notifID", oneNotif.getId());
					 * intent.putExtra("eventTitle", "Quick snack!"); String[]
					 * participants = { "annie", "henna" };
					 * intent.putExtra("participants", participants);
					 * intent.putExtra("timeHrs", 5);
					 * intent.putExtra("timeMins", 30);
					 * intent.putExtra("locationName", "MIT"); hideEverything();
					 * startActivity(intent);
					 * 
					 * } else { Intent intent = new
					 * Intent(HomepageActivity.this,
					 * StrangerProfileActivity.class);
					 * intent.putExtra("notifID", oneNotif.getId());
					 * hideEverything(); startActivity(intent);
					 * 
					 * }
					 */

				}
			}
		});

		initializeUser();
		initializeMap();

		// Background thread that runs to check for notifications
		backgroundNotificationsHandler = new Handler();
		backgroundNotificationsHandler.removeCallbacks(checkNotificationsTask);
		backgroundNotificationsHandler
				.postDelayed(checkNotificationsTask, 5000);
	}

	private void initializeUser() {
		StateManager stateManager = (StateManager) getApplicationContext();
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		String username = prefs.getString("username", "none");

		stateManager.userName = username;
		dirdatasource.open();

		List<Friend> allPeople = dirdatasource.getAllPeople();
		for (Friend friend : allPeople) {
			if (friend.getMITId().equals(username)) {
				stateManager.fullName = friend.getName();
			}
		}
		
		boolean hasDoneTutorial = prefs.getBoolean("tutorial_done:" + username, false);
		if (!hasDoneTutorial)
		{
			Intent intent2 = new Intent(HomepageActivity.this,
					TutorialActivity.class);
			startActivity(intent2);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("tutorial_done:" + username, true);
			editor.commit();
		}

		dirdatasource.close();
	}

	private final Runnable checkNotificationsTask = new Runnable() {
		@Override
		public void run() {
			long nextUpdateTime = System.currentTimeMillis() + 1000;
			System.out.println("checkNotificationsTask update");
			StateManager stateManager = (StateManager) getApplicationContext();
			SharedPreferences prefs = getSharedPreferences("credentials",
					Context.MODE_WORLD_READABLE);
			String username = prefs.getString("username", "none");
			datasource.open();
			dirdatasource.open();
			ServerLink.loadNotifs(username, datasource, dirdatasource);
			updateNotificationsCount();
			
			Vector<GeoLocation> allFriendsLocation = ServerLink.getLocationForAllFriend(datasource);
			for (GeoLocation geoLocation : allFriendsLocation) {
				stateManager.addressMap.put(geoLocation.getUsername(), geoLocation.getAddressName());
				stateManager.geopointMap.put(geoLocation.getUsername(), geoLocation.getGeoPoint());
			}

			datasource.close();
			dirdatasource.close();
			String locationLat = String.valueOf(stateManager.userLat);
			String locationLng = String.valueOf(stateManager.userLon);
			String location = stateManager.userAddress;
			ServerLink.updateLocation(username, location, locationLat,
					locationLng);

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
				updateNotificationsCount();

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
			Collections.sort(values, new FriendComparator());
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
			Collections.sort(values, new EventComparator());
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
			Collections.sort(values, new NotifComparator());
			// ArrayAdapter<Notif> adapter = new ArrayAdapter<Notif>(this,
			// R.layout.list_item, values);
			NotifAdapter adapter = new NotifAdapter(this, R.layout.list_item_notif,
					values);
			lv.setAdapter(adapter);
			b.setVisibility(View.GONE);
			p.setText("Notifications");
			friend.setSelected(false);
			event.setSelected(false);
			notif.setSelected(true);
			datasource.readAllNotif();
			datasource.close();
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

			stateManager.userGeoPoint = new GeoPoint((int) (lat * 1000000),
					(int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		} else {
			lat = 42.360383f;
			lng = -71.090899f;

			stateManager.userGeoPoint = new GeoPoint((int) (lat * 1000000),
					(int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		}

		stateManager.userLat = lat;
		stateManager.userLon = lng;

		// Pan to user's current location
		mapController.setZoom(18);
		mapController.animateTo(stateManager.userGeoPoint);

		// Add button listener
		ImageButton userLocation = (ImageButton) findViewById(R.id.btn_my_location);
		userLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StateManager stateManager = (StateManager) getApplicationContext();
				HomepageActivity homePageActivity = HomepageActivity.this;

				MapView mapView = (MapView) (homePageActivity
						.findViewById(R.id.mapview));
				MapController mapController = mapView.getController();

				mapController.animateTo(stateManager.userGeoPoint);

			}
		});

		List<Overlay> mapOverlays = mapView.getOverlays();

		// Create overlay for User
		HomepageMapOverlay itemizedoverlay = new HomepageMapOverlay(this,
				mapView);
		mapOverlays.add(itemizedoverlay);

		stateManager.userAddress = Utils.getAddressAt(getBaseContext(), stateManager.userGeoPoint);
		System.out.println("stateManager.userAddress=" + stateManager.userAddress);
		OverlayItem overlayitem = new OverlayItem(stateManager.userGeoPoint,
				stateManager.fullName, stateManager.userAddress);
		itemizedoverlay.addOverlay(overlayitem);

		// Create overlay for Friends
		HomepageMapOverlay friendsOverlay = new HomepageMapOverlay(
				getResources().getDrawable(R.drawable.marker2), this, mapView);
		mapOverlays.add(friendsOverlay);

		datasource.open();
		List<Friend> allFriends = datasource.getAllFriends();
		Vector<GeoLocation> allFriendsLocation = ServerLink.getLocationForAllFriend(datasource);
		for (GeoLocation geoLocation : allFriendsLocation) {
			stateManager.addressMap.put(geoLocation.getUsername(), geoLocation.getAddressName());
			stateManager.geopointMap.put(geoLocation.getUsername(), geoLocation.getGeoPoint());
		}
		datasource.close();
		
		for (Friend friend : allFriends) {
			GeoPoint p = stateManager.geopointMap.get(friend.getMITId());
			String add = stateManager.addressMap.get(friend.getMITId());
			OverlayItem item = new OverlayItem(p, friend.getName(), add);
			friendsOverlay.addOverlay(item);
		}

		stateManager.geopointMap.put(stateManager.userName,
				stateManager.userGeoPoint);

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


	public Boolean logout() {
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("username", "none");
		editor.putString("password", "none");
		editor.commit();
		datasource.open();
		datasource.emptyNotifTable();
		datasource.close();
		dirdatasource.open();
		dirdatasource.emptyDirTable();
		dirdatasource.close();
		finish();// close the app instead
		// call authentication webpage and get resposne true or false

		return true;
	}

	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// // TODO Auto-generated method stub
	// super.onCreateContextMenu(menu, v, menuInfo);
	//
	// }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.logout :
				logout();
				Intent intent = new Intent(HomepageActivity.this,
						GDDiscoverMeActivity.class);
				hideEverything();
				finish();
				startActivity(intent);
				return true;
			case R.id.help :
//				Toast.makeText(getApplicationContext(), "help works .. co0l",
//						Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(HomepageActivity.this,
						TutorialActivity.class);
				startActivity(intent2);
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void updateNotificationsCount() {
		int unreadCount = ServerLink.countUnseenNotif(datasource);
		Activity a = this;
		ImageView iv = (ImageView) a
				.findViewById(R.id.notiicationButtonCounter);

		if (iv != null) {
			int resId = a.getResources().getIdentifier(
					"action_bar_btn_notifications_counter_" + unreadCount,
					"drawable", getPackageName());
			if (unreadCount == 0)
			{
				iv.setVisibility(View.GONE);
			}
			else if (unreadCount > 5)
			{
				iv.setImageResource(R.drawable.action_bar_btn_notifications_counter_max);
				iv.setVisibility(View.VISIBLE);
			}
			else
			{
				iv.setImageResource(resId);
				iv.setVisibility(View.VISIBLE);
			}

		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onBackPressed() {
		if (poped != 0) {
			hideEverything();
		} else {
			super.onBackPressed();
		}
	}
	@Override
	protected void onResume() {
		
		backgroundNotificationsHandler = new Handler();
		backgroundNotificationsHandler.removeCallbacks(checkNotificationsTask);
		backgroundNotificationsHandler.postDelayed(checkNotificationsTask, 5000);
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		backgroundNotificationsHandler.removeCallbacks(checkNotificationsTask);
		super.onPause();
	}

}
