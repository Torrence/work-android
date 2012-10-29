package com.led.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class Demo3 extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo3);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.item1, new String[] { "title", "info", "img" },
				new int[] { R.id.title, R.id.info, R.id.img });
		setListAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "Contacts");
		map.put("info", "联系人");
		map.put("img", R.drawable.i1);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "Gallery");
		map.put("info", "图库");
		map.put("img", R.drawable.i2);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "Gmail");
		map.put("info", "邮箱");
		map.put("img", R.drawable.i3);
		list.add(map);

		return list;
	}
}
