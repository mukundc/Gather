package com.example.gather;



import java.util.Arrays;
import java.util.List;


import com.example.gather.CreateGathering;
import com.example.gather.R;
import com.facebook.*;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.model.*;

import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity {

	private Button loginButton;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "uVxfiClzLcUQTzjmbgsxCBc2FnxwtIWNShjAYlDS", "DQGZDxAmQgF25azTspg0Jrw2Mc1LcViPOW3kctgi");
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		setContentView(R.layout.activity_main);

		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			showUserDetailsActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		MainActivity.this.progressDialog = ProgressDialog.show(
				MainActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("public_profile", "user_friends");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				MainActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d("Gather",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("Gather",
							"User signed up and logged in through Facebook!");
					showUserDetailsActivity();
				} else {
					Log.d("Gather",
							"User logged in through Facebook!");
					showUserDetailsActivity();
				}
			}
		});
	}

	private void showUserDetailsActivity() {
		Intent intent = new Intent(this, UserHome.class);
		startActivity(intent);
	}
}