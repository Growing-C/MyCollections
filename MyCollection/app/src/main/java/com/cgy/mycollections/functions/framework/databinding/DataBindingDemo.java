package com.cgy.mycollections.functions.framework.databinding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.R;
import com.cgy.mycollections.databinding.MyBinding;

import appframe.utils.L;

public class DataBindingDemo extends AppCompatActivity {
    Demo demo;
    BaseObservableDataBean dataBean;
    ObservableList<String> dataList;
    MyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_demo);

        //所有的数据类不应该引用activity，引用应该是单向的
        testSingleBinding();

        testBidirectionalBinding();

//        binding.setLifecycleOwner(this); //这是和LiveData搭配使用的
    }

    //当xml中使用到BindingAdapter中的属性的时候都会调用这个方法
    //如果使用 android:onClick之类的系统自带的也会走这里
    //如果自定义如imageUrl,就可以直接在xml中使用 imageUrl来定义一些东西，会自动根据参数类型走到这个方法中来，
    //需要注意的是如
    //   android:onClick="@{()->listener.onBaseObservableClick()}"
    // imageUrl="@{baseObBean.inputData}" 其中数据必须使用dataBinding才会走到这个方法中
    //有两个参数的时候这个方法似乎会调用两次，且必须都设置了才会走这个方法
    //此方法必须使用static 不然会崩溃
    @BindingAdapter({"android:onClick", "imageUrl"})
    public static void loadImage(ImageButton imageView, View.OnClickListener onClickListener, String imageUrl) {
//        L.e("test", "invoke bindingAdapter setImageUrl:" + url);
//      onClickListener是  com.cgy.mycollections.generated.callback.OnClickListener
        L.e("test", "invoke bindingAdapter imageUrl:" + imageUrl);
        L.e("test", "invoke bindingAdapter loadImage:" + onClickListener.getClass().getName());
        imageView.setOnClickListener(onClickListener);
        imageView.setImageResource(R.drawable.yinyue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时记得解绑，以免内存泄漏
        binding.unbind();
    }

    /**
     * 测试双向绑定  ui改变引起数据改变
     */
    private void testBidirectionalBinding() {
//双向绑定就是在ObservableField基础上，将ui如editext中的text增加  一个 = (等于)号。
//        如    android:text="@={baseObBean.inputData}"
    }

    /**
     * 测试单向绑定  即 数据改变引起ui改变
     */
    private void testSingleBinding() {
        demo = new Demo(R.string.title_activity_data_binding_demo,
                R.string.info_data_binding_demo, true, DataBindingDemo.class);
        binding.setDemo(demo);
// 此处修改参数能改变ui显示，不过似乎必须在绘制前，绘制之后如onClick中必须再setDemo才会生效
//        demo.titleId = R.string.info_net_demo;

        binding.setListener(new BindingClickListener() {
            @Override
            public void onClick() {
                demo.titleId = R.string.info_net_demo;
                binding.setDemo(demo);//必须set不然不生效，也可以使Demo继承BaseObservable
//               还可以使用 单向绑定来刷新数据
//                使用单向绑定刷新UI有三种
//               1 使用 BaseObservable
//               2 使用 ObservableField
//               3 使用 ObservableCollection
            }

            @Override
            public void onBaseObservableClick() {
//                dataBean.setTestName("名字改变啦~");//只改一个
//                dataBean.changeAll("名字改变啦111~", "名字改变啦222~");//全都改

                dataBean.obFiled.set("修改obFiled");//ObservableField.set只会直接 更新当前修改字段

                dataList.set(0, "list修改后的值");
            }
        });

//      1，2  BaseObservable+ObservableField
        dataBean = new BaseObservableDataBean(new ObservableField<String>("ObservableFiled")
                , new ObservableField<String>("InputData"));
        dataBean.testName = "使用BaseObservable";
        dataBean.testContent = "来测试点击通知数据改变";
        binding.setBaseObBean(dataBean);

//      3  ObservableCollection
        dataList = new ObservableArrayList<>();
        dataList.add("list data 0");//数组长度可以不足，不足的部分为空
        dataList.add("list data 1");
        binding.setObDataList(dataList);
    }
}
