package com.example.liaodh.keeprun.view.commod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.liaodh.keeprun.R;

/**
 * This view implements the drawing canvas.
 * <p/>
 * It handles all of the input events and drawing functions.
 * 签名版
 */
public class PaintView extends View {
    private Paint paint;
    private Canvas cacheCanvas;
    private Bitmap cachebBitmap;
    private Path path;
    private OnMoveLisener lisener;


    public void setSize(int width,int height,OnMoveLisener lisener) {
        this.lisener=lisener;
        init(width,height);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init(0,0);
    }

    public Bitmap getCachebBitmap() {
        return cachebBitmap;
    }

    private void init(int width,int height) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ffffff"));
        path = new Path();
        cachebBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cachebBitmap);
        cacheCanvas.drawColor(Color.parseColor("#444444"));
    }

    public void clear() {
        if (cacheCanvas != null) {
            paint.setColor(Color.parseColor("#444444"));
            cacheCanvas.drawPaint(paint);
            paint.setColor(Color.parseColor("#ffffff"));
            invalidate();
        }
    }

    public void recycle(){
        paint.reset();
        cachebBitmap.recycle();
        path.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(BRUSH_COLOR);
        canvas.drawBitmap(cachebBitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
        int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }

        if (curW < w)
            curW = w;
        if (curH < h)
            curH = h;

        Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (cachebBitmap != null) {
            newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
        }
        cachebBitmap = newBitmap;
        cacheCanvas = newCanvas;
    }

    private float cur_x, cur_y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        getParent().requestDisallowInterceptTouchEvent(true);// 通知父控件勿拦截本控件touch事件

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                cur_x = x;
                cur_y = y;
                path.moveTo(cur_x, cur_y);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                path.quadTo(cur_x, cur_y, x, y);
                cur_x = x;
                cur_y = y;
                lisener.hideWords();//隐藏提醒的文字
                break;
            }

            case MotionEvent.ACTION_UP: {
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
            }
        }

        invalidate();

        return true;
    }


    public interface OnMoveLisener{
        void hideWords();//主界面回调后隐藏提示文字
    }
}
