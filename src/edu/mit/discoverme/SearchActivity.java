package edu.mit.discoverme;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends ListActivity{
	
	EditText searchBar;
	ArrayAdapter<String> adapter;
	SearchActivity self;
	String[] searchList;
	
	
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
	    String popup = intent.getStringExtra("popupCode");
	    
	    if(popup.equals("friendss")){
	    	searchList = getResources().getStringArray(R.array.friends_array);
	    }
	    else if(popup.equals("eventss")){
	    	searchList = getResources().getStringArray(R.array.events_array);
	    }
	    else{
	    	searchList = getResources().getStringArray(R.array.notifs_array);
	    }
	    
	    
	    searchBar = (EditText)(findViewById(R.id.searchInput));
//	    searchBar.setOnKeyListener(onSearchInput);
	    searchBar.addTextChangedListener(watcher);
//	    adapter = new ArrayAdapter<String>(this, R.layout.list_item, searchList);
//	    searchBar.setAdapter(adapter);
//	    EditText search = (EditText) (findViewById(R.id.searchInput));
	    

	    
	    
	    
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
			setListAdapter(new ArrayAdapter<String>(self, R.layout.list_item, responses));
			
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

//	@Override
//	public Filter getFilter() {
//		// TODO Auto-generated method stub
//		return new Filter(){
//
//			@Override
//			protected FilterResults performFiltering(CharSequence constraint) {
//				// TODO Auto-generated method stub
//				List<String> filteredResults = getFilteredResults(constraint);
//
//                FilterResults results = new FilterResults();
//                results.values = filteredResults;
//
//                return results;
//			}
//
//			@SuppressWarnings("unchecked")
//			@Override
//			protected void publishResults(CharSequence constraint,
//					FilterResults results) {
//				// TODO Auto-generated method stub
//				List<String>myData = (List<String>) results.values;
//                ArrayAdapter<String>.this.notifyDataSetChanged();
//			}
//		};
//	}
	
//	private final View.OnKeyListener onSearchInput = new View.OnKeyListener() {
//
//		@Override
//		public boolean onKey(View v, int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//			searchBar.dismissDropDown();
//			String[] results = new String[adapter.getCount()];
//			for(int i = 0; i < adapter.getCount(); i++ ){
//				results[i] = adapter.getItem(i);
//			}
////			setListAdapter(new ArrayAdapter<String>(self, R.layout.list_item,
////					results));
//			return false;
//		}
//	}; 
	
//	private static class FilterAdapter extends ArrayAdapter implements Filterable{
//		
//	}
	
	

}
