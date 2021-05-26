package com.colortu.colortu_module.colortu_base.core.api;

import android.app.Application;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @name : BaseApplicationApi
 * @Parameters :
 * @describe : 支持多个Module—Application共存，方便处理推送，IM等组件初始化问题；
 * 用法：module模块自定义的Application实现这个接口，然后把实现类的包名+类名写在下方常量中。
 * 加载过程：打开WDApplication代码，使用反射进行各个*Application的初始化
 */
public interface BaseApplicationApi {
    String[] MODULE_APP = new String[]{"", ""};

    void onCreate(Application application);
}
