package com.lengjiye.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片压缩
 * 单张压缩，多张压缩
 * Created by lz on 2016/6/7.
 */
public class ImageCompress {

    private ImageTool imageTool;

    public ImageCompress() {
        if (imageTool == null) {
            imageTool = new ImageTool();
        }
    }

    /**
     * 静态内部类
     * 保证单例模式是线程安全，又避免了同步带来的性能影响
     * 感觉和饿汉式单例差不多
     */
    private static class ImageHolder {
        private static final ImageCompress IMAGE_UPLOAD = new ImageCompress();
    }

    public static ImageCompress getInstance() {
        return ImageHolder.IMAGE_UPLOAD;
    }

    /**
     * 设置图片压缩宽高，默认是480*800
     *
     * @param newWidth
     * @param newHeight
     */
    public void setImageUtils(float newWidth, float newHeight) {
        imageTool = new ImageTool(newWidth, newHeight);
    }

    /**
     * 多张图片压缩方法
     *
     * @param stringList 需要压缩的图片的绝对路径集合
     * @param newpath    新路径
     * @param callBack   接口回调  成功回调使用void success(List<String> strings)接口
     */
    public void compress(final List<String> stringList, final String newpath, final CompressCallBack callBack) {
        final List<String> strings = new ArrayList<>();
        if (stringList == null || stringList.size() <= 0) {
            callBack.failure("压缩失败");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < stringList.size(); i++) {
                    File file = new File(stringList.get(i));
                    if (FileTool.isExist(file.getPath())) {
                        String path = ImageTool.getImage(file.getPath(), newpath, file.getName());
                        strings.add(path);
                    }
                }
                if (strings.size() < 2) {
                    callBack.success(strings.get(0));
                } else {
                    callBack.success(strings);
                }
                ImageTool.bitmapRecycle();
            }
        }).start();
    }

    /**
     * 单张图片压缩方法
     *
     * @param s        需要压缩的图片的绝对路径
     * @param newpath  新路径
     * @param callBack 接口回调 成功回调使用void success(String s)接口
     */
    public void compress(String s, String newpath, CompressCallBack callBack) {
        List<String> strings = new ArrayList<>();
        strings.add(s);
        compress(strings, newpath, callBack);
    }

    /**
     * 压缩回调接口
     * Created by lz on 2016/6/7.
     */
    public interface CompressCallBack {

        // 成功回调 返回list
        void success(List<String> strings);

        //返回单个数据
        void success(String s);

        // 失败回调
        void failure(String error);
    }
}
