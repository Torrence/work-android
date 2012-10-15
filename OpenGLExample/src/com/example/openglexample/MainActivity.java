package com.example.openglexample;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	Renderer render = new GLRender();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GLSurfaceView glView = new GLSurfaceView(this);

		glView.setRenderer(render);
		setContentView(glView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
