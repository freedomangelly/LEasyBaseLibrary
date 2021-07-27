# LEasyBaseLibrary
基础组件综合库合集


对于常用的Activity Fragment Dialog 经常会用的功能进行封装

#代码亮点
App 优化：对于App的基本操作进行封装，便于快速开发

代码规范：参照 Android SDK 、Support 源码和参考阿里巴巴的代码规范文档对代码进行命名，并对难点代码进行了注释，对重点代码进行了说明。

代码统一：对项目中常见的代码进行了封装，或是封装到基类中、或是封装到工具类中、或者封装到框架中，不追求过度封装，根据实际场景和代码维护性考虑，尽量保证同一个功能的代码在项目中不重复。


使用方法：
1.
Add it in your root build.gradle at the end of repositories:
将其添加到存储库末尾的root build.gradle中
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  2.添加依赖
  Add the dependency
  
   implementation 'com.github.freedomangelly:LEasyBaseLibrary:0.0.0.3'


###ActivityAction
对Activity的操作进行接口封装

###AnimAction
对于常用动画进行了封装

###BundleAction
方便快速对Bundle进行put get处理

###ClickAction
对于点击事件，进行处理

###HandlerAction
对于Handle进程封装处理，便于切换线程

###KeyboardAction
对于键盘基本操作的封装

###ResourcesAction
快速获取资源文件

#Base库的类使用
###BaseActivity BaseFragment
集成上述的Action功能
对Activity及Fragment的使用进行规范以及接口进行规范化的优化处理
在初始化的以及finish时候会隐藏软键盘，避免内存泄漏，提升用户体验
对ActivityForResult进行了优化，便于Activity之间交互

```
    /**
    *加载资源文件
    */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**不重要的初始化数据
      */
    protected abstract void initDelayedData();
```

###BaseDialog 万能dialog
对于除带有item列表的dialog除外，如果需要也可以集成进来，需要在此dialog上进行再次封装，因此不适宜放在Base库
直接加载dialog布局进行显示，
对于dialog显示的位置进行设置

万能dialog扩展MessageDialog 针对平治东方消息页面进行扩展
使用方法：
                    new MessageDialog.Builder(getActivity())
                            .setTitle("提示!")
                            .setMessage("文本基本测试发发发发发发付付付付付付付付付付付付付付付付付付付付付付付付付fff")
                            .setConfirm("接听")
                            .setCancel("拒绝")
                            .setCancelable(false)
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(BaseDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onCancel(BaseDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
 ```


###BaseSize
      平治东方定制类，用于根据系统字体大小进行调整

#其他功能

###ExceptionCrashHandle 异常工具

      程序发生crash时，对crash的日志进行捕获，然后将日志保存至指定目录      
      使用方法
      在Application中进行初始化操作，就可以进行补货，但是需要注意的是此方法只能捕获主线程
      ExceptionCrashHandle.getInstance().init(this);


###BaseThead  线程基础类，代替原先的Thread
    集成ExceptionCrashHandle到此类中，调用此类可以捕获子线程中的crash异常
    不推荐使用此方式进行线程的初始化，不方便线程管理，如果使用的话仅限于IO操作使用
    
###Logutil
![image.png](https://upload-images.jianshu.io/upload_images/16249515-f34ab58ea962d31a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
格式如下
 [ 81: CallHelper.java: dialVoipPhoneHandle(): 304 ] msg : dial dialprecheck state=2,audio=0,media=0,number=9042

[ 线程ID：log所在文件：打印log的方法：log打印的位置（行）]  msg : 消息
