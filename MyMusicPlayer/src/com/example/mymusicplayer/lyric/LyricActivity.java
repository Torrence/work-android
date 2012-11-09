package com.example.mymusicplayer.lyric;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.mymusicplayer.R;

public class LyricActivity extends Activity {
	/** Called when the activity is first created. */

	private TextView mnow_song;
	private Button mBtnPause;
	private TextView mNo_lyric;

	private MediaPlayer mMediaPlayer;

	private LrcRead mLrcRead;

	private LyricView mLyricView;

	private int index = 0;

	private int CurrentTime = 0;

	private int CountTime = 0;

	private List<LyricContent> LyricList = new ArrayList<LyricContent>();

	private String media_url = null;
	private String lyric_url = new String();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mnow_song = (TextView) findViewById(R.id.now_song);
		mNo_lyric = (TextView) findViewById(R.id.no_lyric);

		Intent intent = getIntent();
		media_url = intent.getStringExtra("musicurl");
		mnow_song.setText("当前正在播放：" + media_url);
		Log.d("lm", "media_url = " + media_url);
		String tmp[] = media_url.split("\\.");
		for (int i = 0; i < tmp.length - 1; i++) {
			lyric_url = lyric_url + tmp[i] + ".";
		}
		lyric_url = lyric_url + "lrc";
		Log.d("lm", "lyric_url = " + lyric_url);

		// 初始化
		init();
		
		File f = new File(lyric_url);
		if (f.exists()) {
			mNo_lyric.setVisibility(View.GONE);
			mLyricView.setVisibility(View.VISIBLE);
		}
		else {
			mNo_lyric.setVisibility(View.VISIBLE);
			mLyricView.setVisibility(View.GONE);
		}

		try {
			mLrcRead.Read(lyric_url);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LyricList = mLrcRead.GetLyricContent();

		// 设置歌词资源
		mLyricView.setSentenceEntities(LyricList);

		mHandler.post(mRunnable);

		for (int i = 0; i < mLrcRead.GetLyricContent().size(); i++) {
			System.out.println(mLrcRead.GetLyricContent().get(i).getLyricTime()
					+ "-");
			System.out.println(mLrcRead.GetLyricContent().get(i).getLyric()
					+ "----");
		}

		Play();
		mBtnPause.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Play();
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.pause();
					mBtnPause.setText("resume");
				} else {
					mMediaPlayer.start();
					mBtnPause.setText("pause");
				}

			}
		});
	}

	// 播放音乐
	public void Play() {

		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(media_url);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setLooping(true);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// 初始化
	public void init() {
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
		}

		mLrcRead = new LrcRead();

		mBtnPause = (Button) findViewById(R.id.play);

		mLyricView = (LyricView) findViewById(R.id.LyricShow);

	}

	Handler mHandler = new Handler();

	Runnable mRunnable = new Runnable() {
		public void run() {

			mLyricView.SetIndex(Index());

			mLyricView.invalidate();

			mHandler.postDelayed(mRunnable, 100);
		}
	};

	public int Index() {
		if (mMediaPlayer.isPlaying()) {
			CurrentTime = mMediaPlayer.getCurrentPosition();

			CountTime = mMediaPlayer.getDuration();
		}
		if (CurrentTime < CountTime) {

			for (int i = 0; i < LyricList.size(); i++) {
				if (i < LyricList.size() - 1) {
					if (CurrentTime < LyricList.get(i).getLyricTime() && i == 0) {
						index = i;
					}

					if (CurrentTime > LyricList.get(i).getLyricTime()
							&& CurrentTime < LyricList.get(i + 1)
									.getLyricTime()) {
						index = i;
					}
				}

				if (i == LyricList.size() - 1
						&& CurrentTime > LyricList.get(i).getLyricTime()) {
					index = i;
				}
			}
		}

		System.out.println(index);
		return index;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mMediaPlayer.stop();
	}

}