package com.bob.sqlitedatabaseexample;

import java.io.File;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQLiteDatabaseExampleActivity extends Activity {

	EditText nameEditText;
	EditText ageEditText;
	EditText selectEditText;
	Button addButton;
	Button selectButton;
	Button delButton;
	SQLiteDatabase database;
	private static String DATABASE_PATH = "/sdcard/SQLiteDatabaseExample.db";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        nameEditText = (EditText) findViewById(R.id.nameeditText);
        ageEditText = (EditText) findViewById(R.id.ageeditText);
        selectEditText = (EditText) findViewById(R.id.selecteditText);
        addButton = (Button) findViewById(R.id.addbut);
        selectButton = (Button) findViewById(R.id.selectbut);
        delButton = (Button) findViewById(R.id.delbut);
        
        if (new File(DATABASE_PATH).exists()) {
        	database = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
        }
        else {
        	database = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
        	database.execSQL("CREATE TABLE person(personid integer primary key autoincrement, name varchar(20), age integer)");
        }
        
        addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] addstr = new String[] {
					nameEditText.getText().toString().trim(),
					ageEditText.getText().toString().trim()
				};
				database.execSQL("insert into person(name, age) values(?,?)", addstr);
				Toast.makeText(SQLiteDatabaseExampleActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
			}
		});
        
        selectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor cursor = database.rawQuery("select * from person where name = ?", 
						new String[] {
						selectEditText.getText().toString().trim()
				});
				String selString = "";
				if (cursor.moveToFirst()) {
					selString += "id为：" + cursor.getString(cursor.getColumnIndex("personid")) + ";";
					selString += "姓名为：" + cursor.getString(cursor.getColumnIndex("name")) + ";";
					selString += "年龄为：" + cursor.getString(cursor.getColumnIndex("age")) + "";
					delButton.setVisibility(View.VISIBLE);
				}
				cursor.close();
				Toast.makeText(SQLiteDatabaseExampleActivity.this, selString, Toast.LENGTH_SHORT).show();
			}
		});
        
        delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				database.execSQL("delete from person where name = ?", new String[] {
						selectEditText.getText().toString().trim()
				});
				delButton.setVisibility(View.INVISIBLE);
				Toast.makeText(SQLiteDatabaseExampleActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mail, menu);
        menu.add(0, 0, 0, "退出");
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			database.close();
			this.finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
