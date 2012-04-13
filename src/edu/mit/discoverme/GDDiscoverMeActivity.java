package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

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

        // (culim) testing map
        Intent intent = new Intent(GDDiscoverMeActivity.this, MapTestActivity.class);
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
    
    private final OnClickListener onButton1Click = new OnClickListener() {
        public void onClick(View v) {
          // do something when the button is clicked
        	
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					FriendsListActivity.class);
        	startActivity(intent);
        }
    };
    
    private OnClickListener onFriendClick = new OnClickListener() {
   		public void onClick(View v) {
			// TODO Auto-generated method stub
   			Intent intent = new Intent(GDDiscoverMeActivity.this, FriendsActivity.class);
    		startActivity(intent);		
		}
	};
	
	private OnClickListener onNotificationClick = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GDDiscoverMeActivity.this, NotificationsActivity.class);
			startActivity(intent);
		}
	};
	
	
    
    
}