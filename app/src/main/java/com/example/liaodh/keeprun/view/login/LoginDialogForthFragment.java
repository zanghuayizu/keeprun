package com.example.liaodh.keeprun.view.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.LogindialogFragmentForthBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.MainActivity;
import com.example.liaodh.keeprun.view.commod.CommonToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class LoginDialogForthFragment extends BaseDialogFragment implements OnClickListener,DialogInterface.OnKeyListener {

    private LogindialogFragmentForthBinding mBinding;
    private Uri imageUri;
    private static final int TAKE_PHOTO = 1;
    private File outputImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindialog_fragment_forth, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        mBinding.userImage.setOnClickListener(this);
        getDialog().setOnKeyListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.user_image:
                //拍照或者从相册上传
                takePhoto();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (getActivity() != null){
            outputImage = new File(getActivity().getExternalCacheDir(),"output_image.jpg");
        }
        if (!outputImage.exists()){
            CommonToast.showShortToast("请先拍照");
        }else {
            //将数据传到服务器
            saveUserInfo();
        }
    }

    private void saveUserInfo() {
        if (getActivity() != null){
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        SpUserInfoUtil.setUserIsLogin(true);
        dismiss();
    }

    private void takePhoto() {
        if (getActivity() != null){
            outputImage = new File(getActivity().getExternalCacheDir(),"output_image.jpg");
            try {
                if (outputImage.exists()){
                    outputImage.delete();
                }
                outputImage.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 24){
                imageUri = FileProvider.getUriForFile(this.getContext(),
                        "om.example.liaodh.keeprun.fileprovider",outputImage);
            }else {
                imageUri = Uri.fromFile(outputImage);
            }
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,TAKE_PHOTO);
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        mBinding.userImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
