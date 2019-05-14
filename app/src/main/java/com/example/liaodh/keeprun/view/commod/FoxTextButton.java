package com.example.liaodh.keeprun.view.commod;


import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class FoxTextButton extends AppCompatTextView {

    private boolean touchEffect = true;
    public final float[] BG_PRESSED = new float[] { 1, 0, 0, 0, -75, 0, 1,
            0, 0, -75, 0, 0, 1, 0, -75, 0, 0, 0, 1, 0 };
    public final float[] BG_NOT_PRESSED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

    public FoxTextButton(Context context) {
        super(context);
    }

    public FoxTextButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FoxTextButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPressed(boolean pressed) {
        updateView(pressed);
        super.setPressed(pressed);
    }

    private void updateView(boolean pressed){
        if( !touchEffect ){
            return;
        }
        if( pressed ){
            this.setDrawingCacheEnabled(true);
            this.getBackground().setColorFilter( new ColorMatrixColorFilter(BG_PRESSED) );
        }else{
            this.getBackground().setColorFilter(
                    new ColorMatrixColorFilter(BG_NOT_PRESSED));
        }
    }

}