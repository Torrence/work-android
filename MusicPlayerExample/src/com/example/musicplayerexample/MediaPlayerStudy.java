package com.example.musicplayerexample;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPlayerStudy extends Activity {
	private Button bplay, bpause, bstop;
	private MediaPlayer mp = new MediaPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bplay = (Button) findViewById(R.id.play);
        bpause = (Button) findViewById(R.id.pause);
        bstop = (Button) findViewById(R.id.stop);
        bplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mp.setDataSource("/sdcard/test.mp3");
					mp.prepare();
					mp.start();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				mp.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mp.release();
					}
				});
			}
		});
        
        bpause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp != null) {
					mp.pause();
				}
			}
		});
        
        bstop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp != null) {
					mp.stop();
				}
			}
		});
    }

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	if (mp != null) {
    		mp.release();
    	}
		super.onDestroy();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
