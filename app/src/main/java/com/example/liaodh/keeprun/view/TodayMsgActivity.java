package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ActivityTodayMsgBinding;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.PredictionTF;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.commod.CommonToast;
import com.example.liaodh.keeprun.view.commod.PaintView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TodayMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTodayMsgBinding msgBinding;
    private static final String MODEL_FILE = "file:///android_asset/mnist.pb"; //模型存放路径
    Bitmap bitmap;
    PredictionTF preTF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        msgBinding = DataBindingUtil.setContentView(this, R.layout.activity_today_msg);
        msgBinding.paintView.setSize((int)getResources().getDimension(R.dimen.with_height_460), (int)getResources().getDimension(R.dimen.with_height_460), new PaintView.OnMoveLisener() {
            @Override
            public void hideWords() {

            }
        });
        msgBinding.back.setOnClickListener(this);
        msgBinding.refreshEdit.setOnClickListener(this);
        msgBinding.save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.refresh_edit:
                msgBinding.paintView.clear();
                break;
            case R.id.save:
                yuce();
                save();
                break;
        }
    }

    private void yuce() {
        bitmap = msgBinding.paintView.getCachebBitmap();
        preTF = new PredictionTF(getAssets(), MODEL_FILE);//输入模型存放路径，并加载TensoFlow模型
        int[] result = preTF.getPredict(bitmap);
        for (int i = 0; i < result.length; i++) {
            String res = "评价为：";
            res = res + String.valueOf(result[i]) + " ";
            CommonToast.showShortToast(res);
        }
    }

    private void save() {

        String url = HttpUtil.baseUrl + "saveUserRunInfo?" + "userId=" + SpUserInfoUtil.getUserId()
                + "&runStep=" + SpUserInfoUtil.getRunSteps()
                + "&runDis=" + SpUserInfoUtil.getRunDis()
                + "&runTimes=" + SpUserInfoUtil.getTimes()
                + "&kaluli=" + SpUserInfoUtil.getKaluli()
                + "&manyi=" + SpUserInfoUtil.getKaluli();
        HttpUtil.saveUserInfo(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //CommonToast.showShortToast("网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.optString("resCode");
                    if (code.equals("202")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //CommonToast.showShortToast("保存成功");
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        msgBinding.paintView.recycle();
    }

    @Override
    public void onBackPressed() {
        CommonToast.showShortToast("请保存数据，并按返回按钮返回");
    }
}
