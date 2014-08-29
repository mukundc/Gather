package com.example.gather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.parse.ParseException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import com.facebook.FacebookException;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CreateGathering2 extends ActionBarActivity {
	public static final String EXTRA_SELECTED_FRIENDS = "selected friends";

	private FriendPickerFragment friendPickerFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_create_gathering2);
	    Intent i1 = getIntent();
		final GatheringObject obj = (GatheringObject) i1.getSerializableExtra("gatheringObj");
	    Bundle args = getIntent().getExtras();
	    FragmentManager manager = getSupportFragmentManager();
	    Fragment fragmentToShow = null;

	    if (savedInstanceState == null) {
	        friendPickerFragment = new FriendPickerFragment(args);

	    } else {
	        friendPickerFragment = (FriendPickerFragment) manager.findFragmentById(R.id.picker_fragment);
	    }
	    // Set the listener to handle errors
	    friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
	        @Override
	        public void onError(PickerFragment<?> fragment, FacebookException error) {
	            Toast.makeText(CreateGathering2.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
	            setResult(RESULT_CANCELED);
	            finish();
	        }
	    });
	    

	    // Set the listener to handle button clicks
	    friendPickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
	        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
	        public void onDoneButtonClicked(PickerFragment<?> fragment) {
	            List<GraphUser> users = friendPickerFragment.getSelection();
	            if (users.size() > 0) {
	                ArrayList<String> usersID = new ArrayList<String>(users.size());
	                for (GraphUser user : users)	   
	                {
	                    usersID.add(user.getId());
	                }
	                

	                //final ParseRelation<ParseObject> relation = gathering.getRelation("members");

	                
	                Intent data = new Intent();
	                data.putStringArrayListExtra(EXTRA_SELECTED_FRIENDS, usersID);
	                setResult(RESULT_OK, data);
	               //CreateGathering2.this.getFragmentManager().popBackStack();
	              // setContentView(R.layout.activity_create_gathering2);

	                finish();
	            } else {
	                setResult(RESULT_CANCELED);
	                finish();
	            }
	        }
	    });
	    fragmentToShow = friendPickerFragment;
	    manager.beginTransaction().replace(R.id.picker_fragment, fragmentToShow).commit();
	}

	/*
	private void showfriendpicker()
    {
         FragmentManager manager = getSupportFragmentManager();
         Fragment fragmentToShow = null;
         Bundle args = getIntent().getExtras();
         friendPickerFragment = new FriendPickerFragment(args);
             // friendPickerFragment =  (FriendPickerFragment)
         manager.findFragmentById(R.id.picker_fragment);

    Fragment  fragment = manager.findFragmentById(R.id.picker_fragment);
                if (fragment == null) {

                    android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
                    ft.add(R.id.picker_fragment, friendPickerFragment);
                    Fragment  fragment1 = manager.findFragmentById(R.id.picker_fragment);
                   ft.show(fragment1);
                    ft.commit(); 
                     friendPickerFragment.loadData(false);
                    }

    } */

	@Override
	protected void onStart() {
	    super.onStart();
	    friendPickerFragment.loadData(false);

	}
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_gathering2);
		Intent i1 = getIntent();
		GatheringObject obj = (GatheringObject) i1.getSerializableExtra("gatheringObj");
		Toast.makeText(CreateGathering2.this,obj.getLocation(),Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_gathering2, menu);
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
	}*/
	
}
