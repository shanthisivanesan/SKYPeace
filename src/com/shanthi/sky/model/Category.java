package com.shanthi.sky.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Category {
	MEDITATION("Meditation"), 
	INTEROSPECTION("Interospection"),
	SKYCENTERS("SKY Centers"),
	LECTURES("Lectures"),
	KAYAKALPA("Kaya Kalpa");

	private static final Map<String, Category> categories = new HashMap<String, Category>();
	static {
		for (Category c : Category.values()) {
			categories.put(c.categoryName, c);
		}
	}

	private final String categoryName;

	Category(String name) {
		this.categoryName = name;
	}

	public String toString() {
		return categoryName;
	}

	/**
	 * find the Category enum matching the given name
	 * 
	 * @param name
	 * @return
	 */
	public static Category fromName(String categoryName) {
		return categories.get(categoryName);
	}

	public static String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (Category c : Category.values()) {
			names.add(c.categoryName);
		}
		return names.toArray(new String[0]);
	}
}
