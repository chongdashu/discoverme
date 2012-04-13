package edu.mit.discoverme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialogBoxActivity extends Activity{

	private AlertDialog alert;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to exit?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               // Do whatever you want for 'Yes' here. 
    	        	   // MyActivity.this.finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   // Do whatever you want for 'No' here.  
    	        	   dialog.cancel();
    	           }
    	       });
    	alert = builder.create();
		
		Button b = (Button)(findViewById(R.id.button_dialog_sample));
		b.setOnClickListener(onButtonClick);
	}
	
	private OnClickListener onButtonClick = new OnClickListener() {
        public void onClick(View v) {
        	
        	alert.show();
        	
        }
    };
	

}
