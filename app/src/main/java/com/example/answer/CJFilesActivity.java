package com.example.answer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.example.answer.bean.TabEntity;
import com.example.answer.fragment.CJFileListFragment;
import com.example.answer.util.ACache;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CJFilesActivity extends AppCompatActivity {

    @Bind(R.id.main_pager)
    public ViewPager mViewPager;
    @Bind(R.id.main_tablayout)
    public CommonTabLayout mTabLayout;
    @Bind(R.id.title)
    TextView title;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private String[] mTitles = {"经济法基础", "初级会计实务"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] mIconSelectIds = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private ACache mACache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);
        mACache = ACache.get(this);
        title.setText("初级会计题库");
        if (TextUtils.isEmpty(mACache.getAsString(getIMIE() + "b"))) {
            Intent intent = new Intent(this, CJLockActivity.class);
            startActivity(intent);
            finish();
        } else {
            initDataBind();
        }

    }

    private String getIMIE() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


    protected void initDataBind() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        mFragments.add(CJFileListFragment.newInstance("1"));
        mFragments.add(CJFileListFragment.newInstance("2"));
        mFragments.add(CJFileListFragment.newInstance("3"));

        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {

                }
            }
        });

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public Context getThisContext() {
        return this;
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
