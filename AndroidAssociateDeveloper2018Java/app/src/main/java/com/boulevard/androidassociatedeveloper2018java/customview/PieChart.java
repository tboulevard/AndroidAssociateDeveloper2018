package com.boulevard.androidassociatedeveloper2018java.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.boulevard.androidassociatedeveloper2018java.R;

public class PieChart extends View {

    public Boolean mShowText;
    public int mTextPos;

    /**
     * When a view is created from an XML layout, all of the attributes in the XML tag are read
     * from the resource bundle and passed into the view's constructor as an AttributeSet
     */
    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }

    /**
     * You must expose a property getter and setter pair for each custom attribute.
     */
    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
        requestLayout();
    }
}
