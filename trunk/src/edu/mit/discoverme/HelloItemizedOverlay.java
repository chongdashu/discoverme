package edu.mit.discoverme;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
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
		if (tapped) {
			// do what you want to do when you hit an item
		} else {
			 Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.androidmarker);
			 OverlayItem overlayitem = new OverlayItem(p, "Chong-U", "Chong-U Lim");
			 addOverlay(overlayitem);
		}
		return true;
	}

	private OverlayItem drawMarker(GeoPoint p) {
		OverlayItem overlayitem = new OverlayItem(p,
				"Select As Game Location?",
				"Do you want this location to be added as the location for the game?");
		mOverlays.clear();
		addOverlay(overlayitem);
		return overlayitem;
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

}
