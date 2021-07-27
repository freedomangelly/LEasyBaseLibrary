package com.lightyear.leasybase.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by freed on 2019/2/9.
 */

public class ViewUtils {
    //目前使用的
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    //后期使用的
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    //后期使用的
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * @param finder
     * @param object
     */
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //获取类里面的所有属性
        Class clazz = object.getClass();
        Field[] fileds = clazz.getDeclaredFields();//获取所有属性包括共有私有
        //获取viewbyId中的value值
        for (Field field : fileds) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            //findviewbyid找到view
            if (viewById != null) {
                int viewId = viewById.value();//获取注解中的id值
                View view = finder.findViewById(viewId);
                //动态的注入找到的view
                try {
                    field.setAccessible(true);//能够注入所有修饰符
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注入事件
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //获取类里面的所有属性
        Class clazz = object.getClass();
        Method[] Methods = clazz.getDeclaredMethods();//获取所有属性包括共有私有
        //获取viewbyId中的value值
        for (Method method : Methods) {
            OnClick onClickId = method.getAnnotation(OnClick.class);
            //findviewbyid找到view
            if (onClickId != null) {
                int[] viewIds = onClickId.value();//获取注解中的id值
                for (int viewId : viewIds) {
                    View view = finder.findViewById(viewId);

                    boolean isCheckNet=method.getAnnotation(CheckNet.class) !=null;

                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }

        //viewbyid

        //反射执行方法
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object object;
        private Method method;
        private boolean isCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.object = object;
            this.method = method;
            this.isCheckNet=isCheckNet;
        }

        @Override
        public void onClick(View view) {
            if(isCheckNet){
                if(!netWorkAvailable(view.getContext())){
                    Toast.makeText(view.getContext(),"亲，您的网络不够给力", Toast.LENGTH_LONG).show();
                }
            }
            try {
                method.setAccessible(true);
                method.invoke(object, view);
            } catch (Exception e) {
                try {
//                    method.invoke(object, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static boolean netWorkAvailable(Context context){
        try {
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activityNetWorkInfo=connectivityManager.getActiveNetworkInfo();
            if(activityNetWorkInfo!=null&&activityNetWorkInfo.isConnected()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
