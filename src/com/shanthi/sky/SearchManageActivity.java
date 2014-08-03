package com.shanthi.sky;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

public class SearchManageActivity extends FragmentActivity {
	private ViewPager vpPager;
	private FragmentPagerAdapter adapterViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_manage);
		vpPager = (ViewPager) findViewById(R.id.vpPager);
		vpPager = (ViewPager) findViewById(R.id.vpPager);
		adapterViewPager = new PagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);
	
			
	}
	public static class PagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;

		public PagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				//return;// SearchResultFragment.newInstance(0, "Home");
			case 1: 
				//return;//ManagePostsFragment.newInstance(1, "Manage Posts");
			default:
				return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0)
				return "Home";
			else
				return "Manage Posts";
		}

	}
}
