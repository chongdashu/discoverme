package edu.mit.discoverme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.TimePicker;

public class CustomTimePicker extends TimePicker {

	public CustomTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		/*
		 * Prevent parent controls from stealing our events once we've gotten a
		 * touch down
		 */
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			ViewParent p = getParent();
			if (p != null)
				p.requestDisallowInterceptTouchEvent(true);
		}
		return false;
	}
}
