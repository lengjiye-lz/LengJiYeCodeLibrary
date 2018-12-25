package com.lengjiye.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences操作工具类
 * 如果只是保存数据，没什么特殊的要求可以直接调用getInstance()方法获取实例，putXX()保存值，getXX()方法获取值。
 * Created by lz on 2016/7/14.
 */
public class SPTool {

    /**
     * 默认使用包名作为name
     */
    private static String NAME;

    private static SPTool spTool;

    private static Context application;

    private SPTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @return
     */
    public synchronized static SPTool getInstance(Context applicationContext) {
        NAME = applicationContext.getPackageName();
        application = applicationContext;
        return getInstance(NAME);
    }

    /**
     * @return SPTool
     */
    public synchronized static SPTool getInstance(String name) {
        if (spTool == null) {
            spTool = new SPTool();
        }
        NAME = name;
        return spTool;
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        String value = (String) getParam(key, defValue);
        return value;
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        boolean value = (boolean) getParam(key, defValue);
        return value;
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        int value = (int) getParam(key, defValue);
        return value;
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        long value = (long) getParam(key, defValue);
        return value;
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        float value = (float) getParam(key, defValue);
        return value;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    private void setParam(String key, Object object) {
        SharedPreferences sp = application.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    private Object getParam(String key, Object defaultObject) {
        SharedPreferences sp = application.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        SharedPreferences sp = application.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences sp = application.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences sp = application.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
