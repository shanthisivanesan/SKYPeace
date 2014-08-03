package com.shanthi.sky.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shanthi.sky.R;
import com.shanthi.sky.adapter.PostArrayAdapter;
import com.shanthi.sky.model.GeoLocation;
import com.shanthi.sky.model.Post;

public abstract class BaseFragment extends Fragment{
	
	protected List<Post> posts;
	protected ArrayAdapter<Post> aPosts;
	protected ListView lvPosts;
	protected ProgressBar pbLoading;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		posts = new ArrayList<Post>();
		aPosts = new PostArrayAdapter(getActivity(), posts);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_eventlist, container, false);
		lvPosts = (ListView) view.findViewById(R.id.lvPosts);
		lvPosts.setAdapter(aPosts);
		pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);

		return view;
	}
	
	protected Boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

	GeoLocation getGeoFromAddress(String strAddress){
		Geocoder coder = new Geocoder(getActivity());
		List<Address> address;

		try {
		    address = coder.getFromLocationName(strAddress,1);
		    if (address == null) {
		        return null;
		    }
		    Address location = address.get(0);
		    Log.d("DEBUG", "## location: " + location.getLatitude() + ", " + location.getLongitude());

		    GeoLocation p = new GeoLocation();
		    p.setLatitude(location.getLatitude());
		    p.setLongitude(location.getLongitude());
		    return p;
		}
		catch (Exception e){
			Log.e("ERROR", e.getMessage());
		}
		return null;
	}
}
