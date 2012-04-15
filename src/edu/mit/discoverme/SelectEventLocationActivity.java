package edu.mit.discoverme;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SelectEventLocationActivity extends MapActivity {

	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_event_location_map);

		Button back = (Button) (findViewById(R.id.backButton));
		back.setOnClickListener(onCancelClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.INVISIBLE);

		// Get the map view.
		mapView = (MapView) (findViewById(R.id.select_event_location_mapview));
			
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
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String bestProvider = locationManager.getBestProvider(criteria, true);

		Location locLast = locationManager.getLastKnownLocation(bestProvider);
		GeoPoint test;
		if (locLast != null)
		{
			float lat = (float) locLast.getLatitude();
			float lng = (float) locLast.getLongitude();

			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		}
		else
		{
			float lat = 42.360383f;
			float lng = -71.090899f;

			test = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
			System.out.println("lat:" + lat + ", lng:" + lng);
		}
		mapController.setZoom(18);
		mapController.animateTo(test);

		// Place map overlays to allow user to tap a location.
		// Begin tutorial
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.map_marker_small);
		SelectEventLocationItemizedOverlay itemizedoverlay = new SelectEventLocationItemizedOverlay(
				drawable, this);
		OverlayItem overlayitem = new OverlayItem(test, "Chong-U", "Chong-U Lim");

		// itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
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

}