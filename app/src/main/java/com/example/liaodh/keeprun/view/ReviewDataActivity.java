package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ActivityReviewBinding;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.commod.CommonToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class ReviewDataActivity extends AppCompatActivity {
    private ActivityReviewBinding mBinding;
    private HashMap<Integer, ArrayList> mFeedBackMap;
    private ReviewDataActivity.MyAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }


    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        initListener();
        initFeedBackMap();
    }


    private void initListener() {
        mBinding.ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListView() {
        mAdapter = new ReviewDataActivity.MyAdapter();
        mBinding.listFeedback.setAdapter(mAdapter);
        mBinding.listFeedback.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initFeedBackMap() {
        String url = HttpUtil.baseUrl + "getUserRunInfo?" + "userId=" + SpUserInfoUtil.getUserId();
        HttpUtil.getUserInfo(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonToast.showShortToast("网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) return;
                try {
                    JSONArray runInfo = new JSONArray(response.body().string());
                    mFeedBackMap = new HashMap<Integer, ArrayList>();
                    for (int i = 0; i < runInfo.length()-1; i++) {
                        JSONObject object = runInfo.getJSONObject(i);
                        ArrayList list = new ArrayList();
                        list.add(object.getString("day"));
                        list.add(object.getString("runtimes"));
                        list.add(object.getString("runstep"));
                        list.add(object.getString("rundis"));
                        list.add(object.getString("manyi"));
                        mFeedBackMap.put(i,list);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initListView();
                        }
                    });
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mFeedBackMap.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReviewDataActivity.MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.review_item, null);
                holder = new ReviewDataActivity.MyAdapter.ViewHolder();
                holder.riqi = convertView.findViewById(R.id.riqi);
                holder.times = convertView.findViewById(R.id.times);
                holder.steps = convertView.findViewById(R.id.steps);
                holder.juli = convertView.findViewById(R.id.juli);
                holder.manyi = convertView.findViewById(R.id.manyi);
                convertView.setTag(holder);
            } else {
                holder = (ReviewDataActivity.MyAdapter.ViewHolder) convertView.getTag();
            }

            ArrayList list;
            list = mFeedBackMap.get(position);

            if (list != null && list.size() > 4) {
                holder.riqi.setText(list.get(0).toString());
                holder.times.setText(list.get(1).toString());
                holder.steps.setText(list.get(2).toString());
                holder.juli.setText(list.get(3).toString());
                holder.manyi.setText(list.get(4).toString());
            }
            return convertView;
        }

        private class ViewHolder {
            TextView riqi;
            TextView times;
            TextView steps;
            TextView juli;
            TextView manyi;
        }
    }
}
