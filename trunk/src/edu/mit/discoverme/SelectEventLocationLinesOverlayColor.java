package edu.mit.discoverme;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class SelectEventLocationLinesOverlayColor
		extends
			SelectEventLocationLinesOverlay {

	public Vector<Integer> colors = new Vector<Integer>();
	public Vector<PathEffect> pathEffects = new Vector<PathEffect>();

	protected SelectEventLocationLinesOverlayColor(GeoPoint target,
			Vector<GeoPoint> sources, String[] responses) {
		super(target, sources);
		for (int i = 0; i < sources.size(); i++) {
			
			// Set the color based on the participants' response
			colors.add(getColorFromResponse(responses[i]));
			pathEffects.add(getPathEffectFromResponse(responses[i]));
			
//			// Old code:
//			// This just creates random colors
//			double rand = Math.random();
//
//			if (rand < 0.2) {
//				colors.add(Color.GRAY);
//			} else if (rand >= 0.2 && rand < 0.6) {
//				colors.add(Color.RED);
//			} else if (rand >= 0.6 && rand < 1.6) {
//				colors.add(Color.GREEN);
//			} else {
//				colors.add(Color.GREEN);
//			}
		}
	}
	
	private PathEffect getPathEffectFromResponse(String response)
	{
		if (response.compareTo("yes") == 0)
		{
			return new PathEffect();
		}
		else if (response.compareTo("no") == 0)
		{
			return new DashPathEffect(new float[] {10,40}, 0);
		}
		else
		{
			return new DashPathEffect(new float[] {20,5}, 0);
		}
	}
	
	private int getColorFromResponse(String response)
	{
		if (response.compareTo("yes") == 0)
		{
			return Color.GREEN;
		}
		else if (response.compareTo("no") == 0)
		{
			return Color.RED;
		}
		else
		{
			return Color.GRAY;
		}
	}


	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		super.draw(canvas, mapView, shadow);
		Paint paint;
		int i = 0;
		for (GeoPoint point : sources) {
			paint = new Paint();
			paint.setColor(colors.get(i));
			paint.setAntiAlias(true);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(3);
			paint.setPathEffect(pathEffects.get(i));
			Point pt1 = new Point();
			Point pt2 = new Point();
			Projection projection = mapView.getProjection();
			projection.toPixels(target, pt1);
			projection.toPixels(point, pt2);
			canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
			i++;
		}

		return true;
	}
}
