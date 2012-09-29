package com.genius.listen;

import java.util.Calendar;

import android.location.Location;
import android.util.Log;

public class MyUtil {

	private final static String TAG = "<-------MyUtil---------->";
	
	public static String formatLocation(Location location)
	{
		if (location == null)
		{
			return "location --> null";
		}
		
		

		
		String str = 	"provider = " + location.getProvider() + "\n" +
						", lon = " + location.getLongitude() + "\n" + 
						", lat = " + location.getLatitude() + "\n" + 
						", accuray = " + location.getAccuracy() + "\n" +
						", time = " + formatTimeMillis(location.getTime()) + "\n";
		
		
		
		return str;
	}
	
	
	 public static String formatTimeMillis(long time)
	 {
		 Calendar c = Calendar.getInstance(); 
		 c.setTimeInMillis(time);
			
		 int mHour = c.get(Calendar.HOUR_OF_DAY);//获取当前的小时数 
	     int mMinute = c.get(Calendar.MINUTE);//获取当前的分钟数   
	     int mSecond = c.get(Calendar.SECOND);	//获取当前的描述
	     String str = "hour = " + mHour + 
	     				", minute = " + mMinute + 
	     				", second = " + mSecond;
	     
	     return str;		 
	 }
	
	
	 public static void display(String str)
	 {
		 if (str != null)
		 {
			 Log.i(TAG, str);
		 }
	 }
}
