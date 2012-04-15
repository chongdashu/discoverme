package edu.mit.discoverme;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends ListActivity{
	
	EditText searchBar;
	ArrayAdapter<String> adapter;
	SearchActivity self;
	String[] searchList;
	String popup;
	
	ListView lv;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    self = this;
	    
		Button back = (Button) (findViewById(R.id.backButton));
		back.setOnClickListener(onCancelClick);

		Button next = (Button) (findViewById(R.id.nextButton));
		next.setVisibility(View.INVISIBLE);

//	    //create generic array of strings to work with
//	    String[] searchList;
	    
	    //get the popupCode that was passed in the intent to figure out
	    //what list you are searching in at the moment
	    Intent intent = getIntent();
	    popup = intent.getStringExtra("popupCode");
	    
	    if(popup.equals("friendss")){
			searchList = getResources().getStringArray(R.array.directory_array);
	    }
	    else if(popup.equals("eventss")){
	    	searchList = getResources().getStringArray(R.array.events_array);
	    }
	    else{
	    	searchList = getResources().getStringArray(R.array.notifs_array);
	    }
	    
	    
	    searchBar = (EditText)(findViewById(R.id.searchInput));
	    searchBar.addTextChangedListener(watcher);
	    
	    lv = (ListView)findViewById(android.R.id.list);
		lv.setBackgroundColor(Color.WHITE);
		lv.setCacheColorHint(Color.WHITE);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (popup.equals("friendss")) {
					Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
					intent.putExtra("personName", ((TextView) view).getText());
					startActivity(intent);

					// finishActivity(-1);
				} else if (popup.equals("eventss")) {
					// this will actually go to view event page
					Intent intent = new Intent(SearchActivity.this, CreateEventActivity.class);
					intent.putExtra("eventName", ((TextView) view).getText());
					startActivity(intent);

				}

			}
		});

	    
	    
	    
	}
	private final TextWatcher watcher = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			//create a list to hold the results
			ArrayList<String> results = new ArrayList<String>();
			
			//only look if the text field has characters in it
			//if it doesnt results will be blank
			if(searchBar.getText().length() != 0){
				//iterate through the list we are looking for
				for(int i = 0; i < searchList.length; i++){
					//see if it starts what the user queried
					if(searchList[i].startsWith(arg0.toString().toLowerCase())){
						results.add(searchList[i]);
					}
				}
			}
			//make it a string array
			String[] responses = new String[results.size()];
		    responses = results.toArray(responses);
		    
		    //display to the screen!
			lv.setAdapter(new ArrayAdapter<String>(self, R.layout.list_item, responses));
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	};
	private final OnClickListener onCancelClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// go back to last page
			finish();

		}
	};


	

}
