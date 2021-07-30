# LEasyBaseLibrary基础库

# 我的理念概述
我的理念的是能用一行代码解决的是，绝对不用两行
其实编写依赖库的目的就是将一个功能尽可能的完善
代码调用方便，简洁

# 编写原因
这套代码已经应用应用在我自己的项目中，在原先的BaseLibrary中有提交，但是因为BaseLibray功能太多，因此把这个库单独拿出来再次使用
后期会逐步增加自定义View放进来

[Base基础库框架GitHub地址](https://github.com/freedomangelly/LEasyBaseLibrary)

# 框架环境的集成

```
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

 ```java
dependencies {
    // Base库：https://github.com/freedomangelly/LEasyBaseLibrary
    implementation 'com.github.freedomangelly:LEasyBaseLibrary:0.0.0.3'
}
```

# 功能说明
## Activity Fragment相关
# Interface介绍
![接口.png](https://upload-images.jianshu.io/upload_images/16249515-be09934c405ac921.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### ActivityAction
对Activity的操作进行接口封装

### AnimAction
对于常用动画进行了封装

### BundleAction
方便快速对Bundle进行put get处理

### ClickAction
对于点击事件，进行处理

### HandlerAction
对于Handle进程封装处理，便于快速的切换线程
在BaseActivity或者BaseFragment中有remove 此Handle的调用，因此不用担心内存泄漏

### KeyboardAction
对于键盘基本操作的封装

### ResourcesAction
快速获取资源文件

在BaseActivity 和BaseFragment中都会继承上面的Action，方便使用着快速的调用
将Activity，与Fragment的oncreate方法区按功能划分为以下方法
```java
    /**
     * 获取布局 ID
        再此方法中需要传入布局layoutId设置布局
     */
    protected abstract int getLayoutId();
    /**
    *根据view设置布局
    */
    protected abstract View getLayoutView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     *设置监听事件
     */
    protected abstract void addlistener();
    
    /**
    *延时加载（初始化非必要在onCreate中加载的数据）
    */
    protected abstract void initDelayedData();

```

## BaseDialog完成的dialog
dialog使用事例
```java
      //初始化dialog
        new BaseDialog.Builder<>(mContext)
    //设置dilaog布局
                .setContentView(R.layout.dialog_changeline_operate)
    //设置dialog动画样式
                .setAnimStyle(BaseDialog.ANIM_SCALE)
    //设置dialog动画样式
                .setGravity(Gravity.CENTER)
    //设置dialog布局中需要显示的文字
                .setText(R.id.tv_changelingoperate_callname, mContext.getString(R.string.strDialogchangellinetext2)+callManager.getDisplayName()+mContext.getString(R.string.strDialogchangellinetext3))
            //设置dialog点击事件
                .setOnClickListener(R.id.btn_changelingoperate_abandon, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
    //设置dialog点击事件
                .setOnClickListener(R.id.btn_changelingoperate_continue, (dialog, view) -> {
                    dialog.dismiss();
                    operation(operate);
                })
  //dialog的显示
                .show();
```

# IOC功能
可以仿照Butterknife的操作对控件进行初始化，但是与ButterKnife不同的事，我这个是通过反射机制实现不是使用apt
使用方法如下
```java
    @ViewById(R.id.tv)
    TextView tv;

    @OnClick(R.id.tv)
    public void doClick(View view){

    }
```


# 其他功能

### ExceptionCrashHandle 异常工具

      程序发生crash时，对crash的日志进行捕获，然后将日志保存至指定目录      
      使用方法
      在Application中进行初始化操作，就可以进行补货，但是需要注意的是此方法只能捕获主线程
      ExceptionCrashHandle.getInstance().init(this);


### BaseThead  线程基础类，代替原先的Thread
    集成ExceptionCrashHandle到此类中，调用此类可以捕获子线程中的crash异常
    不推荐使用此方式进行线程的初始化，不方便线程管理，如果使用的话仅限于IO操作使用
    
### Logutil
![image.png](https://upload-images.jianshu.io/upload_images/16249515-f34ab58ea962d31a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
格式如下
 [ 81: CallHelper.java: dialVoipPhoneHandle(): 304 ] msg : dial dialprecheck state=2,audio=0,media=0,number=9042

[ 线程ID：log所在文件：打印log的方法：log打印的位置（行）]  msg : 消息
