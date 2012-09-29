package wyf.wpf;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class NewBallListener implements SensorEventListener{
	DriftBall father;		//DriftBall����
	int timeSpan = 500;		//500MS���һ��
	long startTime;			//��¼��ʼ��ʱ��
	int i = 0;
	
	public NewBallListener(DriftBall father){		//����������ʼ����Ա����
		this.father = father;
		startTime = System.currentTimeMillis();	//��¼ϵͳʱ����Ϊ��ǰʱ��
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		int sensor = event.sensor.getType();
		long now = System.currentTimeMillis();		//��ȡϵͳ��ǰʱ��
		if(now - startTime >= timeSpan){			//�ж��Ƿ��߹�ָ����ʱ����
			if(sensor == SensorManager.SENSOR_ORIENTATION){
				analysisData(event.values);		//����analysisData���������ݽ��з���
			}
			startTime = now;				//���¼�ʱ
		}
	}
	
	public void analysisData(float[] values) {		//�Զ�ȡ����̬���ݽ��з���������RoateUtil�ľ�̬��������������
		double[] valuesTemp=new double[]{values[0],values[1],values[2]};	//��y���������
		Log.d("lm", "values[0] = " + values[0]);
		Log.d("lm", "values[1] = " + values[1]);
		Log.d("lm", "values[2] = " + values[2]);
		Log.d("lm", "   \n");
		father.gv.direction = RotateUtil.getDirectionCase(valuesTemp);	//����RotateUtil�ľ�̬���������С����
	}

}
