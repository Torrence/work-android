package com.led.view;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Demo2 extends Activity {
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo2);

		listView = (ListView) findViewById(R.id.listView2);
		String orderBy = PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null,
				orderBy);
		startManagingCursor(cursor);

		ListAdapter listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, cursor,
				new String[] { PhoneLookup.DISPLAY_NAME },
				new int[] { android.R.id.text1 });

		listView.setAdapter(listAdapter);
	}
}
