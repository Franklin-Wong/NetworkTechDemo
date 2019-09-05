package com.integration.networktechdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.integration.networktechdemo.utils.Utils;

/**
 * Created by Wongerfeng on 2019/8/16.
 *
 */
@SuppressLint("DrawAllocation")
public class TextProgressCircle extends View {

    /**
     * 首先，圆形进度条，需要3种线条，背景、进度和文字
     */
    private Paint mPaintBack, mPaintFore, mPaintText;

    private int mLineWidth = 10;
    private int mTextSize;
    private int progress = 0;
    private int mTextColor = Color.GREEN;


    public TextProgressCircle(Context context) {
        this(context, null);
    }

    public TextProgressCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgressCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextSize = Utils.dip2pixel(context, 40);
        initPaint();
    }

    private void initPaint() {

        //背景笔
        mPaintBack = new Paint();
        mPaintBack.setStrokeWidth(mLineWidth);
        mPaintBack.setColor(Color.LTGRAY);
        mPaintBack.setStyle(Paint.Style.STROKE);
        mPaintBack.setAntiAlias(true);

        //前景笔
        mPaintFore = new Paint();
        mPaintFore.setStrokeWidth(mLineWidth);
        mPaintFore.setColor(Color.GREEN);
        mPaintFore.setStyle(Paint.Style.STROKE);
        mPaintFore.setAntiAlias(true);

        //文字笔
        mPaintText = new Paint();
        mPaintText.setTextSize(mTextSize);
        mPaintText.setColor(mTextColor);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取view 的 宽高
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        if (width <= 0 || height <= 0){
            return;
        }
        //取宽高中的最小值
        int demiter = Math.min(width, height);
        //
        RectF rect = new RectF((width - demiter) / 2 + mLineWidth, (height - demiter) / 2 + mLineWidth,
                (width + demiter) /2 - mLineWidth, (height + demiter) / 2 - mLineWidth);
        //绘制圆形背景
        canvas.drawArc(rect, 0, 360, false, mPaintBack);
        //绘制圆形前景
        canvas.drawArc(rect, 0, progress * 360 / 100, false, mPaintFore);
        //绘制文字
        String text = progress + "%";
        Rect rectText = new Rect();
        //获取文字绘制的区域
        mPaintText.getTextBounds(text, 0, text.length(), rectText);

        int x =  (getWidth() / 2) - rectText.centerX();
        int y = (getHeight() / 2) - rectText.centerY();

        canvas.drawText(text, x, y, mPaintText);
    }

    public void setProgress(int value, int textSize){
        //判断
        if (textSize > 0){
            mTextSize = textSize;
        }
        progress = value;
        //重新绘制
        invalidate();
    }

    public void setProgressStyle(int textColor, int lineWidth){
        if (lineWidth > 0){
            mLineWidth = lineWidth;
            mPaintFore.setStrokeWidth(mLineWidth);
        }
        if (textColor > 0){
            mTextColor = textColor;
            mPaintText.setColor(textColor);
        }

        invalidate();
    }

}
