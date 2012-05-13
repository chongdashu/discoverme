package edu.mit.discoverme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TutorialActivity extends Activity {

	Button nextButton;
	int step;
	ImageView imageView;
	RelativeLayout layout;
	
	public static final int STEP_INTRODUCTION = 0;
	public static final int STEP_FRIENDS = 1;
	public static final int STEP_EVENTS = 2;
	public static final int STEP_NOTIFS = 3;
	
	public static final int[] TUTORIAL_IMAGES_FOR_STEP =
	{
		R.drawable.tutorial_introduction,
		R.drawable.tutorial_friends,
		R.drawable.tutorial_events,
		R.drawable.tutorial_notifs,
		R.drawable.tutorial_user_location,
		R.drawable.tutorial_friend_location,
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial);
		
		imageView = (ImageView) findViewById(R.id.tutorial_imageview);
		
		layout = (RelativeLayout) findViewById(R.id.tutorial_relativelayout);
		
		
		nextButton = (Button) findViewById(R.id.tutorial_next_button);
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				step++;
				if (step == TUTORIAL_IMAGES_FOR_STEP.length) {
					finish();
				}
				else {
				layout.setBackgroundResource(TUTORIAL_IMAGES_FOR_STEP[step]);
				}
				
				
			}
		});
		
		
	}

}
