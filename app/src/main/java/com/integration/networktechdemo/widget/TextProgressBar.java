package com.integration.networktechdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.integration.networktechdemo.utils.Utils;

/**
 * Created by Wongerfeng on 2019/8/15.
 */
public class TextProgressBar extends ProgressBar {

    private String progressText = "";
    private Paint mPaint;
    private int textSize;
    private int textColor = Color.WHITE;
    private Rect rect;

    public TextProgressBar(Context context) {
        this(context, null);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        textSize = Utils.dip2pixel(context, 15f);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(textSize);

    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgressText(String text) {
        progressText = text;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建绘制区域
        rect = new Rect();
        //获取绘制的文字的矩形区域
        mPaint.getTextBounds(progressText, 0, progressText.length(), rect);
        ///用View长度的一半，减去 文字部分长度的一半， 余下的就是文字部分的起始点
        //获取view中文字部分的左上角的横坐标数值
        int x = (getWidth() / 2) - rect.centerX();
        //获取左上角的纵坐标的值
        int y = (getHeight() / 2) - rect.centerY();
        //在画布指定的区域绘制进度条上的文字
        canvas.drawText(progressText, x, y, mPaint);

    }

    private int getTextSize() {
        return textSize;
    }

    private void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    private int getTextColor() {
        return textColor;
    }

    private void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
