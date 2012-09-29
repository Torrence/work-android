package com.hello.jnitest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Jnitest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnitest);
        Nadd cal = new Nadd();
        setTitle("The Native Add Result is " + String.valueOf(cal.nadd(10, 19)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_jnitest, menu);
        return true;
    }
}
