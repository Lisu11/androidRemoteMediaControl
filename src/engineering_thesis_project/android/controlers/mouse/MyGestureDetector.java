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
	private OnScrollGestureDetector detector;

	/**
	 * Custom method onTouch 
	 * wrote exclusively for OnScrollGestureDetector
	 * @param e2 current
	 * @return
	 */
	public boolean myOnTouchEvent(MotionEvent e2){
		return detector.onTouchEvent(e2);
	}

	/**
	 * Main Construcor 
	 * @param context context is needed to calculate width of scroll bar and detect gesture from that
	 * @param listener listener to which you want to send information
	 */
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
				return true;
			}
		});

		this.detector = new OnScrollGestureDetector(listener);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		startScroll = width - width/scrollSize;
	}

	/**
	 * Method tells if scroll bar gesture is enabled
	 * @return true is swipe on edge gesture is enabled false otherwise
	 */
	public static boolean isSwipeOnEdgeEnabled() {
		return swipeOnEdgeEnabled;
	}

	/**
	 * Method enable or disable detecting scroll bar gesture
	 * @param swipeOnEdgeEnabled true if you want to enable gesture false otherwise
	 */
	public static void setSwipeOnEdgeEnabled(boolean swipeOnEdgeEnabled) {
		MyGestureDetector.swipeOnEdgeEnabled = swipeOnEdgeEnabled;
	}

	/**
	 * Method returns width of area where scroll bar gesture is detected
	 * @return width of scroll bar
	 */
	public static int getScrollSize() {
		return scrollSize;
	}

	/**
	 * Method sets width of area where scroll bar gesture is detected
	 * @param scrollSize width of scroll bar
	 */
	public static void setScrollSize(int scrollSize) {
		MyGestureDetector.scrollSize = scrollSize;
	}

	/**
	 * Method tells if detection of single tap gesture is enabled
	 * @return true if detection of single tap gesture is enabled false otherwise
	 */
	public static boolean isSingleTapEnabled() {
		return singleTapEnabled;
	}

	/**
	 * Method enable or disable detection of single tap gesture
	 * @param singleTapEnabled true if you want to enable gesture false otherwise
	 */
	public static void setSingleTapEnable(boolean singleTapEnabled) {
		MyGestureDetector.singleTapEnabled = singleTapEnabled;
	}

	/**
	 * Custom onScroll gesture detector
	 * it detects only onSwipe gesture. I needed do this
	 * because default gestureListener not always invoke onScroll what is strange 
	 * @author lisu
	 *
	 */
	private class OnScrollGestureDetector{
		private OnTouchpadEventListener listener;
		private boolean myOnTouchFlag = false;
		private double Ex;
		private double Ey;
	
		/**
		 * Main Constructor 
		 * @param listener
		 */
		public OnScrollGestureDetector(OnTouchpadEventListener listener){
			this.listener = listener;
		}

		public boolean onTouchEvent(MotionEvent e2){
			if((e2.getAction() & MotionEvent.ACTION_MASK)== MotionEvent.ACTION_DOWN){
				if(myOnTouchFlag){
					return false;
				}
				Ex = e2.getX();
				Ey = e2.getY();
				myOnTouchFlag = true;
				return true;
			}else if((e2.getAction() & MotionEvent.ACTION_MASK)== MotionEvent.ACTION_MOVE){
				if(!myOnTouchFlag){
					return false;
				}
				double tempx = (e2.getX() - Ex)*e2.getXPrecision();
				double tempy = (e2.getY() - Ey)*e2.getYPrecision();

				Ex = e2.getX();
				Ey = e2.getY();
				return listener.onSwipe(tempx, tempy);
			} else {// motionEvent.action_up
				myOnTouchFlag = false;
			}
			return false;
		}
	}

}
