package edu.mit.discoverme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
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
	protected String[] participants;
	protected String[] responses;

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
		
		participants = getIntent().getStringArrayExtra("participants");
		responses = getIntent().getStringArrayExtra("responses");

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
	
	public void drawColorMapLinesTo(GeoPoint target)
	{
		if (linesOverlay != null) {
			mapView.getOverlays().remove(linesOverlay);
		}
		
		linesOverlay = new SelectEventLocationLinesOverlayColor(target, friendpoints, responses);
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

		mapController.setZoom(18);
		mapController.animateTo(stateManager.userGeoPoint);

		List<Overlay> mapOverlays = mapView.getOverlays();

		// Add the Select Location overlay
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.map_marker_small);
		selectLocationOverlay = new SelectEventLocationItemizedOverlay(
				drawable, this);
		mapOverlays.add(selectLocationOverlay);
		
		// Create overlay for Friends
		HomepageMapOverlay friendsOverlay = new HomepageMapOverlay(
				getResources().getDrawable(R.drawable.marker2), this, mapView);
		mapOverlays.add(friendsOverlay);

		// Old: Show all frinds in friend list on map.
		// String[] friends = getResources().getStringArray(R.array.friends_array);
		
		friendpoints = stateManager.getGeoPointsFromFriends(participants);
		
		int f = 0;
		for (GeoPoint geoPoint : friendpoints) {
			OverlayItem item = new OverlayItem(geoPoint, participants[f], stateManager.friendAddresses.get(f));
			friendsOverlay.addOverlay(item);
			f++;
		}
		
		// Create overlay for User
				HomepageMapOverlay itemizedoverlay = new HomepageMapOverlay(this,
						mapView);
				mapOverlays.add(itemizedoverlay);

				OverlayItem overlayitem = new OverlayItem(stateManager.userGeoPoint, stateManager.userName, stateManager.userAddress);
				itemizedoverlay.addOverlay(overlayitem);
		
		// Handle read-only mode
		if (mode == MODE_VIEW || mode == MODE_PROPOSE){
			GeoPoint loc = new GeoPoint(latE6, lngE6);
			OverlayItem locItem = new OverlayItem(loc, "", "");
			// mapController.animateTo(loc);
			Utils.animateAndZoomToFit(mapController, friendpoints);
			selectLocationOverlay.addOverlay(locItem);
			
			drawColorMapLinesTo(loc);
		}
		
		// Recommendations
		if (mode == MODE_NORMAL) {
			float lat = stateManager.userLat * 1000000 + 200 * (float) (Math.random());
			float lng = stateManager.userLon * 1000000 + 200 * (float) (Math.random());
			
			GeoPoint recPoint = new GeoPoint((int)(lat), (int)(lng));
			
			RecommendedLocationsOverlay recommendedOverlay = new RecommendedLocationsOverlay(this,
					mapView);
			
			// TODO: add back the recommendations after testing
			// mapOverlays.add(recommendedOverlay);

			OverlayItem recItem = new OverlayItem(recPoint, "Phil's Cafe and Meeting Center", "100 Vassar St");
			recommendedOverlay.addOverlay(recItem);
		}
		
		// Add button listener
		ImageButton userLocation = (ImageButton) findViewById(R.id.btn_my_location);
		userLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StateManager stateManager = (StateManager) getApplicationContext();
				SelectEventLocationActivity selectEventActivity = SelectEventLocationActivity.this;

				MapView mapView = (MapView) (selectEventActivity
						.findViewById(R.id.select_event_location_mapview));
				MapController mapController = mapView.getController();

				mapController.animateTo(stateManager.userGeoPoint);

			}
		});
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
