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
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class ParticipantListingActivity extends Activity {


	String selectedList;
	String[] friends;
	int[] selected;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participant_list);
		selectedList = "";

		Button back = (Button) (findViewById(R.id.backButton));
		back.setText("Cancel");
		back.setOnClickListener(onCancelClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setText("Done");
		next.setOnClickListener(onDoneClick);

		friends = getResources().getStringArray(R.array.friends_array);

		selected = new int[friends.length];

		ListView l = (ListView) findViewById(R.id.participant_listview);
		l.setBackgroundColor(Color.WHITE);
		l.setCacheColorHint(Color.WHITE);
		l.setAdapter(new ArrayAdapter<String>(this, R.layout.participant_row,
				friends));
		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View v, int arg2,
					long arg3) {
				CheckedTextView tt = (CheckedTextView) v
						.findViewById(R.id.participant_checked_textview);
				if (!tt.isChecked()) {
					tt.setChecked(true);
					tt.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
					selected[arg2] = 1;

				} else {
					tt.setChecked(false);
					tt.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
					selected[arg2] = 0;
				}

			}
		});
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
			Activity partSelActivity = ParticipantListingActivity.this;
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
			Toast.makeText(getApplicationContext(),
					getString(R.string.addAsFriendMesg), Toast.LENGTH_SHORT)
					.show();
			selectedList = "";
			Intent resultIntent = new Intent();
			resultIntent.putExtra("participants", selectedList);
			Activity partSelActivity = ParticipantListingActivity.this;
			partSelActivity.setResult(Activity.RESULT_CANCELED, resultIntent);
			partSelActivity.finish();

		}
	};

	private final void getSelected()
	{
		if (selected != null && friends != null) {
			selectedList = "";
			for (int i = 0; i < selected.length; i++) {
				if (selected[i] == 1)
					selectedList = selectedList + friends[i] + ", ";

			}
		}
 else
			selectedList = "84038y";
	}

}
