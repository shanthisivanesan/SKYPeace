package com.shanthi.sky;

import com.shanthi.sky.dao.parse.ParsePost;
import com.shanthi.sky.dao.parse.ParseImages;
import com.shanthi.sky.dao.parse.ParseContact;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

public class SKYApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate(); 
		
		Parse.initialize(this, "Z7FaC8Xqj1LLy6cbnx2lAok4OkC6j8djQoGnGBzD", "IX5mrew8Hhska5EGKc9AJUAbNUaRQOEUwbnoZRVz");
		
		ParseObject.registerSubclass(ParsePost.class);
		ParseObject.registerSubclass(ParseContact.class);
		ParseObject.registerSubclass(ParseImages.class);
		PushService.setDefaultPushCallback(this, WelcomeActivity.class);
		// Add your initialization code here
		//ParseUser.enableAutomaticUser();
       // ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        //defaultACL.setPublicReadAccess(true);
 
        //ParseACL.setDefaultACL(defaultACL, true);
        
		
		
		//ParseInstallation.getCurrentInstallation().saveInBackground();
		if(ParseInstallation.getCurrentInstallation()==null){
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
		    @Override
		    public void done(ParseException e) {
		        if (e == null)
		            Log.d("SKY Application", "ParsePush: save installation ok");
		        else
		            Log.e("SKY Application", "ParsePush: save installation failed - " + e.getMessage());
		    }
		});

	}
  }
}
