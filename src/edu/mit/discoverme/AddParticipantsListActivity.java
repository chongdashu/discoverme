package edu.mit.discoverme;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddParticipantsListActivity extends Activity
		implements
			OnClickListener {

	String selectedList;
	String[] friends;
	int[] selected;
	
	List<Friend> allFriends;

	private MyDataSource datasource;

	// store state
	private final HashMap<Integer, Boolean> mIsChecked = new HashMap<Integer, Boolean>();
	// store CheckTextView's
	private final HashMap<Integer, CheckedTextView> mCheckedList = new HashMap<Integer, CheckedTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participant_list);
		selectedList = "";

		TextView activityTitle = (TextView) findViewById(R.id.navbar_title);
		activityTitle.setText("Participants");

		Button back = (Button) (findViewById(R.id.backButton));
		back.setText("Cancel");
		back.setOnClickListener(onCancelClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setText("Done");
		next.setOnClickListener(onDoneClick);

		// old: returns list based on fake xml file data
		// friends = getResources().getStringArray(R.array.friends_array);

		SharedPreferences prefs = getSharedPreferences("credentials",
				Context.MODE_WORLD_READABLE);
		String username = prefs.getString("username", "none");

		datasource = new MyDataSource(AddParticipantsListActivity.this);
		datasource.open();
		allFriends = datasource.getAllFriends();
		datasource.close();
		friends = new String[allFriends.size()];
		for (int i = 0; i < allFriends.size(); i++) {
			String friendUsername = allFriends.get(i).getEmail().split("@")[0];
			friends[i] = friendUsername;
		}

		selected = getIntent().getIntArrayExtra("selectedArray");
		if (selected == null) {
			selected = new int[friends.length];
		}

		ListView l = (ListView) findViewById(R.id.participant_listview);
		l.setBackgroundColor(Color.WHITE);
		l.setCacheColorHint(Color.WHITE);
		l.setAdapter(new MyAdapter(this, R.layout.participant_row, friends));
		// l.setAdapter(new ArrayAdapter<String>(this, R.layout.participant_row,
		// friends));
		// l.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView arg0, View v, int position, long
		// rowId) {
		//
		// CheckedTextView tt = (CheckedTextView)
		// v.findViewById(R.id.participant_checked_textview);
		// if (!tt.isChecked()) {
		// tt.setChecked(true);
		// tt.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		// selected[position] = 1;
		//
		// } else {
		// tt.setChecked(false);
		// tt.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		// selected[position] = 0;
		// }
		//
		// }
		// });
	}

	private final OnClickListener onDoneClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			// and flash a message on screen saying friend added

			getSelected();
			Toast.makeText(getApplicationContext(), selectedList,
					Toast.LENGTH_SHORT).show();
			// selectedList = "sunila is there !";
			Intent resultIntent = new Intent();
			resultIntent.putExtra("participants", selectedList);
			resultIntent.putExtra("selectedArray", selected);
			Activity partSelActivity = AddParticipantsListActivity.this;
			partSelActivity.setResult(Activity.RESULT_OK, resultIntent);
			partSelActivity.finish();

		}
	};

	private final OnClickListener onCancelClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			// and flash a message on screen saying friend added
			// Toast.makeText(getApplicationContext(),
			// getString(R.string.addAsFriendMesg), Toast.LENGTH_SHORT).show();
			selectedList = "";
			Intent resultIntent = new Intent();
			resultIntent.putExtra("participants", selectedList);
			Activity partSelActivity = AddParticipantsListActivity.this;
			partSelActivity.setResult(Activity.RESULT_CANCELED, resultIntent);
			partSelActivity.finish();

		}
	};

	private final void getSelected() {
		if (selected != null && friends != null) {
			selectedList = "";
			for (int i = 0; i < selected.length; i++) {
				if (selected[i] == 1)
					selectedList = selectedList + friends[i] + ", ";

			}
		} else
			selectedList = "84038y";
	}

	@Override
	public void onClick(View v) {
		// get the CheckedTextView
		CheckedTextView ct = mCheckedList.get(v.getTag());
		if (ct != null) {
			// change the state and colors
			ct.toggle();
			if (ct.isChecked()) {
				ct.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
				selected[(Integer) v.getTag()] = 1;
			} else {
				ct.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
				selected[(Integer) v.getTag()] = 0;
			}
			// add current state to map
			mIsChecked.put((Integer) v.getTag(), ct.isChecked());
		}
	}

	private class MyAdapter extends ArrayAdapter<String> {

		private final String[] items;

		public MyAdapter(Context context, int textViewResourceId, String[] items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.participant_row, null);

			CheckedTextView ct = (CheckedTextView) view
					.findViewById(R.id.participant_checked_textview);
			ct.setText(Utils.getFriendNameFromMITId(items[position], AddParticipantsListActivity.this, true));

			// tag it
			ct.setTag(position);
			mCheckedList.put(position, ct);
			ct.setOnClickListener(AddParticipantsListActivity.this);

			if (mIsChecked.get(position) == null) {
				if (selected[position] == 1) {
					onClick(view);
				}
			}

			if (mIsChecked.get(position) != null) {
				if (mIsChecked.get(position)) {
					ct.setChecked(true);
					ct.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
				} else {
					ct.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
				}
			}

			return view;
		}
	}

}
