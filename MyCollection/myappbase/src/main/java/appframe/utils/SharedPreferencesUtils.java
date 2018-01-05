package appframe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by witon on 2016/7/7.
 * 偏好设置的类
 */
public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance=null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    /**
     * 根据此方法获取实例，配置文件名将是程序的包名
     * @param context
     * @return
     */
    public synchronized static SharedPreferencesUtils getInstance(Context context){
        if(null == instance){
            instance=new SharedPreferencesUtils(context,null);
        }
        return instance;
    }

    /**
     * 根据此方法获取实例
     * @param context
     * @param name 配置文件名
     * @return
     */
    public synchronized static SharedPreferencesUtils getInstance(Context context, String name){
        if(null == instance){
            instance=new SharedPreferencesUtils(context,name);
        }
        return instance;
    }

    /**
     * @param context
     * @param name
     */
    private SharedPreferencesUtils(Context context, String name){
        if (null == name || name.trim().length() == 0) {
            name = context.getPackageName();
        }
        mSharedPreferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * 写入字符串信息
     *
     * @param key
     *            键
     * @param value
     *            String
     */
    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 获取字符串信息
     *
     * @param key
     *            键
     * @param defValue
     *            默认值
     * @return String
     */
    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 写入long型数据
     *
     * @param key
     *            键
     * @param value
     *            long
     */
    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    /**
     * 获取long型数据
     *
     * @param key
     *            键
     * @param defValue
     *            默认值
     * @return long
     */
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 写入int数据
     *
     * @param key
     *            键
     * @param value
     *            int
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 获取int数据
     *
     * @param key
     *            键
     * @param defValue
     *            默认值
     * @return int
     */
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 写入boolean值
     *
     * @param key
     *            键
     * @param flag
     *            boolean
     */
    public void putBoolean(String key, boolean flag) {
        mEditor.putBoolean(key, flag);
        mEditor.commit();
    }

    /**
     * 获取boolean值
     *
     * @param key
     *            键
     * @param defValue
     *            默认值
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 写入float值
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 获取float值
     *
     * @param key
     *            键
     * @param defValue
     *            默认值
     * @return float
     */
    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    /**
     * 删除某项
     *
     * @param key
     *            键
     */
    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clear(){
        mEditor.clear().commit();
    }
    /**
     * 是否已经存在某项
     *
     * @param key
     *            键
     * @return 存在返回true，否则返回false
     */
    public boolean exist(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     * 存入list，固定只有5个
     * @param key
     * @param list
     */
    public void putList(String key, ArrayList<String> list){
        String value="";
        int num=(list.size()>5)?5:list.size();
        for(int i=0;i<num-1;i++){
            value+=list.get(i);
            value+=",";
        }
        value+=list.get(num-1);
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public ArrayList<String> getList(String key){
        ArrayList<String> list=new ArrayList<String>();
        String value=mSharedPreferences.getString(key, "");
        if(!TextUtils.isEmpty(value)){
            String[] values=value.split(",");
           list.addAll(Arrays.asList(values));
        }
        return list;
    }

}
