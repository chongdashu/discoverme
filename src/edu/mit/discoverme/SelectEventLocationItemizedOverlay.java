package edu.mit.discoverme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class SelectEventLocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private final ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private final Context mContext;
	
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
		final Activity activity = (Activity)(mContext);
		final SelectEventLocationActivity selectEventActivity = (SelectEventLocationActivity)activity;
		
		if (!tapped && (mOverlays.size() == 0 || selectEventActivity.mode == SelectEventLocationActivity.MODE_PROPOSE) ) {
			 
			if (mOverlays.size() > 0) {
				mOverlays.clear();
			}
			
			final OverlayItem overlayitem = new OverlayItem(p, "Set the location of the event to:", Utils.getAddressAt(mapView.getContext(), p));
			addOverlay(overlayitem);
			 
			 MapController mapController = mapView.getController();
			 mapController.animateTo(p);
			 
			 AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			 
			 selectedLocationName = overlayitem.getSnippet();
			 
			 dialog	.setTitle(overlayitem.getTitle())
			 		.setMessage(overlayitem.getSnippet())
  	       			.setCancelable(false)
  	       			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
  	           @Override
			public void onClick(DialogInterface dialog, int id) {
  	               // Do whatever you want for 'Yes' here. 
  	        	   // MyActivity.this.finish();
  	        	   
  	        	   dialog.dismiss();
  	        	   
  	        	   
  	        	   LinearLayout confirmationArea = (LinearLayout)(activity.findViewById(R.id.select_event_location_confirmation_area));
  	        	   confirmationArea.setVisibility(View.VISIBLE);
  	        	   
  	        	   TextView tv = (TextView)(activity.findViewById(R.id.select_event_location_name));
  	        	   tv.setText(selectedLocationName);
  	        	   
									selectEventActivity.setSelectionlocation(
											selectedLocationName,
											((float) p.getLatitudeE6()) / 1000000,
											((float) p.getLongitudeE6()) / 1000000);
  	        	   
  	        	 selectEventActivity.drawMapLinesTo(p);
  	        	   
  	        	 ImageButton userLocation = (ImageButton) activity.findViewById(R.id.select_event_location_my_location);
  	        	 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
  	        	 params.addRule(RelativeLayout.ABOVE, confirmationArea.getId());
  	        	 params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
  	        	 userLocation.setLayoutParams(params);
  	        	 
  	        	   // Uncomment this when ready.
//  	        	   Activity mapActivity = (Activity) mContext;
//  	        	 
//  	        	   Intent resultIntent = mapActivity.getIntent();
//  	        	   resultIntent.putExtra("LocationName", selectedLocationName );
//  	        	   
//  	        	   mapActivity.setResult(Activity.RESULT_OK, resultIntent);
//  	        	   mapActivity.finish();
  	           }
  	       })
  	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
  	           @Override
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
	
	public void clearOverlays() {
		mOverlays.clear();
		populate();
	}

}
