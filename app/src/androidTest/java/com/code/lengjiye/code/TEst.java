package com.code.lengjiye.code;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/24
 * 修改备注:
 */
public class TEst<T> {

    private T t;

    /**
     * 类中的静态方法使用泛型：静态方法无法访问类上定义的泛型
     */
    public <E> void getAsdca(T t) {
        this.t = t;
    }


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
