package edu.mit.discoverme;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class SelectEventLocationLinesOverlay extends Overlay {
	protected GeoPoint target;
	protected Vector<GeoPoint> sources;

	protected SelectEventLocationLinesOverlay(GeoPoint target, Vector<GeoPoint> sources) {
		this.target = target;
		this.sources = sources;
	}
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		super.draw(canvas, mapView, shadow);
		Paint paint;
		for (GeoPoint point : sources) {
			paint = new Paint();
			paint.setColor(Color.GRAY);
			paint.setAntiAlias(true);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(5);
			Point pt1 = new Point();
			Point pt2 = new Point();
			Projection projection = mapView.getProjection();
			projection.toPixels(target, pt1);
			projection.toPixels(point, pt2);
			canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
		}
		
		return true;
	}
}
