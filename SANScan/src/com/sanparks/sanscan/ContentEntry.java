package com.sanparks.sanscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentEntry 
{
	public static List<CheckItem> ITEMS = new ArrayList<CheckItem>();

	public static Map<String, CheckItem> ITEM_MAP = new HashMap<String, CheckItem>();

	static 
	{
		addCheckItem(new CheckItem("1", "Vehicle"));
		addCheckItem(new CheckItem("2", "Driver"));
		addCheckItem(new CheckItem("3", "Passengers"));
	}

	public static void addCheckItem(CheckItem item) 
	{
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static class CheckItem 
	{
		public String id;
		public String content;

		public CheckItem(String id, String content) 
		{
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() 
		{
			return content;
		}
	}
}
