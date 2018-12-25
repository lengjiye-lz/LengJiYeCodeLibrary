package com.code.lengjiye.code;

/**
 * 接口
 * 创建人: lz
 * 创建时间: 2018/12/24
 * 修改备注:
 */
public class InterfaceTest {

    interface interface1 {
        void getinterface1();
    }

    interface interface2 {
        void getinterface2();
    }

    /**
     * 接口多继承
     */
    interface interface3 extends interface1, interface2 {

        void getinterface3();

        /**
         * 接口中的默认方法，可以不实现
         *
         * @return
         */
        default String getName() {
            return "asdcas";
        }

    }

    /**
     * 实现接口需要实现接口里面的全部方法
     */
    class sdc implements interface3 {

        @Override
        public void getinterface1() {

        }

        @Override
        public void getinterface2() {

        }

        @Override
        public void getinterface3() {

        }
    }
}
