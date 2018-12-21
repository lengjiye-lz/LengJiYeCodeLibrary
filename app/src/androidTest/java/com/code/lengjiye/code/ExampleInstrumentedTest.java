package com.code.lengjiye.code;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lengjiye.tools.LogTool;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.code.lengjiye.lengjiyecodelibrary", appContext.getPackageName());
    }

    /**
     * 方法测试
     */
    @Test
    public void testMethod() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("100");
        arrayList.add(100);
        arrayList.add('a');
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {

            Object object = arrayList.get(i);

            if (object instanceof String) {
                LogTool.e("对象：" + object + "asdcasd");
            } else if (object instanceof Integer) {
                LogTool.e("对象：" + ((int) object + 10));
            } else if (object instanceof Character) {
                LogTool.e("对象：" + arrayList.get(i));
            }
        }
    }

    @Test
    // 泛型的类型参数只能是类类型，不能是简单类型
    public void getPerson() {
        Persion persion = new Persion();
        persion.setPersion("asdcasd");
        LogTool.e("persion: " + persion.getPersion());
    }

    /**
     * 泛型
     *
     * @param <T>
     */
    public class Persion<T> {
        private T persion;

        public Persion() {
        }

        public T getPersion() {
            return persion;
        }

        public void setPersion(T persion) {
            this.persion = persion;
        }
    }

    /**
     * 泛型接口
     *
     * @param <T>
     */
    public interface Persino<T> {
        T onPersino();
    }

    /**
     * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
     *
     * @param <T>
     */
    class Generator<T> implements Persino<T> {

        @Override
        public T onPersino() {
            return null;
        }
    }
}
