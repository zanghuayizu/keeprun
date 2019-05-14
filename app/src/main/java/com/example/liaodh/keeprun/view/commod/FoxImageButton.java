package com.example.liaodh.keeprun.view.commod;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class FoxImageButton extends AppCompatImageView {
    private boolean touchEffect = true;
    public final float[] BG_PRESSED = new float[] { 1, 0, 0, 0, -75, 0, 1,
            0, 0, -75, 0, 0, 1, 0, -75, 0, 0, 0, 1, 0 };
    public final float[] BG_NOT_PRESSED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

    public FoxImageButton(Context context) {
        super(context);
    }

    public FoxImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoxImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            this.setColorFilter( new ColorMatrixColorFilter(BG_PRESSED) ) ;
            this.getBackground().setColorFilter( new ColorMatrixColorFilter(BG_PRESSED) );
        }else{
            this.setColorFilter( new ColorMatrixColorFilter(BG_NOT_PRESSED) ) ;
            this.getBackground().setColorFilter(
                    new ColorMatrixColorFilter(BG_NOT_PRESSED));
        }
    }
}
