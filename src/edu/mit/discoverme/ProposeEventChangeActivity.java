package edu.mit.discoverme;

import android.view.View;


public class ProposeEventChangeActivity extends ViewEventActivity {

	@Override
	protected void inititialize() {
		super.inititialize();
		proposeChangeArea.setVisibility(View.VISIBLE);
	}
	
}
