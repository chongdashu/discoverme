package edu.mit.discoverme;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class SelectEventLocationLinesOverlayColor
		extends
			SelectEventLocationLinesOverlay {

	public Vector<Integer> colors = new Vector<Integer>();

	protected SelectEventLocationLinesOverlayColor(GeoPoint target,
			Vector<GeoPoint> sources) {
		super(target, sources);
		for (int i = 0; i < sources.size(); i++) {
			double rand = Math.random();

			if (rand < 0.2) {
				colors.add(Color.GRAY);
			} else if (rand >= 0.2 && rand < 0.6) {
				colors.add(Color.RED);
			} else if (rand >= 0.6 && rand < 1.6) {
				colors.add(Color.GREEN);
			} else {
				colors.add(Color.GREEN);
			}
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
