package com.example.gather;

//import MainActivity;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.facebook.Request.GraphUserListCallback;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGathering extends Activity {
	FriendPickerFragment friendpickerfragment = new FriendPickerFragment();
	List<ParseObject> friendUsers;
	GatheringObject gatheringobj;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_gathering);

		Button button = (Button) findViewById(R.id.next);

        button.setOnClickListener(new OnClickListener() {
            @Override
        	public void onClick(View v) {
            	String name = ((EditText) findViewById(R.id.editText1)).getText().toString(); 
        		String description = ((EditText) findViewById(R.id.editText2)).getText().toString();
        		String location = ((EditText) findViewById(R.id.editText3)).getText().toString();
        		gatheringobj = new GatheringObject(name,description,location, null);
        		//Toast.makeText(CreateGathering.this,location,Toast.LENGTH_SHORT).show();
        		Intent i = new Intent(CreateGathering.this, CreateGathering2.class);
        		i.putExtra("gatheringObj", gatheringobj);
        		startActivityForResult(i, 1);
        		//Bundle args = getIntent().getExtras();
            }
        });
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_gathering, menu);
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
	
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        final StringBuffer friendsList = new StringBuffer();

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	        		ArrayList usersID = data.getStringArrayListExtra("selected friends");
	        		gatheringobj.setFriends(usersID);
	                final ParseUser currentUser = ParseUser
							.getCurrentUser();
	                final ParseObject gathering = new ParseObject("Gathering");
	                gathering.put("name", gatheringobj.getName());
	                gathering.put("description", gatheringobj.getDescription());
	                gathering.put("location", gatheringobj.getLocation());
	                gathering.put("createdBy", currentUser);
	                gathering.add("users", currentUser);
	                gathering.saveInBackground();
	                currentUser.add("myCreatedGatherings", gathering);
	                currentUser.saveInBackground();
	                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
	               
	                for (int i = 0; i < usersID.size(); i++)
	                {
		                query.whereEqualTo("fbId", usersID.get(i).toString());
		                try {
							List<ParseObject> scoreList = query.find();
							 Log.d("User", "Retrieved " + scoreList.size() + " users");
		                     ParseUser invitedUser =  ((ParseUser)scoreList.get(0));		                    
		                     //Toast.makeText(CreateGathering.this,invitedUser.get("name").toString(),Toast.LENGTH_SHORT).show();
		                     gathering.add("users", invitedUser);
		    	             gathering.saveInBackground();
		    	             friendsList.append(invitedUser.get("name").toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		               

		                /*query.findInBackground(new FindCallback<ParseObject>() {
		                    public void done(List<ParseObject> scoreList, ParseException e) {
		                        if (e == null) {
		                            Log.d("User", "Retrieved " + scoreList.size() + " users");
		                            ParseUser invitedUser =  ((ParseUser)scoreList.get(0));		                    
		        	        		//Toast.makeText(CreateGathering.this,invitedUser.get("name").toString(),Toast.LENGTH_SHORT).show();
		                            gathering.add("users", invitedUser);
		        	                gathering.saveInBackground();
		        	                friendsList.append(invitedUser.get("name").toString());
	
		                        } else {
		                            Log.d("User", "Error: " + e.getMessage());
		                        }
		                    }
	                
	                
			
	                });*/
		                
	                }
	                TextView friends = (TextView) findViewById(R.id.friends);
                    friends.setText("Selected friends: " + friendsList.toString());

	        }
	        
	        if (resultCode == RESULT_CANCELED) {
                TextView friends = (TextView) findViewById(R.id.friends);
                friends.setText("Selected friends: " + friendsList.toString());

	            //Write your code if there's no result
	        }
	    }
	}
}
