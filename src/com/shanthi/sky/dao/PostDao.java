package com.shanthi.sky.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.shanthi.sky.dao.parse.ParseImages;
import com.shanthi.sky.dao.parse.ParsePost;
import com.shanthi.sky.model.Category;
import com.shanthi.sky.model.GeoLocation;
import com.shanthi.sky.model.Post;
import com.shanthi.sky.util.JsonUtil;
import com.google.gson.JsonObject;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

public class PostDao {
	private static PostDao postDao;
	public static PostDao getInstance(){
		if (postDao == null){
			postDao = new PostDao();
		}
		return postDao;
	}

	
	public void savePosts(List<Post> posts){
		for (Post post: posts){
			savePost(post);
		}
	}

	public void savePost(Post post) {
		ParsePost parsePost = new ParsePost(post);
		parsePost.saveInBackground();		
		//sendNotifications(post);
	}

	private void sendNotifications(Post post) {

		for (String keyword : post.getTitle().split("\\s+")){
			JSONObject data = new JSONObject();
			try {
				data.put("Events", JsonUtil.toJson(post));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ParsePush push = new ParsePush();
			push.setChannel(keyword);
			push.setData(data);
			push.sendInBackground();
		}
	}

	/**
	 * find a Post by its unique id
	 * @param uid
	 * @return
	 */
	public Post getPostById(String id){
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.class);
		try {
			ParsePost parsePost = query.get(id);
			return parsePost.toPost();
		} catch (ParseException e) {
			Log.e("ERROR", "Parse Exception", e);
		}
		return null;
	}

	public void deletePost(Post post){
		ParsePost parsePost = new ParsePost(post);
		parsePost.deleteInBackground();
	}

	public List<Post> getDummyPosts(){
		String postsData = 
				"[\n" + 
						"    {\n" + 
						"        \"userId\": \"1234567\",\n" + 
						"        \"title\": \"Silence \",\n" + 
						"        \"description\": \"Silence event is organized by Prof Meenamma for 2 hours on Sunday Morning August 2nd 8.30 AM to 10.30 AM.\",\n" + 
						"        \"contact\": {\n" + 
						"            \"phone\": \"408-203-5769\",\n" + 
						"            \"address\": \"Cupertino, CA\"\n" + 
						"        },\n" + 
						"        \"price\": \"0\",\n" + 
						"        \"category\": \"Meditation\",\n" + 
						"        \"status\": \"Active\",\n" + 
						"        \"location\": {\n" + 
						"            \"latitude\": 37,\n" + 
						"            \"longitude\": -122\n" + 
						"        },\n" + 
						"        \"createdAt\": 1404198000000\n" + 
						"    },\n"  +  
						"]";
		JSONArray list;
		List<Post> result = new ArrayList<Post>();
		try {
			list = new JSONArray(postsData);
			for (int i=0; i< list.length(); i++){
				JSONObject item = list.getJSONObject(i);
				Post post = (Post) JsonUtil.fromJson(item.toString(), Post.class);
				result.add(post);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


}
