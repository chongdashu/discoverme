package edu.mit.discoverme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class SelectEventLocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	private String selectedLocationName;

	public SelectEventLocationItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		populate();
		mContext = context;
	}

	@Override
	public boolean onTap(int index) {
		boolean tapped = super.onTap(index);
		if (tapped) {
			OverlayItem item = mOverlays.get(index);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet());
			dialog.show();
			
		} else {
		}
		return true;
	}

	@Override
	public boolean onTap(final GeoPoint p, final MapView mapView) {
		boolean tapped = super.onTap(p, mapView);
		if (!tapped) {
			 final OverlayItem overlayitem = new OverlayItem(p, "Set the location of the event to:", "Building 32, Stata Center");
			 addOverlay(overlayitem);
			 
			 MapController mapController = mapView.getController();
			 mapController.animateTo(p);
			 
			 AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			 
			 selectedLocationName = overlayitem.getSnippet();
			 
			 dialog	.setTitle(overlayitem.getTitle())
			 		.setMessage(overlayitem.getSnippet())
  	       			.setCancelable(false)
  	       			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
  	           public void onClick(DialogInterface dialog, int id) {
  	               // Do whatever you want for 'Yes' here. 
  	        	   // MyActivity.this.finish();
  	        	   
  	        	   dialog.cancel();
  	        	 
  	        	   Activity mapActivity = (Activity) mContext;
  	        	 
  	        	   Intent resultIntent = mapActivity.getIntent();
  	        	   resultIntent.putExtra("LocationName", selectedLocationName );
  	        	   
  	        	   mapActivity.setResult(Activity.RESULT_OK, resultIntent);
  	        	   mapActivity.finish();
  	           }
  	       })
  	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
  	           public void onClick(DialogInterface dialog, int id) {
  	        	   // Do whatever you want for 'No' here.  
  	        	   removeOverlay(overlayitem);
  	        	   dialog.cancel();
  	        	   mapView.invalidate();
  	           }
  	       });
			 
			 dialog.show();
		}
		return true;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	public void removeOverlay(OverlayItem overlay) {
		mOverlays.remove(overlay);
		populate();
	}

}
