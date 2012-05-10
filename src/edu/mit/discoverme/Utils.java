package edu.mit.discoverme;

import java.util.Vector;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

public class Utils {

	public static String foldParticipantsList(String[] participants) {
		String s = "";

		for (int i = 0; i < participants.length; i++) {
			String string = participants[i];
			s = s + string + ", ";
		}

		return s;
	}
	public static Vector<GeoPoint> getRandomGeopointsAround(float lat,
			float lon, int n) {

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

	public static void setLayoutAnim_slidedown(ViewGroup panel, Context ctx) {

		AnimationSet set = new AnimationSet(true);

		Animation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(800);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				// MapContacts.this.mapviewgroup.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				// TODO Auto-generated method stub

			}
		});
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.25f);
		panel.setLayoutAnimation(controller);

	}

	public static void setLayoutAnim_slideup(ViewGroup panel, Context ctx) {

		AnimationSet set = new AnimationSet(true);

		/*
		 * Animation animation = new AlphaAnimation(1.0f, 0.0f);
		 * animation.setDuration(200); set.addAnimation(animation);
		 */

		Animation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f);
		animation.setDuration(800);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// MapContacts.this.mapviewgroup.setVisibility(View.INVISIBLE);
				// TODO Auto-generated method stub

			}
		});
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.25f);
		panel.setLayoutAnimation(controller);

	}

	public static void animateAndZoomToFit(MapController controller,
			Vector<GeoPoint> points) {
		int minLat = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int minLon = Integer.MAX_VALUE;
		int maxLon = Integer.MIN_VALUE;

		for (GeoPoint p : points) {

			int lat = p.getLatitudeE6();
			int lon = p.getLongitudeE6();

			maxLat = Math.max(lat, maxLat);
			minLat = Math.min(lat, minLat);
			maxLon = Math.max(lon, maxLon);
			minLon = Math.min(lon, minLon);
		}

		controller.zoomToSpan(Math.abs(maxLat - minLat),
				Math.abs(maxLon - minLon));
		controller.animateTo(new GeoPoint((maxLat + minLat) / 2,
				(maxLon + minLon) / 2));
	}

}
