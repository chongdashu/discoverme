package edu.mit.discoverme;

import java.util.Arrays;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SuggestLocationsActivity extends Activity {
	
	EventLocation Stata = new EventLocation("Stata cafe", true, false, false);
	EventLocation b34 = new EventLocation("34-301", false, true, true);
	EventLocation Stata5G = new EventLocation("32G-575", false, true, false);
	List<EventLocation> locations = Arrays.asList(Stata, Stata5G, b34) ;
	ListView l;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.suggested_location_list);
	    	    
	    //get the view that we will be using
	    l = (ListView)findViewById(R.id.location_listview);
	    
	    //create the array from the list of locations
	    EventLocation[] locationArray = new EventLocation[locations.size()];
	    locationArray = locations.toArray(locationArray);
	    
	    //adapt it
	    l.setAdapter(new EventAdapter(this, R.layout.list_item, locationArray));
	}
		
	
	private class EventAdapter extends ArrayAdapter<EventLocation> {

        private EventLocation[] items;

        public EventAdapter(Context context, int textViewResourceId, EventLocation[] items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item, null);
            
            //get the row item
            //relative layout: textview and horizontal layout
            view = vi.inflate(R.layout.location_row, null);
            
            //get the text view
            TextView tv = (TextView) view.findViewById(R.id.location_textview);
            tv.setText(items[position].name);
                       
            //get the image views to be used
            ImageView food = (ImageView) view.findViewById(R.id.hasFoodView);
            ImageView quiet = (ImageView) view.findViewById(R.id.isQuietView);
            ImageView it = (ImageView) view.findViewById(R.id.hasITView);
            
            
            //if the field is false for any of the locations, disable the notification
            if(!items[position].hasFood){
            	food.setVisibility(View.INVISIBLE);
            }
            
            if(!items[position].isQuiet){
            	quiet.setVisibility(View.INVISIBLE);
            }
            
            if(!items[position].hasIT){
            	it.setVisibility(View.INVISIBLE);
            }

            return view;
        }
    }
}
