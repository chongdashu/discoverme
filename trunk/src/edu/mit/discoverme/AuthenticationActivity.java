package edu.mit.discoverme;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class AuthenticationActivity extends Activity {
	MyDataSource datasource;
	DirDataSource dirdatasource;
	Button enterB;
	EditText password;
	EditText userName;
	StateManager stateManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication);
		Button cancelB = (Button) (findViewById(R.id.cancel));
		cancelB.setOnClickListener(onCancelClick);
		
		stateManager = (StateManager)(getApplicationContext());

		enterB = (Button) (findViewById(R.id.enter));
		enterB.setOnClickListener(onEnterClick);
		enterB.setEnabled(false);
		
		password =  (EditText)(findViewById(R.id.password));
		password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String text = s.toString();
				String username = password.getText().toString();
				enterB.setEnabled(text.length() > 0 && username.length() > 0);
			}
		});
		password.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId==EditorInfo.IME_ACTION_DONE){
					doLogin();
		        }
		    return false;
			}
		});
		
		userName = (EditText)(findViewById(R.id.username));
		userName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String text = s.toString();
				String pword = password.getText().toString();
				enterB.setEnabled(text.length() > 0 && pword.length() > 0);
			}
		});

	}

	private void doLogin() {
		TextView username = (TextView) findViewById(R.id.username);
		TextView password = (TextView) findViewById(R.id.password);

		String usr = username.getText().toString();
		String pswrd = password.getText().toString();

		Boolean auth = Authenticate.check(usr, pswrd);
		if (auth) {
			SharedPreferences prefs = getSharedPreferences("credentials",
					Context.MODE_WORLD_READABLE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("username", usr);
			editor.putString("password", pswrd);
			editor.commit();
			datasource = new MyDataSource(AuthenticationActivity.this);
			datasource.open();
			dirdatasource = new DirDataSource(AuthenticationActivity.this);
			dirdatasource.open();
			ServerLink.loadPeople(dirdatasource);
			ServerLink.loadFriends(usr, datasource);
			// ServerLink.loadEvents("saqib01", datasource);
			Vector<GeoLocation> allFriendsLocation = ServerLink.getLocationForAllFriend(datasource);
			for (GeoLocation geoLocation : allFriendsLocation) {
				stateManager.addressMap.put(geoLocation.getUsername(), geoLocation.getAddressName());
				stateManager.geopointMap.put(geoLocation.getUsername(), geoLocation.getGeoPoint());
			}
			// ServerLink.loadEvents("pmerc02", datasource);
			// ServerLink.loadEvents("pmerc03", datasource);
			ServerLink.loadNotifs(usr, datasource, dirdatasource);
			datasource.close();
			dirdatasource.close();
			Toast.makeText(getApplicationContext(), "valid credentials!!",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthenticationActivity.this,
					GDDiscoverMeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "invalid credentials",
					Toast.LENGTH_SHORT).show();
		}
	}

	private final OnClickListener onEnterClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			// doLogin();
			Intent intent = new Intent(AuthenticationActivity.this,
					TutorialActivity.class);
			startActivity(intent);

			
		}
	};

	private final OnClickListener onCancelClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("event clicked");
		}
	};


}
