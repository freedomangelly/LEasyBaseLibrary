package com.lightyear.leasybase.ui.baseComponent;


import com.lightyear.leasybase.exception.ExceptionCrashHandle;

/**
*BaseThread
*线程基类
*author Light Year
*email 674919909@qq.com
* created 2021/7/27
*
*/
public class BaseThread extends Thread{

    public BaseThread() {
        initThread();
    }

    public BaseThread(Runnable target) {
        super(target);
        initThread();
    }

    public BaseThread(ThreadGroup group, Runnable target) {
        super(group, target);
        initThread();
    }

    public BaseThread(String name) {
        super(name);
        initThread();
    }

    public BaseThread(ThreadGroup group, String name) {
        super(group, name);
        initThread();
    }

    public BaseThread(Runnable target, String name) {
        super(target, name);
        initThread();
    }

    public BaseThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        initThread();
    }

    public BaseThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        initThread();
    }

    private void initThread(){
        this.setUncaughtExceptionHandler(ExceptionCrashHandle.getInstance());
    }
}
