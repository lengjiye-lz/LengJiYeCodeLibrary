package com.lengjiye.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片操作方法类
 * Created by lz on 2016/6/7.
 */
public class ImageTool {
    private static Bitmap bitmap;

    private static float height;
    private static float width;

    public ImageTool(float newWidth, float newHeight) {
        this.width = newWidth;
        this.height = newHeight;
    }

    public ImageTool() {
        this.height = 800f;
        this.width = 480f;
    }

    /**
     * 对图片进行比例压缩
     *
     * @param oldPath 图片原始路径
     * @param newPath 压缩之后图片的路径
     * @param name    图片名字
     */
    public static String getImage(String oldPath, String newPath, String name) {
        ByteArrayOutputStream baos = null;// 压缩好比例大小后再进行质量压缩
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(oldPath, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > width) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / width);
            } else if (w < h && h > height) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / height);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(oldPath, newOpts);
            baos = compressImage(bitmap, 100);
            return saveImage(newPath, name, baos);
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 对图片进行质量压缩
     *
     * @param image
     * @param compressSize 需要压缩的图片大小，单位kb
     * @return
     */
    private static ByteArrayOutputStream compressImage(Bitmap image, int compressSize) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (outputStream.toByteArray().length / 1024 > compressSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                outputStream.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, outputStream);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    /**
     * 保存图片
     *
     * @param path         路径
     * @param name         图片名字
     * @param outputStream 输出流
     */
    private static String saveImage(String path, String name, ByteArrayOutputStream outputStream) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = FileTool.createDirectory(path);
            try {
                FileOutputStream out = new FileOutputStream(file.getAbsolutePath() + File.separator + name);
                out.write(outputStream.toByteArray());
                out.flush();
                out.close();
                return file.getAbsolutePath() + File.separator + name;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 释放bitmap
     * 界面关闭时需要调用此方法
     */
    public static void bitmapRecycle() {
        if (null != bitmap) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
