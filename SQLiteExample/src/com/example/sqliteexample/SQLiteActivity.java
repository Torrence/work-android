package com.example.sqliteexample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteActivity extends Activity implements OnClickListener {
	private static final int METHOD_ONE = 1;
	private static final int METHOD_TWO = 2;

	DatabaseHelper database;
	SQLiteDatabase db;
	
	Button btnInsert1, btnInsert2;
	Button btnDelete1, btnDelete2;
	Button btnUpdate1, btnUpdate2;
	Button btnQuery1, btnQuery2;
	TextView tv;
	String str = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);

		database = new DatabaseHelper(this);
		db = database.getReadableDatabase();
		
		btnInsert1 = (Button) findViewById(R.id.insert1);
		btnInsert2 = (Button) findViewById(R.id.insert2);
		btnDelete1 = (Button) findViewById(R.id.delete1);
		btnDelete2 = (Button) findViewById(R.id.delete2);
		btnUpdate1 = (Button) findViewById(R.id.update1);
		btnUpdate2 = (Button) findViewById(R.id.update2);
		btnQuery1 = (Button) findViewById(R.id.query1);
		btnQuery2 = (Button) findViewById(R.id.query2);
		tv = (TextView) findViewById(R.id.textview);
		
		btnInsert1.setOnClickListener(this);
		btnInsert2.setOnClickListener(this);
		btnDelete1.setOnClickListener(this);
		btnDelete2.setOnClickListener(this);
		btnUpdate1.setOnClickListener(this);
		btnUpdate2.setOnClickListener(this);
		btnQuery1.setOnClickListener(this);
		btnQuery2.setOnClickListener(this);
	}

	void insertData(int whichMethod) {
		switch (whichMethod) {
		case METHOD_ONE:// 使用insert方法
			ContentValues cv = new ContentValues();// 实例化一个ContentValues用来装载待插入的数据
			cv.put("username", "Jack Johnson1");// 添加用户名
			cv.put("password", "iLovePopMusic");// 添加密码
			db.insert("user", null, cv);
			break;
		case METHOD_TWO:// 使用execSQL方法来实现
			String sql = "insert into user(username,password) values ('Jack Johnson2','iLovePopMusic')";// 插入操作的SQL语句
			db.execSQL(sql);// 执行SQL语句
			break;
		}
	}

	void deleteData(int whichMethod) {
		switch (whichMethod) {
		case METHOD_ONE:// 使用delete方法
			String whereClause = "username=?";// 删除条件
			String[] whereArgs = { "Jack Johnson1" };// 删除的条件参数
			db.delete("user", whereClause, whereArgs);// 执行删除
			break;
		case METHOD_TWO:// 使用execSQL方式的实现
			String sql = "delete from user where username='Jack Johnson2'";// 删除操作的SQL语句
			db.execSQL(sql);// 执行删除操作
			break;
		}
	}

	void updateData(int whichMethod) {
		switch (whichMethod) {
		case METHOD_ONE:// 使用update方法
			ContentValues cv = new ContentValues();// 实例化
			cv.put("password", "iHatePopMusic");// 添加要更改的字段及内容
			String whereClause = "username=?";// 修改条件
			String[] whereArgs = { "Jack Johnson1" };// 修改条件的参数
			db.update("user", cv, whereClause, whereArgs);// 执行修改
			break;
		case METHOD_TWO:// 使用execSQL方式的实现
			String sql = "update [user] set password = 'iHatePopMusic' where username='Jack Johnson2'";// 修改的SQL语句
			db.execSQL(sql);// 执行修改
			break;
		}
	}

	void queryData(int whichMethod) {
		String password = "";
		switch (whichMethod) {
		case METHOD_ONE:// 通过query实现查询
			Cursor cursor = db.query("user", new String[] { "username",
					"password" }, "username=?",
					new String[] { "Jack Johnson1" }, null, null, null);
			if (cursor.moveToFirst()) {
				password = cursor.getString(cursor.getColumnIndex("password"));
			}
			break;
		case METHOD_TWO:// 通过rawQuery实现的带参数查询
			Cursor c = db.rawQuery("select * from user where username=?", new String[] {"Jack Johnson2"});
			if (c.moveToFirst()) {
				password = c.getString(c.getColumnIndex("password"));
			}
			break;
		}
		Toast.makeText(SQLiteActivity.this, "查询到的密码为:" + password, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sqlite, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.insert1:
			insertData(METHOD_ONE);
			str = "使用方法1插入数据";
			break;
		case R.id.insert2:
			insertData(METHOD_TWO);
			str = "使用方法2插入数据";
			break;
		case R.id.delete1:
			deleteData(METHOD_ONE);
			str = "使用方法1删除数据";
			break;
		case R.id.delete2:
			deleteData(METHOD_TWO);
			str = "使用方法2删除数据";
			break;
		case R.id.update1:
			updateData(METHOD_ONE);
			str = "使用方法1更新数据";
			break;
		case R.id.update2:
			updateData(METHOD_TWO);
			str = "使用方法2更新数据";
			break;
		case R.id.query1:
			queryData(METHOD_ONE);
			str = "使用方法1查询数据";
			break;
		case R.id.query2:
			queryData(METHOD_TWO);
			str = "使用方法2查询数据";
			break;
		}
		tv.setText(str);
	}
}
