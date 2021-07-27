package com.lightyear.leasybase.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
*AutoHideTextView
*自动隐藏的文本框
*author Light Year
*email 674919909@qq.com
* created 2021/7/27
*
*/
public class AutoHideTextView extends AppCompatTextView {
    public AutoHideTextView(Context context) {
        this(context,null);
    }

    public AutoHideTextView(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.textViewStyle);
    }

    public AutoHideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        // 判断当前有没有设置文本达到自动隐藏和显示的效果
        if (TextUtils.isEmpty(text) && getVisibility() != GONE) {
            setVisibility(GONE);
            return;
        }

        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }
}
