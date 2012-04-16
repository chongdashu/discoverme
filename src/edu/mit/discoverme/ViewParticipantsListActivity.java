package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewParticipantsListActivity extends Activity {

		String[] friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participant_list);

		Button back = (Button) (findViewById(R.id.backButton));
		back.setText("Back");
		back.setOnClickListener(onBackClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.INVISIBLE);

		TextView activityTitle = (TextView) findViewById(R.id.navbar_title);
		activityTitle.setText("Participants");


		Intent intent = getIntent();
		String participants = intent.getStringExtra("participants");

		
		friends = participants.split(",");
		


		ListView l = (ListView) findViewById(R.id.participant_listview);
		l.setBackgroundColor(Color.WHITE);
		l.setCacheColorHint(Color.WHITE);
		l.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, friends));
		
		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View v, int position, long rowId) {
				Intent intent = new Intent(ViewParticipantsListActivity.this,
						ProfileActivity.class);
				intent.putExtra("personName", ((TextView) v).getText());

				startActivity(intent);
				

			}
		});
		
	}
		
	

	private final OnClickListener onBackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};

	

	
	
	

}
