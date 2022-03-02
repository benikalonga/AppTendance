package com.servo.apptendance.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.servo.apptendance.R;
import com.servo.apptendance.utils.Utils;

/**
 * Created by KADI on 18/03/2018.
 */

public class ViewBK extends View {

    Context context;
    Resources res;

    int RATIO_VITH_WIDTH = 1;
    int RATIO_VITH_HEIGHT = 2;

    public ViewBK(Context context){
        super(context);
        this.context = context;
        res = getResources();
        initAttrib(null,-1);
    }

    public ViewBK(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        res = getResources();
        initAttrib(attrs, -1);
    }

    public ViewBK(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
        res = getResources();
        initAttrib(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewBK(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        res = getResources();
        initAttrib(attrs, defStyleAttr);
    }
    private void initAttrib(AttributeSet attrs, int defStyleAttr){
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ViewBK, defStyleAttr, 0);
        int sizeH = attr.getInt(attr.getIndex(R.styleable.ViewBK_percent_heigth),-1);
        int sizeW = attr.getInt(attr.getIndex(R.styleable.ViewBK_percent_width),-1);
        int ratioValue = attr.getInt(attr.getIndex(R.styleable.ViewBK_ration_with),0);

        applyAttrib(attrs,sizeH, sizeW, ratioValue);
    }
    public void applyAttrib(AttributeSet attrs, int sizeH, int sizeW, int ratioValue){
        int screenHeight = Utils.getScreenHeight(context);
        int screenWidth = Utils.getScreenWidth(context);

        int scaledWidth = (int) (((float)screenWidth*(float)sizeW)/100f);
        int scaledHeight = (int) (((float)screenHeight*(float)sizeH)/100f);

        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp == null )
            lp = new ViewGroup.LayoutParams(context,attrs);

        if (ratioValue == RATIO_VITH_WIDTH){
            lp.width = lp.height = scaledWidth;
        }
        else if (ratioValue == RATIO_VITH_HEIGHT){
            lp.width = lp.height = scaledHeight;
        }
        else {
            lp.width = scaledWidth;
            lp.height = scaledHeight;
        }
        setLayoutParams(lp);
    }
}
