package edu.mit.discoverme;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewParticipantsListActivity extends Activity {

	String[] friends;
	List<Friend> allFriends;

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
		l.setAdapter(new ViewParticipantsListAdapter(this, R.layout.list_item, friends));
		
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
			// go back to last page
			finish();

		}
	};

	private class ViewParticipantsListAdapter extends ArrayAdapter<String> {

		private final String[] items;

		public ViewParticipantsListAdapter(Context context, int textViewResourceId, String[] items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.list_item, null);

			TextView tv = (TextView) view
					.findViewById(R.id.list_item_textview);
			tv.setText(Utils.getFriendNameFromMITId(items[position], ViewParticipantsListActivity.this, true));

			// tag it
			tv.setTag(position);

			return view;
		}
	}

	
	
	

}
