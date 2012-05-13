package edu.mit.discoverme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewProposedChangeActivity extends ViewEventActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
//		back.setText("Reject");
		next.setText("Respond");
		next.setVisibility(View.VISIBLE);
		next.setOnClickListener(onRespondClick);
	}
	
	
	protected OnClickListener onRespondClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewProposedChangeActivity.this);
			builder.setMessage("Do you want to accept these changes?")
			.setCancelable(true)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
									ServerLink
											.acceptProposedChange(new Event());// TODO
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
			
		}
	};

}
