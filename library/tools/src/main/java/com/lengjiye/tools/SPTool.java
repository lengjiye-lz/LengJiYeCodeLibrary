package com.lengjiye.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.code.lengjiye.app.AppMaster;

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
    private static String NAME = AppMaster.getInstance().getApplicationId();

    private SPTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        put(NAME, key, object);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String name, String key, Object object) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
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

    public static String getString(String key, String defValue) {
        return getString(NAME, key, defValue);
    }

    public static String getString(String name, String key, String defValue) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return getInt(NAME, key, defValue);
    }

    public static int getInt(String name, String key, int defValue) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getBoolean(NAME, key, defValue);
    }

    public static boolean getBoolean(String name, String key, boolean defValue) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return getFloat(NAME, key, defValue);
    }

    public static float getFloat(String name, String key, float defValue) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return getLong(NAME, key, defValue);
    }

    public static long getLong(String name, String key, long defValue) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return contains(NAME, key);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String name, String key) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        remove(NAME, key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String name, String key) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        clear(NAME);
    }


    /**
     * 清除所有数据
     */
    public static void clear(String name) {
        SharedPreferences sp = AppMaster.getInstance().getAppContext().getSharedPreferences(name,
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
