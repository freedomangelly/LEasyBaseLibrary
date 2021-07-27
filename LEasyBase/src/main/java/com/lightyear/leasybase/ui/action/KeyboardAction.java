package com.lightyear.leasybase.ui.action;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
*KeyboardAction
*键盘基础方法
*author Light Year
*email 674919909@qq.com
* created 2021/7/27
*
*/
public interface KeyboardAction {

    /**
     * 显示软键盘
     */
    default void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    default void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 切换软键盘
     */
    default void toggleSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.toggleSoftInput(0, 0);
        }
    }
}