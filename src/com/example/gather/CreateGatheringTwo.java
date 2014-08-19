package com.example.gather;

//import MainActivity;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.facebook.Request.GraphUserListCallback;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGatheringTwo extends ActionBarActivity {

	List<ParseObject> friendUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_gathering_two);
			 
		Session.openActiveSession(this, true, new Session.StatusCallback() {
		public void call(Session session, SessionState state, Exception exception) {
	    	if (session.isOpened()) {
	    		
	    	
	    							Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
			
						    		  // callback after Graph API response with user object
						    		  @Override
						    		  public void onCompleted(List<GraphUser> users, Response response) {
					    				  List<String> friendsList = new ArrayList<String>();
						    			  if (users != null) {
						    			      for (GraphUser user : users) {
						    			        friendsList.add(user.getId());
						    			        
						    			      }
						    			      // Construct a ParseUser query that will find friends whose
						    			      // facebook IDs are contained in the current user's friend list.
						    			      /*ParseQuery friendQuery = ParseUser.getQuery();
						    			      friendQuery.whereContainedIn("fbId", friendsList);
						    			      try {
								 					friendUsers = (List<ParseObject>) friendQuery.find();
								 					TextView friends = (TextView) findViewById(R.id.textView1);
								 					friends.setText(((ParseObject) friendUsers.get(0)).getString("name"));
								 			      } catch (ParseException | com.parse.ParseException e) {
								 					// TODO Auto-generated catch block
								 					e.printStackTrace();
								 				}*/
						    				}
						 					TextView friends = (TextView) findViewById(R.id.textView1);
						 					friends.setText(users.get(0).getName());	
						 						
						 					Button button = (Button) findViewById(R.id.next2);

								            button.setOnClickListener(new OnClickListener() {
								                @Override
								            	public void onClick(View v) {
								                	//Intent i = new Intent(CreateGathering.this, CreateGathering2.class);
								                	//startActivity(i);
								                    // TODO Auto-generated method stub
								                }
								            });
						    			
						    		  }

									
						    		}).executeAsync();
	    	}
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
}
