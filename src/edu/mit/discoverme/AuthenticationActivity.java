package edu.mit.discoverme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AuthenticationActivity extends Activity {
	MyDataSource datasource;
	DirDataSource dirdatasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication);
		Button cancelB = (Button) (findViewById(R.id.cancel));
		cancelB.setOnClickListener(onCancelClick);

		Button enterB = (Button) (findViewById(R.id.enter));
		enterB.setOnClickListener(onEnterClick);

	}

	private final OnClickListener onEnterClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked

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

				ServerLink.loadFriends(usr, datasource);
				ServerLink.loadNotifs(usr, datasource);
				datasource.close();

				dirdatasource = new DirDataSource(AuthenticationActivity.this);
				dirdatasource.open();
				ServerLink.loadPeople(dirdatasource);
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
	};

	private final OnClickListener onCancelClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("event clicked");
		}
	};


}
