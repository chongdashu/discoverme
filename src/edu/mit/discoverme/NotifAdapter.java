package edu.mit.discoverme;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotifAdapter extends ArrayAdapter<Notif> {

	private List<Notif> notifications;
	private HomepageActivity activity;
	private final HashMap<Integer, TextView> notificationsList = new HashMap<Integer, TextView>();
	private final HashMap<Integer, Boolean> isNotificationUnread = new HashMap<Integer, Boolean>();
	
	public NotifAdapter(Context context, int textViewResourceId, List<Notif> notifications) {
		super(context, textViewResourceId, notifications);
		this.notifications = notifications;
		this.activity = (HomepageActivity) context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = (View) vi.inflate(R.layout.list_item, null);
		
		TextView tv = (TextView) view.findViewById(R.id.list_item_textview);
		Notif notif = notifications.get(position);
		tv.setText(notif.toString());
		
		// tag it
		tv.setTag(position);
		notificationsList.put(position, tv);
		
		System.out.println("NotifAdapter, position:" + position + ", read" + notif.getReadFlag());
		isNotificationUnread.put(position, !notif.isRead());
		
		if (isNotificationUnread.get(position) != null) {
			if (isNotificationUnread.get(position)) {
				tv.setBackgroundColor(activity.getResources().getColor(R.color.notif_unread));
			}
			else {
				tv.setBackgroundColor(activity.getResources().getColor(R.color.white));
			}
		}
		
		return view;
	}
	

}
