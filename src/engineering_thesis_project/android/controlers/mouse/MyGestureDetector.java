package engineering_thesis_project.android.controlers.mouse;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGestureDetector extends GestureDetector{

	private static boolean singleTapEnabled = true;
	private static boolean swipeOnEdgeEnabled = false;
	private static int width;
	private static int startScroll;
	private static int scrollSize = 10;
	
	
	
	public static int getScrollSize() {
		return scrollSize;
	}

	public static void setScrollSize(int scrollSize) {
		MyGestureDetector.scrollSize = scrollSize;
	}

	public static boolean isSingleTapEnabled() {
		return singleTapEnabled;
	}

	public static void setSingleTapEnable(boolean singleTapEnabled) {
		MyGestureDetector.singleTapEnabled = singleTapEnabled;
	}

	public MyGestureDetector(Context context, final OnTouchpadEventListener listener){
		super(context, new OnGestureListener() {
			double x, y;
			boolean scrollFlag = false; 
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				if(singleTapEnabled){
					listener.onLeftButtonPressed();
					return listener.onLeftButtonReleased();
				}
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {			
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				
				//return listener.onSwipe((e2.getX() - e1.getX()) * e1.getXPrecision(),
					//	(e2.getY() - e1.getY()) * e2.getYPrecision());
				double tempx = (e2.getX() - x)*e2.getXPrecision();
				double tempy = (e2.getY() - y)*e2.getYPrecision();
				x = e2.getX();
				y = e2.getY();
				if(scrollFlag && swipeOnEdgeEnabled){
					return listener.onWheelMoved((int)tempy/2);
				}

				return listener.onSwipe(tempx, tempy);
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				//Log.v("fling","X= "+(e2.getX() - e1.getX()) * e1.getXPrecision()+" Y= "+
				//		(e2.getY() - e1.getY()) * e2.getYPrecision());
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				x = e.getX();
				y = e.getY();

				if(x > (double)startScroll && x < (double)width){
					scrollFlag = true;
				} else {
					scrollFlag = false;
				}
					
				Log.v("onDown", "X= "+x+" y= "+y+"\n"+"width="+width+" scrStart="+startScroll);
				return false;
			}
		});

		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		startScroll = width - width/scrollSize;
	}

	public static boolean isSwipeOnEdgeEnabled() {
		return swipeOnEdgeEnabled;
	}

	public static void setSwipeOnEdgeEnabled(boolean swipeOnEdgeEnabled) {
		MyGestureDetector.swipeOnEdgeEnabled = swipeOnEdgeEnabled;
	}


}
