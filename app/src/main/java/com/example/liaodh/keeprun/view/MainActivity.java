package com.example.liaodh.keeprun.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private String fragmentTag;
    private TextView main,user,run;
    private TextView lastTextView;
    private TextView currTextView;
    private Fragment mainFragment = new MainFragment(),
            userFragment = new UserFragment(),
            runFragment = new RunFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  第一步：创建4个继承自android.support.v4.app.Fragment的java文件,并一起创建对应的布局文件
        /**
           1）：在mainActivity.java文件同级目录新建名为fragment的目录    （用来放4个java文件）
           2）：新建文件时，一定要继承自Fragment（是android.support.v4包下的）
           3）：创建对应的布局文件。
           4）：为fragment文件绑定布局文件，通过重写方法onCreateView方法
         */
        //  第二步：添加所有Fragment到管理类去。（添加前先实列化好）
        this.getSupportFragmentManager()                                       // 获取管理类
                .beginTransaction()                                            // 开启事物
                .add(R.id.main_content, mainFragment, "mainFragment")       // 添加fragment
                .add(R.id.main_content, runFragment, "runFragment")         // 添加fragment
                .add(R.id.main_content, userFragment, "userFragment")       // 添加fragment
                .hide(runFragment)
                .hide(userFragment)
                .commit();                                                    // 提交
        this.fragmentTag = "mainFragment";                                    // 保存当前显示的Tag
        this.lastTextView = findViewById(R.id.main);                          // 保存上一次点击的底部按钮
        //  第三步：要能过点击按钮显示不同的Fragment当然得先监听按钮事件
        initView();
        //  第四步：创建一个继承自FragmentActivity的类，并定义一个方法来实现Fragment页面间的切换
        //  第五步：在当前Activity中创建一个方法，调用内部类的页面切换方法。并保存当前Tag
        //  第六步：fragment中控制页面切换（去对应Java文件中设置监听事件，调用switchFragment方法）
    }

    /**
     * 初始化4个按钮，并绑定监听器
     */
    private void initView() {
        main =  findViewById(R.id.main);
        run  =  findViewById(R.id.run);
        user =  findViewById(R.id.user);
        main.setOnClickListener(this);
        run.setOnClickListener(this);
        user.setOnClickListener(this);
        if (!SpUserInfoUtil.isUserLogin()){
            LoginDialogFirstFragment firstFragment = new LoginDialogFirstFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            firstFragment.show(ft,TAG);
        }
    }

    /**
     * 监听器对应点击方法
     */
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main:
                if (mainFragment != null){
                    this.getSupportFragmentManager().beginTransaction().attach(mainFragment).commit();
                }
                this.switchFragment(v,"mainFragment");
                break;
            case R.id.run:
                this.switchFragment(v,"runFragment");
                break;
            case R.id.user:
                this.switchFragment(v,"userFragment");
                break;
        }
    }

    /**
     * 主Activity切换Fragment页面方法入口
     *
     * @param v
     * @param toTag 要显示的Fragment的Tag
     */
    public void switchFragment(View v, String toTag) {
        this.currTextView = (TextView) v;
        currTextView.setTextColor(Color.parseColor("#ffffff"));
        lastTextView.setTextColor(Color.parseColor("#605e5e"));
        MyFragmentActivity mf = new MyFragmentActivity();
        mf.switchFragment(getSupportFragmentManager(), toTag, this.fragmentTag);
        this.lastTextView = (TextView) v;
        this.fragmentTag = toTag;
    }

    /**
     *  创建一个继承自FragmentActivity的类，并定义一个方法来实现Fragment页面间的切换
     */
    public class MyFragmentActivity extends FragmentActivity {
        // 定义一个方法：实现Fragment页面间的切换
        public void switchFragment(FragmentManager fm, String toTag, String foTag) {
            Fragment fo = fm.findFragmentByTag(foTag);
            Fragment to = fm.findFragmentByTag(toTag);
            if (fo != null && to != null && fo != to) {
                fm.beginTransaction().hide(fo).show(to).commit();
            }
        }
    }
}
