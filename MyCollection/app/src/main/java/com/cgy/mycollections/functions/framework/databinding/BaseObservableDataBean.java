package com.cgy.mycollections.functions.framework.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.cgy.mycollections.BR;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/9/28 14:16
 * 修改日期: 2020/9/28 14:16
 * 类说明：
 */
public class BaseObservableDataBean extends BaseObservable {
    @Bindable //使用了这个才能生成 BR.testName  也可以用于方法
    public String testName;//必须用public 不然xml中找不到
    public String testContent;

    //使用单向绑定刷新UI第二种 ObservableField 使用这个不需要继承BaseObservable
    public final ObservableField<String> obFiled;
    public final ObservableField<String> inputData;

    BaseObservableDataBean(ObservableField<String> obFiled, ObservableField<String> inputData) {
        this.obFiled = obFiled;
        this.inputData = inputData;
    }

    public void setTestName(String testName) {
        this.testName = testName;
        this.testContent = "我是不会修改的";//不会生效
        notifyPropertyChanged(BR.testName);//只修改其中的一个ui
    }

    public void changeAll(String testName, String testContent) {
        this.testName = testName;
        this.testContent = testContent;
        notifyChange();//使用这个不需要搭配 @Bindable 就可以都改变
    }
}
