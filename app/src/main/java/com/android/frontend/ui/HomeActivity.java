package com.android.frontend.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.frontend.R;
import com.android.frontend.common.utils.BitmapUtils;
import com.android.frontend.common.utils.CameraUtils;
import com.android.frontend.common.utils.SPUtils;
import com.android.frontend.common.utils.SnackbarUtils;
import com.android.frontend.model.enums.SnackbarType;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    // 当前页面的视图
    private View currentView;
    // 是否已获取权限
    private boolean hasPermissions = false;
    // 底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    private View bottomView;
    // 存储拍照后的图片路径
    private File outputImagePath;
    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    // 用户头像ImageView
    private ShapeableImageView ivHead;

    private final RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true);

    private ActivityResultLauncher<Intent> takePhotoLauncher;
    private ActivityResultLauncher<Intent> openAlbumLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentView = findViewById(android.R.id.content);
        setContentView(R.layout.activity_home);
        ivHead = findViewById(R.id.iv_head);
        checkVersion();
        // 初始化 ActivityResultLauncher
        takePhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::handleTakePhotoResult);

        openAlbumLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::handleOpenAlbumResult);
        // 从缓存中取出用户头像地址
        String imageUrl = SPUtils.getString("imageUrl", null, this);
        if (imageUrl != null) {
            loadImage(imageUrl);
        }
    }

    /**
     * @Description: 显示消息，并通过SnackbarUtils显示Snackbar
     * @see String
     * @see SnackbarType
     */
    private void showMsg(String msg, SnackbarType snackbarType) {
        SnackbarUtils.showCustomSnackbar(currentView, msg, snackbarType);
    }

    /**
     * @Description: 检查Android版本并请求相关权限
     */

    @SuppressLint("CheckResult")
    private void checkVersion() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(this::handlePermissions);
}

    /**
     * @Description: 处理权限请求结果
     * @see boolean
     */

    private void handlePermissions(boolean granted) {
        showMsg(granted ? "已获取权限" : "权限未开启", granted ? SnackbarType.SUCCESS : SnackbarType.FAILURE);
        hasPermissions = granted;
    }

    /**
     * @Description: 更换头像按钮点击事件
     * @see View
     */

    public void changeAvatar(View view) {
        initBottomSheetDialog();
        bottomSheetDialog.show();
    }

    /**
     * @Description: 初始化底部弹窗
     */

    private void initBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        // 设置底部弹窗中各按钮的点击事件
        setOnClickListener(R.id.tv_take_pictures, this::takePhoto);
        setOnClickListener(R.id.tv_open_album, this::openAlbum);
        setOnClickListener(R.id.tv_cancel, v -> bottomSheetDialog.cancel());
    }

    /**
     * @Description: 设置View的点击事件
     * @see int
     * @see View.OnClickListener
     */

    private void setOnClickListener(int viewId, View.OnClickListener listener) {
        bottomView.findViewById(viewId).setOnClickListener(listener);
    }

    /**
     * @Description: 拍照按钮点击事件
     * @see View
     */

    private void takePhoto(View v) {
        if (!hasPermissions) {
            showMsg("未获取到权限", SnackbarType.FAILURE);
            checkVersion();
            return;
        }
        outputImagePath = createOutputImageFile();
        // 使用 ActivityResultLauncher 启动相机
        takePhotoLauncher.launch(CameraUtils.getTakePhotoIntent(this, outputImagePath));
        bottomSheetDialog.cancel();
    }

    /**
     * @Description: 创建拍照后存储图片的文件
     * @see File
     */

    private File createOutputImageFile() {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        return new File(getExternalCacheDir(), timestamp + ".jpg");
    }

    /**
     * @Description: 打开相册按钮点击事件
     * @see View
     */

    private void openAlbum(View v) {
        if (!hasPermissions) {
            showMsg("未获取到权限", SnackbarType.FAILURE);
            checkVersion();
            return;
        }
        // 使用 ActivityResultLauncher 启动相册
        openAlbumLauncher.launch(CameraUtils.getSelectPhotoIntent());
        bottomSheetDialog.cancel();
    }

    /**
     * @Description: 处理拍照返回结果
     * @see ActivityResult
     */

    private void handleTakePhotoResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            displayImage(outputImagePath.getAbsolutePath());
        }
    }

    /**
     * @Description: 处理选择相册返回结果
     * @see ActivityResult
     */

    private void handleOpenAlbumResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            String imagePath = null;
            if (data != null) {
                imagePath = CameraUtils.getImageOnKitKatPath(data, this);
            }
            displayImage(imagePath);
        }
    }

    /**
     * @Description: 显示图片
     * @see String
     */

    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            SPUtils.putString("imageUrl", imagePath, this);
            loadImage(imagePath);
            Bitmap orcBitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            String base64Pic = BitmapUtils.bitmapToBase64(orcBitmap);
        } else {
            showMsg("图片获取失败", SnackbarType.FAILURE);
        }
    }

    /**
     * @Description: 加载图片到ImageView
     * @see String
     */

    private void loadImage(String imageUrl) {
        Glide.with(this).load(imageUrl).apply(requestOptions).into(ivHead);
    }
}