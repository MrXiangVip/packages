package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TouchPanel extends Activity {
	private SharedPreferences mSp;
	private Handler mTouchHandler;
	private static final int RIGHT_MESSAGE = 0;
    private static final int WRONG_MESSAGE = 1;

	private class TouchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RIGHT_MESSAGE : {
					touchtestresult();
                    break;
                }
                case WRONG_MESSAGE : {
					touchtestresult();
                    break;
                }
            }
        }
    }

    protected void touchtestresult(){
        new AlertDialog.Builder(this).setTitle(R.string.title_activity_tp_test)
        .setPositiveButton(R.string.success, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
				setResult(Utils.SUCCESS_CODE);
				finish();
            }
        })
        .setNegativeButton(R.string.failed, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
				setResult( Utils.FAILED_CODE );
				finish();
            }
        })
        .create()
        .show();
	} 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		mTouchHandler = new TouchHandler();
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_GESTURE_ISOLATED);
		BlockView bv = new BlockView(this,getWindowManager().getDefaultDisplay().getHeight(),getWindowManager().getDefaultDisplay().getWidth());
		setContentView(bv);
	}
	
	private void successGoBack(){
		mTouchHandler.sendEmptyMessage(RIGHT_MESSAGE);
	}
	public class BlockView extends View {
		private static final String TAG = "BlockView";
		private List<Quadrangle> crossLayout = new ArrayList<Quadrangle>();
		private List<List<Quadrangle>> cellLayout = new ArrayList<List<Quadrangle>>();
		static final float MAX_VELOCITY = 8;
		private int crossLine = 22;
		private int crossVeticalLine = 9;
		private int cellLine = 17;
		private int cellVeticalLine = 9;
		private int screenHeight;
		private int screenWidth;
		private Point crossLeftLineLTPoint = new Point();
		private Point crossLeftLineRTPoint = new Point();
		private Point crossLeftLineLBPoint = new Point();
		private Point crossLeftLineRBPoint = new Point();
		private Path leftLine = new Path();
		private Point crossRightLineLTPoint = new Point();
		private Point crossRightLineRTPoint = new Point();
		private Point crossRightLineLBPoint = new Point();
		private Point crossRightLineRBPoint = new Point();
		private Path rightLine = new Path();
		private Paint redPaint = new Paint();
		private Paint whitePaint = new Paint();
		private Paint bluePaint = new Paint();
		private Paint greenPaint = new Paint();
		private Toast mToast;
		private VelocityTracker vt;
		/**
			* @param context
		*/
		public BlockView(Context context, int screenHeight, int screenWidth){
			super(context);
			this.screenHeight = screenHeight;
			this.screenWidth = screenWidth;
			Log.d(TAG, "screenHeight:" + screenHeight + ",screenWidth:" + screenWidth);
			initData();
			initPaint();
		}
		
		private void initPaint(){
			// 边线画笔
			redPaint.setAntiAlias(true);
			redPaint.setColor(Color.RED);
			redPaint.setStrokeWidth(1);
			redPaint.setStyle(Paint.Style.STROKE);
			// 白色填充画笔
			whitePaint.setAntiAlias(true);
			whitePaint.setColor(Color.WHITE);
			whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
			// 绿色点画笔
			bluePaint.setAntiAlias(true);
			bluePaint.setStrokeWidth(3);
			bluePaint.setColor(Color.BLUE);
			bluePaint.setStyle(Paint.Style.STROKE);
			// 绿色轨迹画笔
			greenPaint.setAntiAlias(true);
			greenPaint.setColor(Color.GREEN);
			greenPaint.setStyle(Paint.Style.STROKE);
		}
		
		private void initData(){
			int crossCellWidth = screenWidth / crossVeticalLine;
			int crossCellHeight = screenHeight / crossLine;
			// 左斜线的顶部 左边点和右边点
			crossLeftLineLTPoint.x = 0;
			crossLeftLineLTPoint.y = crossCellHeight;
			crossLeftLineRTPoint.x = crossCellWidth / 2;
			crossLeftLineRTPoint.y = 0;
			// 左斜线的底部 左边点和右边点
			crossLeftLineLBPoint.x = screenWidth - crossCellWidth / 2;
			crossLeftLineLBPoint.y = screenHeight;
			crossLeftLineRBPoint.x = screenWidth;
			crossLeftLineRBPoint.y = screenHeight - crossCellHeight;
			// 左斜线
			leftLine.moveTo(crossLeftLineLTPoint.x, crossLeftLineLTPoint.y);
			leftLine.lineTo(0, 0);
			leftLine.lineTo(crossLeftLineRTPoint.x, crossLeftLineRTPoint.y);
			leftLine.lineTo(crossLeftLineRBPoint.x, crossLeftLineRBPoint.y);
			leftLine.lineTo(screenWidth, screenHeight);
			leftLine.lineTo(crossLeftLineLBPoint.x, crossLeftLineLBPoint.y);
			leftLine.lineTo(crossLeftLineLTPoint.x, crossLeftLineLTPoint.y);
			
			// 右斜线的顶部 左边点和右边点
			crossRightLineLTPoint.x = screenWidth - crossCellWidth / 2;
			crossRightLineLTPoint.y = 0;
			crossRightLineRTPoint.x = screenWidth;
			crossRightLineRTPoint.y = crossCellHeight;
			// 右斜线的顶部 左边点和右边点
			crossRightLineLBPoint.x = 0;
			crossRightLineLBPoint.y = screenHeight - crossCellHeight;
			crossRightLineRBPoint.x = crossCellWidth / 2;
			crossRightLineRBPoint.y = screenHeight;
			
			// 右斜线
			rightLine.moveTo(crossRightLineLTPoint.x, crossRightLineLTPoint.y);
			rightLine.lineTo(screenWidth, 0);
			rightLine.lineTo(crossRightLineRTPoint.x, crossRightLineRTPoint.y);
			rightLine.lineTo(crossRightLineRBPoint.x, crossRightLineRBPoint.y);
			rightLine.lineTo(0, screenHeight);
			rightLine.lineTo(crossRightLineLBPoint.x, crossRightLineLBPoint.y);
			rightLine.lineTo(crossRightLineLTPoint.x, crossRightLineLTPoint.y);
			getCrossLayoutNodePoint(crossCellHeight);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawARGB(255, 0, 0, 0);
			if (cellIsShow) {
				int size = cellLayout.size();
				for (int i = 0; i < size; i++) {
					List<Quadrangle> line = cellLayout.get(i);
					for (int j = 0; j < line.size(); j++) {
						if (checkCellIsRemoved(i, j)) {
							continue;
						}
						Quadrangle q = line.get(j);
						p.reset();
						p.moveTo(q.left_top.x, q.left_top.y);
						p.lineTo(q.top_right.x, q.top_right.y);
						p.lineTo(q.right_bottom.x, q.right_bottom.y);
						p.lineTo(q.bottom_left.x, q.bottom_left.y);
						p.close();
						canvas.drawPath(p, whitePaint);
						canvas.drawPath(p, redPaint);
					}
				}
			} else {
				int size = crossLayout.size();
				for (int i = 0; i < size; i++){
					if (checkQuadIsRemoved(i)){
						continue;
					}
					Quadrangle q = crossLayout.get(i);
					p.reset();
					p.moveTo(q.left_top.x, q.left_top.y);
					p.lineTo(q.top_right.x, q.top_right.y);
					p.lineTo(q.right_bottom.x, q.right_bottom.y);
					p.lineTo(q.bottom_left.x, q.bottom_left.y);
					p.close();
					canvas.drawPath(p, whitePaint);
					canvas.drawPath(p, redPaint);
				}
			}
			int touchLastX = 0;
			int touchLastY = 0;
			
			Iterator<Integer> keys = touchPoints.keySet().iterator();
			while (keys.hasNext()) {
				List<Point> list = touchPoints.get(keys.next());
				int touchSize = list.size();
				for (int i = 0; i < touchSize; i++) {
					trackPoint = list.get(i);
					if (i == 0) {
						canvas.drawPoint(trackPoint.x, trackPoint.y, greenPaint);
					} else { 
						canvas.drawLine(touchLastX, touchLastY, trackPoint.x, trackPoint.y, greenPaint);
					}
					canvas.drawPoint(trackPoint.x,trackPoint.y,bluePaint);
					touchLastX = trackPoint.x;
					touchLastY = trackPoint.y;
				}
			}
		}
		
		private void toNextTest() {
			touchPoints.clear();
			int cellHeight = screenHeight / cellLine;
			int cellWidth = screenWidth / cellVeticalLine;
			getCellLayoutNodePoint(cellWidth, cellHeight);
			cellIsShow = true;
			invalidate();
		}
		boolean eliminateMode = false;
		boolean cellIsShow = false;
		Map<Integer, List<Point>> touchPoints = new HashMap<Integer, List<Point>>();
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int action = event.getAction();
			switch (action) {
				case MotionEvent.ACTION_DOWN:
					eliminateMode = true;
					if (cellIsShow) {
						checkPointInCell(x, y);
					} else {
						checkPointInQuad(x, y);
					}
					List<Point> newPoints = new ArrayList<Point>();
					int key = touchPoints.size();
					touchPoints.put(key, newPoints);
					newPoints.add(new Point(x, y));
					vt = VelocityTracker.obtain();
					vt.addMovement(event);
					invalidate();
					break;
					
				case MotionEvent.ACTION_MOVE:
					vt.addMovement(event);
					vt.computeCurrentVelocity(10);
					int xVe = (int) vt.getXVelocity();
					int yVe = (int) vt.getYVelocity();
					if(xVe>MAX_VELOCITY||yVe>MAX_VELOCITY){
						if(mToast!=null){
							mToast.cancel();
						}
						// mToast = Toast.makeText(getContext(),"蓝色点为TP返回点，请勿滑动过快。", Toast.LENGTH_SHORT);
						// mToast.show();
					}
					if (cellIsShow) {
						checkPointInCell(x, y);
					} else {
						checkPointInQuad(x, y);
					}
					touchPoints.get(touchPoints.size() - 1).add(new Point(x, y));
					invalidate();
					break;
					
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					eliminateMode = false;
					touchPoints.get(touchPoints.size() - 1).add(new Point(x, y));
					invalidate();
					if (!cellIsShow) {
						if (touchedQuad.size() == crossLayout.size()) {
							toNextTest();
						}
					} else {
						if(checkCellRemovedOver()){
							successGoBack();
						}
					}
					logOutData();
					vt.addMovement(event);
					vt.computeCurrentVelocity(10);
					int xVe2 = (int) vt.getXVelocity();
					int yVe2 = (int) vt.getYVelocity();
					if(xVe2>MAX_VELOCITY||yVe2>MAX_VELOCITY){
						if(mToast!=null){
							mToast.cancel();
						}
						// mToast = Toast.makeText(getContext(), "蓝色点为TP返回点，请勿滑动过快。", Toast.LENGTH_SHORT);
						// mToast.show();
					}
					break;
					
				default:
					break;
			}
			return true;
		}
		
		private boolean checkCellRemovedOver(){
			int touchedCellSize = touchedCell.size();
			int cellSize=0;
			for (int i = 0; i < cellLayout.size(); i++) {
				cellSize+=cellLayout.get(i).size();
			}
			if(touchedCellSize==cellSize){
				return true;
			}
			return false;
		}
		
		Path p = new Path();
		Region pRe = new Region();
		RectF pBounds = new RectF();
		Region standRe = new Region();
		Point trackPoint = new Point();
		List<Integer> touchedQuad = new ArrayList<Integer>();
		List<Cell> touchedCell = new ArrayList<Cell>();
		
		private boolean checkQuadIsRemoved(int position) {
			for (int i = 0; i < touchedQuad.size(); i++) {
				if (position == touchedQuad.get(i)) {
					return true;
				}
			}
			return false;
		}
		
		private boolean checkCellIsRemoved(int i, int j) {
			for (int h = 0; h < touchedCell.size(); h++) {
				Cell c = touchedCell.get(h);
				if (c.i == i && c.j == j) {
					return true;
				}
			}
			return false;
		}
		
		private void checkPointInQuad(int x, int y) {
			int size = crossLayout.size() / 2;
			for (int i = 0; i < size; i++) {
				Quadrangle lq = crossLayout.get(i);
				if (y < lq.bottom_left.y) {
					Quadrangle rq = crossLayout.get(size + i);
					if (!checkIsExitInMovedQuad(i)) {
						if (checkPointInGiveRegion(x, y, lq.left_top, lq.top_right, lq.right_bottom, lq.bottom_left)) {
							addPositionForQuad(i);
						}
					}
					if (!checkIsExitInMovedQuad(size + i)) {
						if (checkPointInGiveRegion(x, y, rq.left_top, rq.top_right, rq.right_bottom, rq.bottom_left)) {
							addPositionForQuad(size + i);
						}
					}
					return;
				}
			}
		}
		
		private void addPositionForQuad(int position) {
			for (int i = 0; i < touchedQuad.size(); i++) {
				if (position == touchedQuad.get(i)) {
					return;
				}
			}
			touchedQuad.add(position);
		}
		
		private void checkPointInCell(int x, int y) {
			int size = cellLayout.size();
			for (int i = 0; i < size; i++) {
				if (y < cellLayout.get(i).get(0).bottom_left.y) {
					List<Quadrangle> yContainList = cellLayout.get(i);
					for (int j = 0; j < yContainList.size(); j++) {
						if (isExitInMovedCell(i, j)) {
							continue;
						}
						Quadrangle q = yContainList.get(j);
						if (checkPointInGiveRegion(x, y, q.left_top, q.top_right, q.right_bottom, q.bottom_left)) {
							addTouchedCell(i, j);
						}
					}
					return;
				}
			}
		}
		
		private boolean isExitInMovedCell(int i, int j) {
			for (int h = 0; h < touchedCell.size(); h++) {
				Cell c = touchedCell.get(h);
				if (c.i == i && c.j == j) {
					return true;
				}
			}
			return false;
		}
		
		private void addTouchedCell(int i,int j){
			for (int h = 0; h < touchedCell.size(); h++) {
				if(touchedCell.get(h).i==i&&touchedCell.get(h).j==j){
					return ;
				}
			}
			touchedCell.add(new Cell(i, j));
		}
		
		private boolean checkPointInGiveRegion(int x, int y, Point tl, Point tr, Point br, Point bl) {
			p.reset();
			p.moveTo(tl.x, tl.y);
			p.lineTo(tr.x, tr.y);
			p.lineTo(br.x, br.y);
			p.lineTo(bl.x, bl.y);
			p.close();
			pBounds.setEmpty();
			p.computeBounds(pBounds, true);
			pRe.setEmpty();
			standRe.setEmpty();
			standRe.set((int) pBounds.left, (int) pBounds.top, (int) pBounds.right, (int) pBounds.bottom);
			pRe.setPath(p, standRe);
			return pRe.contains(x, y);
		}
		
		private boolean checkIsExitInMovedQuad(int position) {
			for (int i = 0; i < touchedQuad.size(); i++) {
				if (position == touchedQuad.get(i)) {
					return true;
				}
			}
			return false;
		}
		
		private void getCellLayoutNodePoint(int cellWidth, int cellHeight) {
			List<Point> nodeTList = new ArrayList<Point>();
			List<Point> nodeBList = new ArrayList<Point>();
			for (int i = 0; i <= cellLine; i++) {
				for (int j = 0; j <= cellVeticalLine; j++) {
					Point p = new Point(cellWidth * j, cellHeight * i);
					if (i == 0) {
						nodeTList.add(p);
					} else {
						nodeBList.add(p);
					}
				}
				if (i > 0) {
					List<Quadrangle> list = new ArrayList<Quadrangle>();
					for (int j = 0; j < (nodeBList.size() - 1); j++) {
						if (i == 2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 8 || i == 10 || i == 11 || i == 12 || i == 14 || i == 15 || i == 16
							|| i ==5 || i == 9 || i == 13) {
							if(j==1||j==3||j==5||j==7 || j==2||j==4||j==6){
								continue;
							}
						}
						Quadrangle q = new Quadrangle();
						q.left_top = nodeTList.get(j);
						q.top_right = nodeTList.get(j + 1);
						q.right_bottom = nodeBList.get(j + 1);
						q.bottom_left = nodeBList.get(j);
						list.add(q);
					}
					for (int j = 0; j < nodeBList.size(); j++) {
						nodeTList.set(j, nodeBList.get(j));
					}
					nodeBList.clear();
					cellLayout.add(list);
				}
			}
		}
		
		private void getCrossLayoutNodePoint(int cellHeight) {
			int mHeight = 0;
			Point[] listP = new Point[4];
			for (int i = 0; i <= crossLine; i++) {
				// 左斜线 交点
				if (i != 0 && i != 2 && i != 22 && i != 21) {
					mHeight += cellHeight + 1;
				} else if (i != 0) {
					mHeight += cellHeight;
				}
				Point leftLeftLinePoint = calcLineNode(mHeight, crossLeftLineLTPoint.x, crossLeftLineLTPoint.y, crossLeftLineLBPoint.x, crossLeftLineLBPoint.y);
				fixOutOfBoundPoint(leftLeftLinePoint);
				Point rightLeftLinePoint = calcLineNode(mHeight, crossLeftLineRTPoint.x, crossLeftLineRTPoint.y, crossLeftLineRBPoint.x, crossLeftLineRBPoint.y);
				fixOutOfBoundPoint(rightLeftLinePoint);
				if (i == 0) {
					listP[0] = leftLeftLinePoint;
					listP[1] = rightLeftLinePoint;
				} else if (i == 1) {
					listP[2] = leftLeftLinePoint;
					listP[3] = rightLeftLinePoint;
					Quadrangle leftQ = new Quadrangle();
					leftQ.left_top = listP[0];
					leftQ.top_right = listP[1];
					leftQ.right_bottom = listP[3];
					leftQ.bottom_left = listP[2];
					crossLayout.add(leftQ);
				} else {
					listP[0] = listP[2];
					listP[1] = listP[3];
					listP[2] = leftLeftLinePoint;
					listP[3] = rightLeftLinePoint;
					Quadrangle leftQ = new Quadrangle();
					leftQ.left_top = listP[0];
					leftQ.top_right = listP[1];
					leftQ.right_bottom = listP[3];
					leftQ.bottom_left = listP[2];
					crossLayout.add(leftQ);
				}
			}
			mHeight = 0;
			for (int i = 0; i <= crossLine; i++) {
				// 右斜线交点
				if (i != 0 && i != 2 && i != 22 && i != 21) {
					mHeight += cellHeight + 1;
				} else if (i != 0) {
					mHeight += cellHeight;
				}
				Point leftRightLinePoint = calcLineNode(mHeight, crossRightLineLTPoint.x, crossRightLineLTPoint.y, crossRightLineLBPoint.x, crossRightLineLBPoint.y);
				fixOutOfBoundPoint(leftRightLinePoint);
				Point rightRightLinePoint = calcLineNode(mHeight, crossRightLineRTPoint.x, crossRightLineRTPoint.y, crossRightLineRBPoint.x, crossRightLineRBPoint.y);
				fixOutOfBoundPoint(rightRightLinePoint);
				if (i == 0) {
					listP[0] = leftRightLinePoint;
					listP[1] = rightRightLinePoint;
				} else if (i == 1) {
					listP[2] = leftRightLinePoint;
					listP[3] = rightRightLinePoint;
					Quadrangle rightQ = new Quadrangle();
					rightQ.left_top = listP[0];
					rightQ.top_right = listP[1];
					rightQ.right_bottom = listP[3];
					rightQ.bottom_left = listP[2];
					crossLayout.add(rightQ);
				} else {
					listP[0] = listP[2];
					listP[1] = listP[3];
					listP[2] = leftRightLinePoint;
					listP[3] = rightRightLinePoint;
					Quadrangle rightQ = new Quadrangle();
					rightQ.left_top = listP[0];
					rightQ.top_right = listP[1];
					rightQ.right_bottom = listP[3];
					rightQ.bottom_left = listP[2];
					crossLayout.add(rightQ);
				}
			}
		}
		
		private void fixOutOfBoundPoint(Point p) {
			if (p.x < 0) {
				p.x = 0;
			}
			if (p.x > screenWidth) {
				p.x = screenWidth;
			}
		}
		
		private Point calcLineNode(int height, int x1, int y1, int x2, int y2) {
			Point node = new Point();
			node.y = height;
			node.x = ((x2 - x1) * (height - y1) + (y2 - y1) * x1) / (y2 - y1);
			int k = (y2 - y1) / (x2 - x1);
			int b = y1 - k * x1;
			return node;
		}
		
		public class Quadrangle {
			public Point left_top;
			public Point top_right;
			public Point right_bottom;
			public Point bottom_left;
			
			@Override
			public String toString() {
				return "(" + left_top + "," + top_right + "," + right_bottom + "," + bottom_left+")";
			}
		}
		
		public class Cell {
			public int i;
			public int j;
			public Cell(int i, int j) {
				this.i = i;
				this.j = j;
			}
			
			@Override
			public String toString() {
				return "("+i+","+j+")";
			}
		}
		
		private void logOutData(){
			for (int i = 0; i < touchPoints.size(); i++) {
				Log.d(TAG, "i:"+i+"--->point:"+touchPoints.get(i));
			}
			for (int i = 0; i < touchedCell.size(); i++) {
				Log.d(TAG, "i:"+i+"--->touchedCell:"+touchedCell.get(i));
			}
			for (int i = 0; i < touchedQuad.size(); i++) {
				Log.d(TAG, "i:"+i+"--->touchedQuad:"+touchedQuad.get(i));
			}
		}
	}

	// 拦截back键
	public void onBackPressed(){
		return;
	}

}
