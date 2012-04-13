package edu.mit.discoverme;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapTestActivity extends MapActivity {
	
	MapController mapController;
	LocationManager locationManager;
	Criteria criteria;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		
		mapController = mapView.getController();
		mapController.setZoom(18);
		
		criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
	        public void onLocationChanged(Location location) {
	        	System.out.println("onLocationChanged");
	        	String bestProvider = locationManager.getBestProvider(criteria, true);
	        	Location locLast = locationManager.getLastKnownLocation(bestProvider);
	            float lat = (float) locLast.getLatitude();
	            float lng = (float) locLast.getLongitude();
	            
	            System.out.println("lat:"+lat+", lng:"+lng);
	            
	            GeoPoint test = new GeoPoint((int)(lat*1000000), (int)(lng*1000000));
	            // mapController.setCenter(test);
	            // mapController.setZoom(15);
	            mapController.animateTo(test);
	            
	            
	        }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};
		

        String bestProvider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);
		
		Location locLast = locationManager.getLastKnownLocation(bestProvider);
        float lat = (float) locLast.getLatitude();
        float lng = (float) locLast.getLongitude();
        
        GeoPoint test = new GeoPoint((int)(lat*1000000), (int)(lng*1000000));
        System.out.println("lat:"+lat+", lng:"+lng);
        // mapController.setCenter(test);
        // mapController.setZoom(15);
        mapController.animateTo(test);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
