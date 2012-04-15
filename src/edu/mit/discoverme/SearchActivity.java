package edu.mit.discoverme;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class SearchActivity extends ListActivity {
	
	AutoCompleteTextView searchBar;
	ArrayAdapter<String> adapter;
	SearchActivity self;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
//	    self = this;
	    
	    //create generic array of strings to work with
	    String[] searchList;
	    
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
	    
	    
	    searchBar = (AutoCompleteTextView)(findViewById(R.id.searchInput));
	    searchBar.setOnKeyListener(onSearchInput);
	    //searchBar.addTextChangedListener(watcher);
	    adapter = new ArrayAdapter<String>(this, R.layout.list_item, searchList);
	    searchBar.setAdapter(adapter);
	    EditText search = (EditText) (findViewById(R.id.searchInput));
	    
	    search.addTextChangedListener(new TextWatcher() {
	        @Override
	        public void afterTextChanged(Editable s) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            // TODO Auto-generated method stub
	        }
	            
	    });
	    
	    
	    
	}
	
	private final View.OnKeyListener onSearchInput = new View.OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			searchBar.dismissDropDown();
			String[] results = new String[adapter.getCount()];
			for(int i = 0; i < adapter.getCount(); i++ ){
				results[i] = adapter.getItem(i);
			}
//			setListAdapter(new ArrayAdapter<String>(self, R.layout.list_item,
//					results));
			return false;
		}
	}; 
	
}
