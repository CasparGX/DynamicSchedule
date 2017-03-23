package cc.xaabb.dynamicschedule.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhouenxu on 2017/3/22.
 */

public class ImageUtils {

    /**
     * 上下拼接两个Bitmap
     */
    public static Bitmap mergeBitmap(Bitmap mBitmap1, Bitmap mBitmap2) {
        int h = 0;
        Bitmap mMergeBitmap = null;
        h+=mBitmap1.getHeight();
        h+=mBitmap2.getHeight();
        mMergeBitmap = Bitmap.createBitmap(mBitmap1.getWidth(), h,
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mMergeBitmap);

        Rect topRect = new Rect(0, 0, mBitmap1.getWidth(), mBitmap1.getHeight());
        Rect bottomRect  = new Rect(0, 0, mBitmap2.getWidth(), mBitmap2.getHeight());

        Rect bottomRectT  = new Rect(0, mBitmap1.getHeight(), mBitmap1.getWidth(), h);

        canvas.drawBitmap(mBitmap1, topRect, topRect, null);
        canvas.drawBitmap(mBitmap2, bottomRect, bottomRectT, null);
        return mMergeBitmap;
    }

    /**
     * 截取view的屏幕
     *
     * @param mView
     * @return
     */
    public static Bitmap getBitmapByView(View mView, int color) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        h += mView.getHeight();
        //mView.setBackgroundColor(color);
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(mView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);

        mView.draw(canvas);
        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByScrollView(NestedScrollView scrollView, int color) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(color);
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                Locale.US);
        File outfile = new File("/sdcard/image");
        // 如果文件不存在，则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fname = outfile + "/" + sdf.format(new Date()) + ".png";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fname);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname;
    }
}
