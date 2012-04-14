package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class GDDiscoverMeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        
        ImageButton friend = (ImageButton) (findViewById(R.id.friendButton));
//        button.setText(R.string.button_text_friend);
        friend.setOnClickListener(onFriendClick);
        
        ImageButton event = (ImageButton) (findViewById(R.id.eventButton));
        event.setOnClickListener(onEventClick);
        
        ImageButton notif = (ImageButton) (findViewById(R.id.notificationButton));
        notif.setOnClickListener(onNotificationClick);
        
        Button back = (Button)(findViewById(R.id.backButton));
        back.setOnClickListener(onBackClick);
        
        Button next = (Button)(findViewById(R.id.nextButton));
        next.setOnClickListener(onNextClick);
        
        // (culim) testing map
        Intent intent = new Intent(GDDiscoverMeActivity.this, CreateEventActivity.class);
		// Uncomment next line to launch map:

		// startActivity(intent);
        
        // ethan's part


		// TextView view = (TextView) findViewById(R.id.list_friends);
		// String s = "";
		// for (int i = 0; i < 100; i++) {
		// s += "vogella.com ";
		// }
		// view.setText(s);
    }
    
	private final OnClickListener onFriendClick = new OnClickListener() {
        @Override
		public void onClick(View v) {
          // do something when the button is clicked
        	
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					PopupListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.putExtra("popupCode", "friendss");
			startActivity(intent);
        	
        }
    };

	private final OnClickListener onEventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					PopupListActivity.class);// EventsActivity.class);
			intent.putExtra("popupCode", "eventss");
			startActivity(intent);
		}
	};

	private final OnClickListener onNotificationClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					PopupListActivity.class);// NotificationsActivity.class);
			intent.putExtra("popupCode", "notifss");
			startActivity(intent);
		}
	};
	
	private final OnClickListener onNextClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private final OnClickListener onBackClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
    
    
}