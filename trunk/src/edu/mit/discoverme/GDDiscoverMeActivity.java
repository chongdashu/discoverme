package edu.mit.discoverme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
        
		StateManager appState = ((StateManager) getApplicationContext());
		appState.start();
		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
			 
		String username = prefs.getString("username", "none");
		String password = prefs.getString("password", "none");

		if(username.equals("none")){
			Toast.makeText(getApplicationContext(), "no credentials",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(GDDiscoverMeActivity.this,
				AuthenticationActivity.class);
			startActivity(intent);
		}
		else 
		{
			Toast.makeText(getApplicationContext(), "credentials found!!",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					HomepageActivity.class);// MyDatabaseActivity.class);//
			startActivity(intent);
		}
			
		
        
    }
    
	private final OnClickListener onFriendClick = new OnClickListener() {
        @Override
		public void onClick(View v) {
          // do something when the button is clicked
        	
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					HomepageActivity.class);
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
					HomepageActivity.class);// EventsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.putExtra("popupCode", "eventss");
			startActivity(intent);
		}
	};

	private final OnClickListener onNotificationClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GDDiscoverMeActivity.this,
					HomepageActivity.class);// NotificationsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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