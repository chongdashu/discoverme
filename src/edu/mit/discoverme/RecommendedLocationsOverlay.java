package edu.mit.discoverme;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class RecommendedLocationsOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public RecommendedLocationsOverlay(Context context, MapView mapView) {
		super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker3)), mapView);
		populate();
		mContext = context;
	}
	
	public RecommendedLocationsOverlay(Drawable defaultMarker, Context context, MapView mapView) {
		super(boundCenterBottom(defaultMarker), mapView);
		populate();
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index) {
		//Toast.makeText(mContext, "onBalloonTap for overlay index " + index,
		//		Toast.LENGTH_LONG).show();
		Intent intent = new Intent(mContext, SuggestLocationsActivity.class);
		Activity act = (Activity) mContext;
		act.startActivity(intent);
		return true;
	}

}
