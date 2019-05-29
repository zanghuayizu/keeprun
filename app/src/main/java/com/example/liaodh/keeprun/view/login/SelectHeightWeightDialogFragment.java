package com.example.liaodh.keeprun.view.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentSelectHeightWeightBinding;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.commod.CommonToast;

import java.util.HashMap;


public class SelectHeightWeightDialogFragment extends BaseDialogFragment {


    private FragmentSelectHeightWeightBinding mBinding;
    private HashMap<Integer,Integer> mFeedBackMap;
    private int mCurrentCheck = -1;
    private MyAdapter mAdapter;
    private LoginDialogSecondFragment.setSelect mSelectListener;
    private int userSelectType = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_height_weight,container,false);
        initTitle();
        initListener();
        initListView();
       return mBinding.getRoot();
    }

    private void initTitle() {
        if (userSelectType == 1){
            mBinding.tvTitle.setText("身高选择 cm");
        }else if (userSelectType == 2){
            mBinding.tvTitle.setText("体重选择 kg");
        }else {
            mBinding.tvTitle.setText("步伐大小 cm");
        }
    }

    private void initListener() {
        mBinding.ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBinding.tvSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentCheck == -1) {
                    CommonToast.showShortToast("还没有选择哦");
                } else {
                    dismiss();
                    mSelectListener.setSelect(mCurrentCheck);
                }
            }
        });
    }

    private void initListView() {
        initFeedBackMap();
        mAdapter = new MyAdapter();
        mBinding.listFeedback.setAdapter(mAdapter);
        mBinding.listFeedback.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentCheck = position;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initFeedBackMap() {
        mFeedBackMap = new HashMap<Integer, Integer>();
        if (userSelectType == 1){
            for (int i = 0;i <= 126;i++) {
                mFeedBackMap.put(i, i+100);
            }
        }else if (userSelectType == 2){
            for (int i = 0;i <= 120;i++) {
                mFeedBackMap.put(i, i+30);
            }
        }else {
            for (int i = 5;i <= 20;i++) {
                mFeedBackMap.put(i-5, i*10);
            }
        }
    }

    public void setSelectListener(int i, LoginDialogSecondFragment.setSelect selectListener) {
        userSelectType = i;
        mSelectListener = selectListener;
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
            ViewHolder holder = null;
            if(convertView == null) {
                convertView = View.inflate(getContext(),R.layout.view_item_select,null);
                holder = new ViewHolder();
                holder.mImg = convertView.findViewById(R.id.img);
                holder.mText = convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mText.setText(String.valueOf(mFeedBackMap.get(position)));
            if(position == mCurrentCheck) {
                holder.mImg.setImageResource(R.drawable.ic_checked_feedback);
            } else {
                holder.mImg.setImageResource(R.drawable.ic_normal_feedback);
            }

            return convertView;
        }

        private class ViewHolder{
            ImageView mImg;
            TextView mText;
        }
    }
}
