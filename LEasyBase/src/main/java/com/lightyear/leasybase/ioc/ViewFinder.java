package com.lightyear.leasybase.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by freed on 2019/2/9.
    view的findbyid的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(View view) {
        this.mView=view;
    }

    public ViewFinder(Activity activity) {
        this.mActivity=activity;
    }

    public View findViewById(int viewId){
        return mActivity!=null
                ? mActivity.findViewById(viewId):
                mView.findViewById(viewId);
    }
}
