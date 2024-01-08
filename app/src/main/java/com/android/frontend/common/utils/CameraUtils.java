package com.android.frontend.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @Description: 相机、相册工具类
 * @Author: MING
 * @Date: 2024/01/07
 */
public class CameraUtils {

    /**
     * @Description: 获取启动相机的Intent
     * @see Context
     * @see File
     * @see Intent
     */
    public static Intent getTakePhotoIntent(Context context, File outputImagePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSdcard()) {
            setOutputImagePath(context, intent, outputImagePath);
        }
        return intent;
    }

    /**
     * @Description: 获取启动相册的Intent
     * @see Intent
     */
    public static Intent getSelectPhotoIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    /**
     * @Description: 检查SD卡是否可用
     * @see boolean
     */

    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @Description: 设置拍照结果存储路径
     * @see Context
     * @see Intent
     * @see File
     */
    private static void setOutputImagePath(Context context, Intent intent, File outputImagePath) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Uri uri = Uri.fromFile(outputImagePath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, outputImagePath.getAbsolutePath());
            Uri uri = context.getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
    }

    /**
     * @Description: 获取在Android 4.4及以上版本中从相册选择照片的路径
     * @see Intent
     * @see Context
     * @see String
     */
    public static String getImageOnKitKatPath(Intent data, Context context) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=" + docId.split(":")[1], context);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null, context);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null, context);
        }
        return imagePath;
    }

    /**
     * @Description: 通过Uri获取图片路径
     * @see Uri
     * @see String
     * @see Context
     * @see String
     */
    @SuppressLint("Range")
    private static String getImagePath(Uri uri, String selection, Context context) {
        String path = null;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, selection, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @Description: 旋转图片，使其方向正确
     * @see String
     * @see Bitmap
     * @see ImageView
     */

    public static void ImgUpdateDirection(String filepath, Bitmap orc_bitmap, ImageView iv) {
        int digree = getExifOrientation(filepath);
        if (digree != 0) {
            Matrix m = new Matrix();
            m.postRotate(digree);
            orc_bitmap = Bitmap.createBitmap(orc_bitmap, 0, 0, orc_bitmap.getWidth(), orc_bitmap.getHeight(), m, true);
        }
        if (orc_bitmap != null) {
            iv.setImageBitmap(orc_bitmap);
        }
    }

    /**
     * @Description: 获取图片的Exif方向信息
     * @see String
     * @see int
     */

    private static int getExifOrientation(String filepath) {
        try {
            ExifInterface exif = new ExifInterface(filepath);
            return exif != null ? exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED) : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description: 获取在Android 4.4以下版本中从相册选择照片的路径
     * @see Intent
     * @see Context
     * @see String
     */

    public static String getImageBeforeKitKatPath(Intent data, Context context) {
        Uri uri = data.getData();
        return getImagePath(uri, null, context);
    }

    /**
     * @Description: 压缩图片
     * @see Bitmap
     * @see Bitmap
     */

    public static Bitmap compression(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        if (outputStream.toByteArray().length / 1024 > 1024) {
            outputStream.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        BitmapFactory.Options options = calculateOptions(inputStream);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * @Description: 计算图片的压缩选项
     * @see ByteArrayInputStream
     * @see BitmapFactory.Options
     */

    private static BitmapFactory.Options calculateOptions(ByteArrayInputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inJustDecodeBounds = false;
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        float height = 800f;
        float width = 480f;
        int zoomRatio = calculateZoomRatio(outWidth, outHeight, width, height);
        options.inSampleSize = zoomRatio;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    /**
     * @Description: 计算图片的缩放比例
     * @see int
     * @see int
     * @see float
     * @see float
     * @see int
     */

    private static int calculateZoomRatio(int outWidth, int outHeight, float width, float height) {
        int zoomRatio = 1;
        if (outWidth > outHeight && outWidth > width) {
            zoomRatio = outWidth / (int) width;
        } else if (outHeight > height) {
            zoomRatio = outHeight / (int) height;
        }
        return Math.max(1, zoomRatio);
    }
}