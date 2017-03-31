package com.example.answer.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    public static final int REQUEST_CODE = 100;
    public static final int REQUEST_DATA = 101;
    public static final int REQUEST_IMAGE = 102;
    public static final int REQUEST_FILE = 103;
    public static final int REQUEST_HANDWRITTEN = 110;
    public static final int REQUEST_AUDIO = 104;
    public static final int REQUEST_VIDEO = 105;
    public static final int REQUEST_MAP = 106;
    public static final int REQUEST_REFRESH = 108;
    /**
     * EventBus配置
     */
    public static String EVENTBUS_TAG_FRAGMENT = "EVENTBUS_TAG_FRAGMENT";//举报

    public static String EVENTBUS_TAG_COMPLAIN = "EVENTBUS_TAG_COMPLAIN";//举报
    public static String EVENTBUS_TAG_COMPLAIN_LIST = "EVENTBUS_TAG_COMPLAIN_LIST";//举报列表
    public static String EVENTBUS_TAG_COMPLAIN_ADD = "EVENTBUS_TAG_COMPLAIN_ADD";//举报添加
    public static String EVENTBUS_TAG_COMPLAIN_SHOW = "EVENTBUS_TAG_COMPLAIN_SHOW";//举报显示
    public static String EVENTBUS_TAG_TEST = "EVENTBUS_TAG_TEST";//测试
    public static String EVENTBUS_TAG_FRAGMENT_EXIT = "EVENTBUS_TAG_FRAGMENT_EXIT";//Fragment退出
    public static String EVENTBUS_TAG_EXIT = "EVENTBUS_TAG_EXIT";//退出
    public static String EVENTBUS_TAG_MAINTITLE = "EVENTBUS_TAG_MAINTITLE";//修改主页标题

    /*系统弹出框*/
    /*分页相关*/
    /* 每页最大分页数量 */
    public int mPageSize = 20;
    public int mMaxPageSize = 50000;
    /* 当前页码 */
    public int mPageIndex = 1;
    /* 按日期分页需要 */
    public String mPageDate = "";
    public String mSearchFilter = "";

    /*系统基础数据*/
    public String mTitle;
    public String mUrlKey;
    public String mToken;
    public String mDeptSID;
    public String mOSDeptID;
    public String mUserSID = "";
    public String mUserName = "";
    public String mUserDeptID = "";
    public String mUserDeptName = "";

    /*工具类初始化*/
    public Context mContext;

    public View rootView;

    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mContext = this.getActivity();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataBind();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }


    protected abstract int getLayoutResId();

    protected abstract void initDataBind();


    /**
     * 获取context
     *
     * @return
     */
    public Context getThisContext() {
        return getActivity();
    }

    /**
     * 跳转到指定页面
     *
     * @param cls
     */
    public void goToActivity(Class<?> cls) {
        goToActivity(cls, null);
    }

    public void goToActivity(Class<?> cls, HashMap<String, Object> filter) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (filter != null) {
            intent.putExtra("filter", filter);
        }
        getActivity().startActivity(intent);

    }


    public void goToActivityForResult(Class<?> cls, int code) {
        goToActivityForResult(cls, code, null);
    }

    public void goToActivityForResult(Class<?> cls, int code,
                                      HashMap<String, Object> filter) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        if (filter != null) {
            intent.putExtra("filter", filter);
        }
        startActivityForResult(intent, code);

    }

    /**
     * 关闭Activity
     */
    public void CloseActivity() {
        getActivity().finish();

    }

    public void CloseActivityForResult(HashMap<String, Object> mode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mode", mode);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        // 返回intent
        getActivity().setResult(getActivity().RESULT_OK, intent);
        getActivity().finish();

    }


    /**
     * 获取String.xml内容
     *
     * @param id
     * @return
     */
    public String getRString(int id) {
        return getResources().getString(id);
    }


    /**
     * 获取EditText内容
     *
     * @param et
     * @return
     */
    public String getTextString(EditText et) {
        return et.getText().toString().trim();
    }


    public void setText(TextView tv, Object obj) {
        String value = String.valueOf(obj);
        String text = "";
        if (!TextUtils.isEmpty(value) && !value.toUpperCase().equals("NULL")) {
            text = value;

        }
        tv.setText(text);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    /**
     * 是否是平板
     *
     * @param context
     * @return
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 是否横屏
     *
     * @return
     */
    public boolean isScreenChange() {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {

//横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {

//竖屏
            return false;
        }
        return false;
    }

}