package com.example.mymusicplayer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.mymusicplayer.lyric.LyricActivity;

public class MusicList extends ListActivity {
	private MusicInfoController mMusicInfoController = null;
	private Cursor mCursor = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		MusicPlayerApp musicPlayerApp = (MusicPlayerApp) getApplication();
		mMusicInfoController = (musicPlayerApp).getMusicInfoController();

	}

	protected void onResume() {
		super.onResume();
		mCursor = mMusicInfoController.getAllSongs();

		ListAdapter adapter = new MusicListAdapter(this,
				android.R.layout.simple_expandable_list_item_2, mCursor,
				new String[] {}, new int[] {});
		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (mCursor == null || mCursor.getCount() == 0) {
			return;
		}
		mCursor.moveToPosition(position);
		String url = mCursor.getString(mCursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
		Intent intent = new Intent(MusicList.this, LyricActivity.class);
		intent.putExtra("musicurl", url);
		Log.d("lm", "url = " + url);
		MusicList.this.startActivity(intent);
	}
}

class MusicListAdapter extends SimpleCursorAdapter {

	public MusicListAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
	}

	public void bindView(View view, Context context, Cursor cursor) {

		super.bindView(view, context, cursor);

		TextView titleView = (TextView) view.findViewById(android.R.id.text1);
		TextView artistView = (TextView) view.findViewById(android.R.id.text2);

		titleView.setText(cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));

		artistView.setText(cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));

	}

	public static String makeTimeString(long milliSecs) {
		StringBuffer sb = new StringBuffer();
		long m = milliSecs / (60 * 1000);
		sb.append(m < 10 ? "0" + m : m);
		sb.append(":");
		long s = (milliSecs % (60 * 1000)) / 1000;
		sb.append(s < 10 ? "0" + s : s);
		return sb.toString();
	}
}
