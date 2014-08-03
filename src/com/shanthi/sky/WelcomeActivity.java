package com.shanthi.sky;

import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	// Declare Variable
    Button logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		 // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();
 
        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();
 
        // Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
 
        // Set the currentUser String into TextView
        txtuser.setText("You are logged in as " + struser);
 
            
	}
	
	public void onLogout(MenuItem mi) {
		Toast.makeText(this, "logging out", Toast.LENGTH_LONG).show();
            // Logout current user
            ParseUser.logOut();
            Intent intent = new Intent(
            		WelcomeActivity.this,
                    MainActivity.class);
            startActivity(intent);

	}
	public void onList(MenuItem mi){
		Toast.makeText(this, "logging out", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(
        		WelcomeActivity.this,
                SearchManageActivity.class);
        startActivity(intent);

	}
	public void onCreate(MenuItem mi){
		Toast.makeText(this, "logging out", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(
        		WelcomeActivity.this,
                CreatePostActivity.class);
        startActivity(intent);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_welcome, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
