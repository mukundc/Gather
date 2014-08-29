package com.example.gather;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyGatherings extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_gatherings);
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
		final ParseUser currentUser = ParseUser.getCurrentUser();
		final ArrayList<ParseObject> myGatherings = (ArrayList<ParseObject>) currentUser.get("myCreatedGatherings");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Gathering");
		query.whereNotEqualTo("admin", currentUser.getObjectId());
		query.include("users");
		final ArrayList<ParseObject> myInvitedGatherings = new ArrayList<ParseObject>();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> allGatherings, ParseException e) {
                if (e == null) {
                	for (ParseObject obj1 : allGatherings){
                		try {
							for(ParseUser person : ((ArrayList<ParseUser>)(obj1.fetchIfNeeded().get("users")))){
			                	if (person.fetchIfNeeded().getObjectId().equals(currentUser.fetchIfNeeded().getObjectId())){			              
			                		myInvitedGatherings.add(obj1);
			                	}
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
							
						}
                	}
                } else {
                    Log.d("User", "Error: " + e.getMessage());
                }
            }
        });
    	//Log.d("mygatherings2", myGatherings.toString());
		for (ParseObject obj: myGatherings){
			TextView name = new TextView(this);
			try {
				name.setText(obj.fetchIfNeeded().getString("name"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//Log.d("REACHED", "reached");
			linearLayout.addView(name);
			
		}
		for (ParseObject obj3: myInvitedGatherings){
			TextView name2 = new TextView(this);
			try {
				name2.setText(obj3.fetchIfNeeded().getString("name"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	Log.d("REACHED", "reached");
			linearLayout.addView(name2);
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_gatherings, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
