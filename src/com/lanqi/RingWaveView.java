package com.lanqi;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * ˮ����Ч��
 * @author lq
 *
 */
public class RingWaveView extends View {
	
	double DIS_SOLP=8;//2������֮��ľ���
	
	private ArrayList<Wave> wList;
	
	private boolean isRunning=false;
	
	public RingWaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		wList = new ArrayList<Wave>();
	
		//addPoint(280,480);
		//��ӵ�
	
	
		
	}
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			//ˢ������
			flushData();
			
			//ˢ��ҳ��
			invalidate();
			
			//ѭ������
			if(isRunning){
				
				//handler.sendEmptyMessageDelayed(0, 100);//��
				handler.sendEmptyMessageDelayed(0, 200);//��
				//handler.sendEmptyMessageDelayed(0, 500);//����ˢ��ʱ�䡣��
			}
			
		};
	};
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		for(int i = 0; i <wList.size();i++){
			Wave wave = wList.get(i);
			//Բ�ģ��뾶������
			canvas.drawCircle(wave.cx, wave.cy, wave.cr, wave.paint);
		}
	}
	//ˢ������
	protected void flushData() {
		for (int i = 0; i <wList.size(); i++) {
			
			Wave w = wList.get(i);
			
			//���͸����Ϊ 0 �Ӽ�����ɾ��
			int alpha = w.paint.getAlpha();
			//System.out.println("alpha"+alpha);
			if(alpha == 0){
				wList.remove(i);//ɾ��i �Ժ�i��ֵӦ���ټ�1 �����©��һ�����󣬲������ڴ˴�Ӱ�첻��Ч���Ͽ���������
				i--;
				continue;
			}
			
			//alpha-=4;
			alpha-=6;
			if(alpha<170){
				alpha-=30;
				if(alpha<30){
					alpha =0;//0����ȫ͸��
				}
			}

			//����͸����
			w.paint.setAlpha(alpha);
			
			//����뾶
			//w.cr = w.cr+3;
			w.cr = w.cr+0.5f;
			//����Բ�����
			//w.paint.setStrokeWidth(w.cr/4);
			w.paint.setStrokeWidth(w.cr/2.5f);
//			w.paint.setStrokeWidth(w.cr/4);
		}
		
		/*
		 * ������ϱ���գ���ֹͣˢ�¶���
		 */
		if(wList.size() == 0){
			isRunning = false;
			//handler.sendEmptyMessage(0);���Ǵ���
		}
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			
			int x = (int) event.getX();
			int y = (int) event.getY();	
			
			addPoint(x,y);
			
			break;
			}
		return true;//true�����Σ�false ��������
		//return false;
	}

	/**
	 * ����µĲ������ĵ�
	 * @param x
	 * @param y
	 */
	void addPoint(float x, float y) {
		if(wList.size()==0){
			addPointView(x,y);
			//��һ�δ�����Ļ����������
			isRunning = true;
			handler.sendEmptyMessage(0);
			
		}else if(wList.size()>0){
			Wave w = wList.get(wList.size()-1);
			
			if(Math.sqrt((w.cx - x)*(w.cx-x)+(w.cy-y)*(w.cy-y))>DIS_SOLP){
				addPointView(x,y);
//				isRunning = true;
//				handler.sendEmptyMessage(0);
			}
		}
		
	}
	/**
	 * ����µĲ���
	 * @param x
	 * @param y
	 */
	int i = 0;//��ɫ����
	 void addPointView(float x, float y) {
		Wave w = new Wave();
		w.cx = x;
		w.cy=y;
		Paint pa=new Paint();
		pa.setColor(colors[(int)(Math.random()*colors.length)]);//�����ɫ
		//pa.setColor(colors[i%colors.length]);//˳����ɫ
		i++;
		pa.setAntiAlias(true);
		pa.setStyle(Style.STROKE);

		w.paint = pa;
		
		wList.add(w);
		
	}
	//��ɫֵ
	private int [] colors = new int[]
			{Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.CYAN,Color.MAGENTA};
			//�죬�ƣ��̣��������̣����
	/**
	 * ����һ������
	 * @author lq
	 *
	 */
	class Wave {
		//Բ��
		float cx;
		float cy;
		//�뾶
		float cr;
		//����
		Paint paint;
	}
		
}
