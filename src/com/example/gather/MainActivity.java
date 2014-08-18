package com.example.gather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facebook.*;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.model.*;

import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

public class MainActivity extends ActionBarActivity {
	List<ParseObject> friendUsers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "uVxfiClzLcUQTzjmbgsxCBc2FnxwtIWNShjAYlDS", "DQGZDxAmQgF25azTspg0Jrw2Mc1LcViPOW3kctgi");
		setContentView(R.layout.activity_main);
		// start Facebook Login
		ParseFacebookUtils.initialize(getString(R.string.app_id));
		List<String> permissions = Arrays.asList("public_profile", "user_friends");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			      getFacebookIdInBackground();
			    } else {
			      Log.d("MyApp", "User logged in through Facebook!");
			      getFacebookIdInBackground();
			    }
			  }
			});
		
		Session.openActiveSession(this, true, new Session.StatusCallback() {

		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	if (session.isOpened()) {
		    		// make request to the /me API
		    		Request.newMeRequest(session, new Request.GraphUserCallback() {

		    		  // callback after Graph API response with user object
		    		  @Override
		    		  public void onCompleted(GraphUser user, Response response) {
		    			  if (user != null) {
		    				  TextView welcome = (TextView) findViewById(R.id.welcome);
		    				  welcome.setText("Hello " + user.getName() + "!");
		    				}
		    		  }
		    		}).executeAsync();
		    		
		    		Button button = (Button) findViewById(R.id.creategathering);

		            button.setOnClickListener(new OnClickListener() {
		                @Override
		            	public void onClick(View v) {
		                	Intent i = new Intent(MainActivity.this, CreateGathering.class);
		                	startActivity(i);
		                    // TODO Auto-generated method stub
		                }
		            });
		    		
		            //Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
/*
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
			    			      ParseQuery friendQuery = ParseUser.getQuery();
			    			      friendQuery.whereContainedIn("fbId", friendsList);
			    				}
			 					TextView friends = (TextView) findViewById(R.id.textView1);
			 					friends.setText(users.get(0).getName());			    			  
			    			  try {
			 					friendUsers = (List<ParseObject>) friendQuery.find();
			 					TextView friends = (TextView) findViewById(R.id.textView1);
			 					friends.setText(((ParseObject) friendUsers.get(0)).getString("name"));
			 			      } catch (ParseException e) {
			 					// TODO Auto-generated catch block
			 					e.printStackTrace();
			 				}
			    		  }
			    		}).executeAsync();*/
		    }
		    }
		}); 
		
		
			
	}

	@SuppressWarnings("deprecation")
	private static void getFacebookIdInBackground() {
		  Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
		    @Override
		    public void onCompleted(GraphUser user, Response response) {
		      if (user != null) {
		        ParseUser.getCurrentUser().put("fbId", user.getId());
		        ParseUser.getCurrentUser().put("name", user.getName());
		        ParseUser.getCurrentUser().saveInBackground();
		      }
		    }
		  });
		}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
