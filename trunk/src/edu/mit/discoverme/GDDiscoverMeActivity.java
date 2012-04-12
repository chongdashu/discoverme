package edu.mit.discoverme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GDDiscoverMeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) (findViewById(R.id.button1));
        button.setText(R.string.button_text_friend);
        
        button.setOnClickListener(onButton1Click);
    }
    
    private OnClickListener onButton1Click = new OnClickListener() {
        public void onClick(View v) {
          // do something when the button is clicked
        	
        	Intent intent = new Intent(GDDiscoverMeActivity.this, EventsActivity.class);
        	startActivity(intent);
        }
    };
    
    
}