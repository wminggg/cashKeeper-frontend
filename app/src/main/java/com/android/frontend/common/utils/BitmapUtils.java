package com.android.frontend.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description: Bitmap工具类
 * @Author: MING
 * @Date: 2024/01/06
 */

public class BitmapUtils {

    /**
     * @Description: 将Bitmap转为Base64字符串
     * @see Bitmap
     * @see String
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            Log.e("BitmapUtils", "Error converting bitmap to base64", e);
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                Log.e("BitmapUtils", "Error closing ByteArrayOutputStream", e);
            }
        }
        return result;
    }


    /**
     * @Description: 将Base64字符串转为Bitmap
     * @see String
     * @see Bitmap
     */

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * @Description: 通过URL获取Bitmap
     * @see String
     * @see Bitmap
     */
    public static Bitmap urlToBitmap(final String url) {
        final Bitmap[] bitmap = {null};
        new Thread(() -> {
            URL imageUrl;
            try {
                imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap[0] = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                Log.e("BitmapUtils", "Error loading bitmap from URL: " + url, e);
            }
        }).start();
        return bitmap[0];
    }

}