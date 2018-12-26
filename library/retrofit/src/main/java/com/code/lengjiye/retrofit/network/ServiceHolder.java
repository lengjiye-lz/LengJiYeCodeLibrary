package com.code.lengjiye.retrofit.network;

import java.util.LinkedList;

import static com.code.lengjiye.retrofit.network.ServiceHolder.ERetrofitHolderType.RETROFIT_HOLDER;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class ServiceHolder {

    private LinkedList linkedList;

    interface ERetrofitHolderType {
        int RETROFIT_HOLDER = 0;
    }

    private ServiceHolder() {
        linkedList = new LinkedList();
    }

    private static class Instance {
        private static ServiceHolder serviceHolder = new ServiceHolder();
    }

    public static ServiceHolder getInstance() {
        return Instance.serviceHolder;
    }

    /**
     * @param c
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> c) {
        return getService(c, RETROFIT_HOLDER);
    }

    /**
     * @param c
     * @param <T>
     * @return
     */
    private synchronized <T> T getService(Class<T> c, int eRetrofitHolderType) {

//        long startTime = System.currentTimeMillis();

        int index = -1;
        T t;

        for (int i = 0; i < linkedList.size(); i++) {
            Class linkedListitem = linkedList.get(i).getClass();

            if (linkedListitem.getInterfaces()[0].getName().equals(c.getName())) {
                index = i;
                break;
            }
        }


        if (index != -1) {      //如果已经初始化过该Service
            t = (T) linkedList.get(index);
            if (index != 0) {   //将该Service移到栈顶
                linkedList.remove(index);
                linkedList.push(t);
            }

        } else {
            if (linkedList.size() > 3) {
                linkedList.pollLast();
            }
            switch (eRetrofitHolderType) {
                case ERetrofitHolderType.RETROFIT_HOLDER: {
                    t = RetrofitHolder.getRetrofitInstance().create(c);
                    break;
                }
//                case RetrofitHolderPluto: {
//                    t = RetrofitHolderPluto.getRetrofitInstance().create(c);
//                    break;
//                }
//                case RetrofitHolderPlay: {
//                    t = RetrofitHolderPlay.getRetrofitInstance().create(c);
//                    break;
//                }
//                case ERetrofitHolderType.RetrofitHolderDownload: {
//                    t = RetrofitDownloadHolder.getRetrofitInstance().create(c);
//                    break;
//                }
//                case RetrofitCustomHolder: {
//                    t = RetrofitCustomHolder.getRetrofitInstance().create(c);
//                    break;
//                }
                default:
                    t = RetrofitHolder.getRetrofitInstance().create(c);
            }
            linkedList.push(t);
        }

//        LogUtils.e("ServiceHolder", "manageService loadingTime=" + (System.currentTimeMillis() - startTime));

        return t;
    }
}
