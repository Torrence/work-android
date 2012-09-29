package wyf.wpf;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class NewBallListener implements SensorEventListener{
	DriftBall father;		//DriftBall引用
	int timeSpan = 500;		//500MS检查一次
	long startTime;			//记录开始的时间
	int i = 0;
	
	public NewBallListener(DriftBall father){		//构造器，初始化成员变量
		this.father = father;
		startTime = System.currentTimeMillis();	//记录系统时间作为当前时间
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		int sensor = event.sensor.getType();
		long now = System.currentTimeMillis();		//获取系统当前时间
		if(now - startTime >= timeSpan){			//判断是否走过指定的时间间隔
			if(sensor == SensorManager.SENSOR_ORIENTATION){
				analysisData(event.values);		//调用analysisData方法对数据进行分析
			}
			startTime = now;				//重新计时
		}
	}
	
	public void analysisData(float[] values) {		//对读取的姿态数据进行分析，调用RoateUtil的静态方法解析出方向
		double[] valuesTemp=new double[]{values[0],values[1],values[2]};	//对y轴进行修正
		Log.d("lm", "values[0] = " + values[0]);
		Log.d("lm", "values[1] = " + values[1]);
		Log.d("lm", "values[2] = " + values[2]);
		Log.d("lm", "   \n");
		father.gv.direction = RotateUtil.getDirectionCase(valuesTemp);	//调用RotateUtil的静态方法计算出小球方向
	}

}
