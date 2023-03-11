package com.example.ring.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ring.MainActivity;
import com.example.ring.MoveSearch;
import com.example.ring.R;
import com.example.ring.utils.BitmapUtils;
import com.example.ring.utils.CameraUtils;
import com.example.ring.utils.SPUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Date;


public class MyFragment extends Fragment {
    //权限请求
      private RxPermissions rxPermissions;
    //是否拥有权限
    private boolean hasPermissions = false;
    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    //图片控件
    private ShapeableImageView ivHead;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;
    private String titles;

    private RelativeLayout relativeLayout;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        titles = ((MainActivity) activity).getTitles();//通过强转成宿主activity，就可以获取到传递过来的数据
//        showMsg(titles);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my, container, false);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        ivHead=getActivity().findViewById(R.id.iv_head);
        TextView text =getActivity().findViewById(R.id.xingming);
        text.setText(titles);
        relativeLayout=getActivity().findViewById(R.id.re_xiangce);
        checkVersion();
        //取出缓存
        String imageUrl = SPUtils.getString("imageUrl",null,getActivity());
        if(imageUrl != null){
            Glide.with(this).load(imageUrl).apply(requestOptions).into(ivHead);
        }
        /**
         * 更换头像
         *
         * @param view
         */
        relativeLayout.setOnClickListener(v -> {
                    showMsg("请点击头像，更换哦~");
                    });

        ivHead.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(getActivity());
            bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
            bottomSheetDialog.setContentView(bottomView);
            bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
            TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
            TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
            TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

            //拍照
            tvTakePictures.setOnClickListener( view->{
                showMsg("拍照");
                bottomSheetDialog.cancel();
                takePhoto();
            });
            //打开相册
            tvOpenAlbum.setOnClickListener(view -> {
//                showMsg("打开相册");
                bottomSheetDialog.cancel();
                openAlbum();
            });
            //取消
            tvCancel.setOnClickListener(view -> {
                bottomSheetDialog.cancel();
            });
            bottomSheetDialog.show();

        });
    }
        /**
         * Toast提示
         *
         * @param msg
         */
    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }





    /**
     * 检查版本
     */
    @SuppressLint("CheckResult")
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(getActivity());
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
//                            showMsg("已获取权限");
                            //是否拥有权限
                            hasPermissions = true;

                        } else {//申请失败
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }

    /**
     * 拍照
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getActivity().getExternalCacheDir(), filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(getActivity(), outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }
    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }
    /**
     * 返回到Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK){
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode ==  getActivity().RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    //4.4及以上系统使用这个方法处理图片
                    imagePath = CameraUtils.getImageOnKitKatPath(data, getActivity());
                    //显示图片
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            SPUtils.putString("imageUrl",imagePath,getActivity());

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(ivHead);

            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            showMsg("图片获取失败");
        }
    }







}