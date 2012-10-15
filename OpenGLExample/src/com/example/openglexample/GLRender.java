package com.example.openglexample;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer {
	int one = 0x10000;

	// 三角形三个顶点
	private IntBuffer triggerBuffer = IntBuffer.wrap(new int[] { 0, one, 0, // 上顶点
			-one, -one, 0, // 左下点
			one, -one, 0, }); // 右下点
	// 正方形的4个顶点
	private IntBuffer quaterBuffer = IntBuffer.wrap(new int[] { one, one, 0,
			-one, one, 0, one, -one, 0, -one, -one, 0 });
	
	int triggerArray[] = {
			0, one, 0,
			-one, -one, 0,
			one, -one, 0
	};
	int quaterArray[] = {
			one, one, 0,
			-one, one, 0,
			one, -one, 0,
			-one, -one, 0
	};
	int colorBuffer[] = {
			one, 0, 0, one,
			0, one, 0, one,
			0, 0, one, one
	};
	float rotateTri;
	float rotateQuad;

	@Override
	public void onDrawFrame(GL10 gl) {
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);

		// 允许设置顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 设置三角形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(triggerArray));
		gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(colorBuffer));
		gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
		// 绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		
		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(1.5f, 0.0f, -6.0f);
		// 设置和绘制正方形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(quaterArray));
		gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		// 取消顶点设置
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		rotateTri += 0.5f;
		rotateQuad -= 0.5f;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		// 设置OpenGL场景的大小
		gl.glViewport(0, 0, width, height);
		// 设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// 选择模型观察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 重置模型观察矩阵
		gl.glLoadIdentity();

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);

		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);

		// 设置深度缓存
		gl.glClearDepthf(1.0f);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 所作深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);

		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	}
	
	public Buffer bufferUtil(int arr[]) {
		IntBuffer mBuffer;
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		qbb.order(ByteOrder.nativeOrder());
		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);
		return mBuffer;
	}

}
