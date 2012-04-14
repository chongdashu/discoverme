package edu.mit.discoverme;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class SelectEventLocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

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
		if (tapped) {
			// do what you want to do when you hit an item
		} else {
			 OverlayItem overlayitem = new OverlayItem(p, "Chong-U", "Chong-U Lim");
			 addOverlay(overlayitem);
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

}
