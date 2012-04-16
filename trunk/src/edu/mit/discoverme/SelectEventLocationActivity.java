package edu.mit.discoverme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SelectEventLocationActivity extends MapActivity {

	
	public static final int MODE_NORMAL = 0;
	public static final int MODE_VIEW = 1;
	public static final int MODE_PROPOSE = 2;
	
	protected MapView mapView;
	protected SelectEventLocationItemizedOverlay selectLocationOverlay;
	protected String selectedLocation;
	protected String locationLng;
	protected String locationLat;
	protected SelectEventLocationLinesOverlay linesOverlay;
	protected Vector<GeoPoint> friendpoints;
	// protected boolean readOnly;
	protected int mode;
	protected int latE6;
	protected int lngE6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_event_location_map);

		Button back = (Button) (findViewById(R.id.backButton));
		back.setOnClickListener(onCancelClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setText(R.string.button_text_done);
		next.setVisibility(View.INVISIBLE);
		next.setOnClickListener(onDoneClick);
		
		// readOnly = getIntent().getBooleanExtra("readOnly", false);
		mode = getIntent().getIntExtra("mode", MODE_NORMAL);
		
		if (mode == MODE_VIEW || mode == MODE_PROPOSE) {
			latE6 = getIntent().getIntExtra("lat", (int)(42.360383*1000000));
			lngE6 = getIntent().getIntExtra("lng", (int)(-71.090899*1000000));
		}

		initializeMap();
		initializeConfirmationArea();
	}

	public void setSelectionlocation(String name, float lat, float lng) {
		selectedLocation = name;
		locationLat = String.valueOf(lat);
		locationLng = String.valueOf(lng);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.VISIBLE);
	}
	
	public void drawMapLinesTo(GeoPoint target)
	{
		if (linesOverlay != null) {
			mapView.getOverlays().remove(linesOverlay);
		}
		
		linesOverlay = new SelectEventLocationLinesOverlay(target, friendpoints);
		mapView.getOverlays().add(linesOverlay);
	}

	private void initializeConfirmationArea() {

		Button change = (Button) (findViewById(R.id.select_event_location_btn_change));
		change.setOnClickListener(onChangeClick);
	}

	private final OnClickListener onChangeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mapView.getOverlays().remove(linesOverlay);
			
			LinearLayout confirmationArea = (LinearLayout) (findViewById(R.id.select_event_location_confirmation_area));
			confirmationArea.setVisibility(View.GONE);
			Button next = (Button) (findViewById(R.id.nextButton));
			next.setVisibility(View.INVISIBLE);
			selectLocationOverlay.clearOverlays();

		}
	};

	private void initializeMap() {
		// Get the map view.
		mapView = (MapView) (findViewById(R.id.select_event_location_mapview));

		// Get the map controller
		MapController mapController = mapView.getController();
		
		StateManager stateManager = (StateManager) getApplicationContext();

		// Get to current location
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		criteria.setAltitudeRequired(false);
//		criteria.setBearingRequired(false);
//		criteria.setSpeedRequired(false);
//		criteria.setCostAllowed(true);
//
//		LocationManager locationManager;
//		locationManager = (LocationManager) this
//				.getSystemService(Context.LOCATION_SERVICE);
//		String bestProvider = locationManager.getBestProvider(criteria, true);
//
//		Location locLast = locationManager.getLastKnownLocation(bestProvider);
		// GeoPoint test;
		float lat;
		float lng;
//		if (locLast != null) {
//			lat = (float) locLast.getLatitude();
//			lng = (float) locLast.getLongitude();
//
//			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
//			System.out.println("lat:" + lat + ", lng:" + lng);
//		} else {
//			lat = 42.360383f;
//			lng = -71.090899f;
//
//			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
//			System.out.println("lat:" + lat + ", lng:" + lng);
//		}
		mapController.setZoom(18);
		mapController.animateTo(stateManager.userGeoPoint);

		List<Overlay> mapOverlays = mapView.getOverlays();

		// Add the Select Location overlay
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.map_marker_small);
		selectLocationOverlay = new SelectEventLocationItemizedOverlay(
				drawable, this);
		mapOverlays.add(selectLocationOverlay);
		
		// Add the user overlay
		HomepageMapOverlay itemizedoverlay = new HomepageMapOverlay(this,
				mapView);
		mapOverlays.add(itemizedoverlay);

		OverlayItem overlayitem = new OverlayItem(stateManager.userGeoPoint, stateManager.userName, stateManager.userAddress);
		itemizedoverlay.addOverlay(overlayitem);

		// Create overlay for Friends
		HomepageMapOverlay friendsOverlay = new HomepageMapOverlay(
				getResources().getDrawable(R.drawable.marker2), this, mapView);
		mapOverlays.add(friendsOverlay);

		String[] friends = getResources().getStringArray(R.array.friends_array);
		friendpoints = stateManager.friendPoints;
		
		int f = 0;
		for (GeoPoint geoPoint : friendpoints) {
			OverlayItem item = new OverlayItem(geoPoint, friends[f], stateManager.friendAddresses.get(f));
			friendsOverlay.addOverlay(item);
			f++;
		}
		
		// Handle read-only mode
		if (mode == MODE_VIEW || mode == MODE_PROPOSE){
			GeoPoint loc = new GeoPoint(latE6, lngE6);
			OverlayItem locItem = new OverlayItem(loc, "", "");
			mapController.animateTo(loc);
			selectLocationOverlay.addOverlay(locItem);
			
			drawMapLinesTo(loc);
		}
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

	private final OnClickListener onCancelClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};
	
	private final OnClickListener onDoneClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
     	 
     	   Intent resultIntent = getIntent();
     	   resultIntent.putExtra("LocationName", selectedLocation );
			resultIntent.putExtra("LocationLng", locationLng);
			resultIntent.putExtra("LocationLat", locationLat);
     	   
     	   setResult(Activity.RESULT_OK, resultIntent);
     	   finish();

		}
	};

}
