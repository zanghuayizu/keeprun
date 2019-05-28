package com.example.liaodh.keeprun.view.commod;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ActivityReviewBinding;

import java.util.ArrayList;
import java.util.HashMap;

import static org.litepal.LitePalApplication.getContext;

public class ReviewDataActivity extends AppCompatActivity {
    private ActivityReviewBinding mBinding;
    private HashMap<Integer,ArrayList> mFeedBackMap;
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
        initListView();
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
        initFeedBackMap();
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
        mFeedBackMap = new HashMap<>();
        for (int i = 0;i < 30;i ++){
            ArrayList subList = new ArrayList<String>();
            for (int j = 0;j < 5; j ++){
                subList.add("j="+j);
            }
            mFeedBackMap.put(i,subList);
        }

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
            if(convertView == null) {
                convertView = View.inflate(getContext(),R.layout.review_item,null);
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

            if (list != null && list.size() > 4){
                holder.riqi.setText(list.get(0).toString());
                holder.times.setText(list.get(1).toString());
                holder.steps.setText(list.get(2).toString());
                holder.juli.setText(list.get(3).toString());
                holder.manyi.setText(list.get(4).toString());
            }
            return convertView;
        }

        private class ViewHolder{
            TextView riqi;
            TextView times;
            TextView steps;
            TextView juli;
            TextView manyi;
        }
    }
}
