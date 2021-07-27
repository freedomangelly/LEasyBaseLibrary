package com.lightyear.leasybase.exception;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * author: freed on 2019/6/30
 * email: 674919909@qq.com
 * version: 1.0
 */
public class ExceptionCrashHandle implements Thread.UncaughtExceptionHandler{
    private static final String TAG="ExceptionCrashHandle";
    private Context mContext;
    private static ExceptionCrashHandle mInstance;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    private ExceptionCrashCallback exceptionCrashCallback;




    public static ExceptionCrashHandle getInstance(){
        if(mInstance==null){
            synchronized (ExceptionCrashHandle.class){//解决多并发的问题
                mInstance=new ExceptionCrashHandle();
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext=context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultExceptionHandler= Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //全局异常
        Log.e(TAG,"Exception");

        //写入到本地文件 ex 当前的版本 手机信息

        //崩溃信息

        //应用信息，包名，版本号

        //手机信息
        if(exceptionCrashCallback!=null){
            exceptionCrashCallback.callBack();
        }
        //保存当前文,等应用再次启动上传
        String crashFileName=saveInfoToSd(throwable);
        Log.e(TAG,crashFileName);
        cacheCrashFile(crashFileName);
        throwable.printStackTrace();

        //报错直接闪退
//        mDefaultExceptionHandler.uncaughtException(thread,throwable);//注释此代码不会报错

        android.os.Process.killProcess(android.os.Process.myPid()); //直接进入闪退
    }

    /**
     * 缓存崩溃日志文件
     * @param fileName
     */
    private void cacheCrashFile(String fileName){
        SharedPreferences sp=mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME",fileName).commit();
    }

    public File getCrashFile(){
        String crashFileName=mContext.getSharedPreferences("crash", Context.MODE_PRIVATE).getString("CRASH_FILE_NAME","");
        return new File(crashFileName);
    }

    public String saveInfoToSd(Throwable ex){
        String fileName=null;
        StringBuffer sb=new StringBuffer();
        for (Map.Entry<String, String> entry:obtainSimpleInfo(mContext).entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        sb.append(obtainExceptionInfo(ex));

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir=new File(File.separator+"data"+ File.separator+"logs"+ File.separator);
            if(dir.exists()){
                deleteDir(dir);
            }else {
                dir.mkdirs();
            }

            try {
                fileName = dir.toString()+ File.separator+getAssignTime("yyyy_MM_dd_HH_mm_ss")+".txt";
                FileOutputStream fos=new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return fileName;
    }

    private boolean deleteDir(File dir) {
        if(dir==null||!dir.exists()){
            return false;
        }
        if(dir.isDirectory()){
            String[] children = dir.list();

            for(int i = 0;i<children.length;i++){
                boolean success=deleteDir(new File(dir,children[i]));
                if(!success){
                    return false;
                }
            }
        }else {
            dir.delete();
        }
        return true;
    }

    private HashMap<String, String> obtainSimpleInfo(Context mContext) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager packageManager=mContext.getPackageManager();
        PackageInfo mPackageInfo=null;
        try {
            mPackageInfo=packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("versionName",mPackageInfo.versionName);
        map.put("versionCode",""+mPackageInfo.versionCode);
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT",""+ Build.VERSION.SDK_INT);
        map.put("PRODUCT", Build.PRODUCT);
        map.put("MOBLE_INFO",getMobileInfo());
        return map;
    }

    private String getMobileInfo() {
        StringBuffer sb=new StringBuffer();
        try {
            Field[] fields= Build.class.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                String name=field.getName();
                String value=field.get(null).toString();
                sb.append(name+"="+value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getAssignTime(String dateFormatStr) {
        DateFormat dateFormat=new SimpleDateFormat(dateFormatStr);
        long currentTime= System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }

    private String obtainExceptionInfo(Throwable throwable){
        StringWriter stringWriter=new StringWriter();
        PrintWriter printWriter=new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    public void setExceptionCrashCallback(ExceptionCrashCallback exceptionCrashCallback) {
        this.exceptionCrashCallback = exceptionCrashCallback;
    }
}
