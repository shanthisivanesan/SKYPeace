package com.shanthi.sky;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.shanthi.sky.util.JsonUtil;
import com.shanthi.sky.dao.PostDao;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shanthi.sky.model.Contact;
import com.shanthi.sky.model.Post;
import com.shanthi.sky.model.Category;
import com.shanthi.sky.model.GeoLocation;

public class CreatePostActivity extends BaseActivity {
	private Post post;
	private Integer position;
	private Spinner spinner;
	private TextView title;
	private TextView description;
	private TextView location;
	private TextView price;
	private TextView phone;
	private TextView tvUploading;
	private ImageView ivLocate;
	private String userId;
	private SharedPreferences prefs;
	private PostDao postDao = PostDao.getInstance();
	private GeoLocation geoLocation;
	private Location lastKnownLocation;
	private boolean isNewPost = false;
	private Geocoder geoCoder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_create_post);
		lastKnownLocation = getIntent().getParcelableExtra("location");
		geoCoder = new Geocoder(this);
		
		prefs = getSharedPreferences("com.codepath.yardsale", Context.MODE_PRIVATE);
		userId = prefs.getString("userId", "");
		if (userId.isEmpty()){
			userId = UUID.randomUUID().toString();
			prefs.edit().putString("userId", userId).commit();
		}
		
		setUpViews();
		Toast.makeText(this, "userid", Toast.LENGTH_LONG).show();
		setUpViews();
		// Create an ArrayAdapter using the string array and a default spinner layout
		String[] categoryNames = Category.getNames();
		Arrays.sort(categoryNames);
		List<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList(categoryNames));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		String locationJson = getIntent().getStringExtra("geo_location");
		fetchPostData();
		if (!isNewPost){
			getActionBar().setTitle("Edit Event");
			populateData();
		}
		
	}
	public void setUpViews() {
		spinner = (Spinner) findViewById(R.id.spCategory);
		title = (TextView) findViewById(R.id.etTitle);
		description = (TextView) findViewById(R.id.etDescription);
		location = (TextView) findViewById(R.id.etLocation);
		price = (TextView) findViewById(R.id.etPrice);
		phone = (TextView) findViewById(R.id.etPhone);
		tvUploading = (TextView) findViewById(R.id.tvUploading);
		ivLocate = (ImageView) findViewById(R.id.ivLocate);
	}
	
	public void populateData()
	{
		String category = post.getCategory().toString();
		spinner.setSelection(getIndex(spinner, category));
		
		title.setText(post.getTitle());
		description.setText(post.getDescription());
		location.setText(post.getContact().getAddress());
		Double  dValue= post.getPrice();

		if(!dValue.isNaN())
		{
			price.setText(dValue.toString());
		} 
		
		phone.setText(post.getContact().getPhone());
		
		
	}
	//Get Spinner Index position
	 
	private int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(myString)) {
				index = i;
			}
		}
		// Toast.makeText(this, "Read post: " +
		// index,Toast.LENGTH_SHORT).show();
		return index;

	}
	
	public void onSave(MenuItem mi) {
		Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
		savePost();
		
		// Prepare data intent
		Intent data = new Intent();
		// Pass relevant data back as a result
		if (post.getTitle() != null && !post.getTitle().isEmpty()){
			data.putExtra("post", JsonUtil.toJson(post));
			data.putExtra("position", position);
		}
		data.putExtra("action", "save");
		
		overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent

	}
	
	private void savePost(){
		if (title.getText().toString().isEmpty()){
			//TODO: show alert dialog
			return;
		}
		//check if the ad is an edit or new
		post.setUserId(userId);
		post.setCategory(Category.fromName(spinner.getSelectedItem().toString()));
		Contact contact = new Contact(phone.getText().toString(), location.getText().toString());
		post.setContact(contact);
		post.setTitle(title.getText().toString());
		post.setDescription(description.getText().toString());
		post.setPrice(Double.valueOf(price.getText().toString()));
		Date date = new Date();
		
		post.setCreatedAt((new Timestamp(date.getTime())).getTime());
		post.setStatus("Active");
		String locationStr = location.getText().toString();
		if (locationStr != null && !locationStr.isEmpty()){
			geoLocation = getGeoFromAddress(contact);
		}
		post.setLocation(geoLocation);
		
		postDao.savePost(post);
	}
	
	public void onCancel(MenuItem mi){
		Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(
				CreatePostActivity.this,
				SearchManageActivity.class);
        startActivity(intent);

	}
	
	public void onBack(MenuItem mi){
		Toast.makeText(this, "back to main", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(
				CreatePostActivity.this,
				WelcomeActivity.class);
        startActivity(intent);

	}
	
	public void fetchPostData(){
		//Existing ad
		String postJson = getIntent().getStringExtra("post");
		position = getIntent().getIntExtra("position", -1);
		if (postJson == null || postJson.isEmpty())
		{
			post = new Post();
			isNewPost = true;
			//gallery.setVisibility(View.GONE);
		}
		else{
			isNewPost = false;
			post = (Post) JsonUtil.fromJson(postJson, Post.class);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_post, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
