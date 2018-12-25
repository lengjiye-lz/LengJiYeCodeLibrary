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
 * 泛型类 泛型方法测试
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GenericTest {

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
        T getPersion();

        void setPersion(T t);
    }

    /**
     * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
     *
     * @param <T>
     */
    static class Generator<T> implements Persino<T> {

        private T t;

        @Override
        public T getPersion() {
            return t;
        }

        @Override
        public void setPersion(T t) {
            this.t = t;
        }

    }

    class GSDFs implements Persino<String> {

        @Override
        public String getPersion() {
            return null;
        }

        @Override
        public void setPersion(String s) {

        }


    }

    @Test
    public void getT() {
        Generator<String> generator = new Generator<>();
        generator.setPersion("asdcasd");
        LogTool.e("getPersion:" + generator.getPersion());

        sdc("asdcasd");
        sdc(100);


        sdcsd(new Generator<EAsd>());

        adscasd(new Generator<TEst>());

    }

    /**
     * 泛型方法
     */
    public <T> void sdc(T t) {
        LogTool.e("t:" + t);
    }

    /**
     * extends 泛型的上边界  传入的类型必须是String本身或者子类
     */
    public void sdcsd(Persino<? extends TEst> persino) {
        LogTool.e("sdcsd");

    }

    /**
     * super 泛型的下边界  传入的类型必须是String本身或者父类
     */
    public <T extends Number> T adscasd(Persino<? super TEst> persino) {
        LogTool.e("adscasd");
        return null;
    }

    @Test
    public void sdca() {

//        Son son = (Son) new Father();
//        son.setAge(10);

        Father father = new Son();
        if (father != null) {
            father.toString();
        }
        ((Son) father).setAge(20);
        String.valueOf(father);


    }

    /**
     * 参数可变的方法
     * <p>
     * strings就是一个临时的数组
     */
    private void getString(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            LogTool.e(strings[i]);
        }
    }
}
