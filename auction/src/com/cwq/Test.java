package com.cwq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Date date = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
	      Date oldDate = format.parse("2010-12-19 12:16:00"); //这里时间可以自己定 
	      if(date.after(oldDate)) //判断,如果时间在这时间后,就执行后面操作 
	      { 
	          System.out.println("投票已经关闭!谢谢你的参与");
	      return ; 
	      } 
	      else{ 
	    	  System.out.print("继续访问"); 
	      } 


	}

}
