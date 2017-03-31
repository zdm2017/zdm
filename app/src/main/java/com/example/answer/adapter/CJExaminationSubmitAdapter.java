package com.example.answer.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.CJAnalogyExaminationActivity;
import com.example.answer.MyErrorQuestionActivity;
import com.example.answer.R;
import com.example.answer.R.drawable;
import com.example.answer.R.id;
import com.example.answer.bean.AnSwerInfo;
import com.example.answer.bean.SaveQuestionInfo;
import com.example.answer.database.DBManager;
import com.example.answer.util.ConstantUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kennyc.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CJExaminationSubmitAdapter extends PagerAdapter {

    CJAnalogyExaminationActivity mContext;
    // 传递过来的页面view的集合
    List<View> viewItems;
    // 每个item的页面view
    View convertView;
    // 传递过来的所有数据
    List<AnSwerInfo> dataItems;

    String imgServerUrl = "";
    String dbPath="";

    private Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
    private Map<Integer, Boolean> mapClick = new HashMap<Integer, Boolean>();
    private Map<Integer, String> mapMultiSelect = new HashMap<Integer, String>();

    boolean isClick = false;

    boolean isNext = false;

    StringBuffer answer = new StringBuffer();
    StringBuffer answerLast = new StringBuffer();
    StringBuffer answer1 = new StringBuffer();

    DBManager dbManager;

    String isCorrect = ConstantUtil.isCorrect;//1对，0错

    int errortopicNum = 0;

    String resultA = "";
    String resultB = "";
    String resultC = "";
    String resultD = "";
    String resultE = "";

    public CJExaminationSubmitAdapter(CJAnalogyExaminationActivity context, List<View> viewItems, List<AnSwerInfo> dataItems, String imgServerUrl) {
        mContext = context;
        this.viewItems = viewItems;
        this.dataItems = dataItems;
        this.imgServerUrl = imgServerUrl;
        dbManager = new DBManager(context);
        dbManager.openDB();
    }
    public CJExaminationSubmitAdapter(CJAnalogyExaminationActivity context, List<View> viewItems, List<AnSwerInfo> dataItems, String imgServerUrl,String dbPath) {
        mContext = context;
        this.viewItems = viewItems;
        this.dataItems = dataItems;
        this.imgServerUrl = imgServerUrl;
        this.dbPath=dbPath;
        dbManager = new DBManager(context);
        dbManager.openDB();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ViewHolder holder = new ViewHolder();
        convertView = viewItems.get(position);
        holder.questionType = (TextView) convertView.findViewById(id.activity_prepare_test_no);
        holder.question = (TextView) convertView.findViewById(id.activity_prepare_test_question);

        holder.totalBtn = (LinearLayout) convertView.findViewById(id.activity_prepare_test_totalLayout);
        holder.previousBtn = (LinearLayout) convertView.findViewById(id.activity_prepare_test_upLayout);
        holder.nextBtn = (LinearLayout) convertView.findViewById(id.activity_prepare_test_nextLayout);
        holder.nextText = (TextView) convertView.findViewById(id.menu_bottom_nextTV);
        holder.errorBtn = (LinearLayout) convertView.findViewById(id.activity_prepare_test_errorLayout);
        holder.errorBtn.setVisibility(View.GONE);
        holder.submitBtn = (LinearLayout) convertView.findViewById(id.activity_prepare_test_submitLayout);
        holder.totalText = (TextView) convertView.findViewById(id.activity_prepare_test_totalTv);
        holder.nextImage = (ImageView) convertView.findViewById(id.menu_bottom_nextIV);
        holder.wrongLayout = (LinearLayout) convertView.findViewById(id.activity_prepare_test_wrongLayout);
        holder.explaindetailTv = (TextView) convertView.findViewById(id.activity_prepare_test_explaindetail);
        holder.layoutA = (LinearLayout) convertView.findViewById(id.activity_prepare_test_layout_a);
        holder.layoutB = (LinearLayout) convertView.findViewById(id.activity_prepare_test_layout_b);
        holder.layoutC = (LinearLayout) convertView.findViewById(id.activity_prepare_test_layout_c);
        holder.layoutD = (LinearLayout) convertView.findViewById(id.activity_prepare_test_layout_d);
        holder.layoutE = (LinearLayout) convertView.findViewById(id.activity_prepare_test_layout_e);
        holder.ivA = (ImageView) convertView.findViewById(id.vote_submit_select_image_a);
        holder.ivB = (ImageView) convertView.findViewById(id.vote_submit_select_image_b);
        holder.ivC = (ImageView) convertView.findViewById(id.vote_submit_select_image_c);
        holder.ivD = (ImageView) convertView.findViewById(id.vote_submit_select_image_d);
        holder.ivE = (ImageView) convertView.findViewById(id.vote_submit_select_image_e);
        holder.tvA = (TextView) convertView.findViewById(id.vote_submit_select_text_a);
        holder.tvB = (TextView) convertView.findViewById(id.vote_submit_select_text_b);
        holder.tvC = (TextView) convertView.findViewById(id.vote_submit_select_text_c);
        holder.tvD = (TextView) convertView.findViewById(id.vote_submit_select_text_d);
        holder.tvE = (TextView) convertView.findViewById(id.vote_submit_select_text_e);
        holder.ivA_ = (ImageView) convertView.findViewById(id.vote_submit_select_image_a_);
        holder.ivB_ = (ImageView) convertView.findViewById(id.vote_submit_select_image_b_);
        holder.ivC_ = (ImageView) convertView.findViewById(id.vote_submit_select_image_c_);
        holder.ivD_ = (ImageView) convertView.findViewById(id.vote_submit_select_image_d_);
        holder.ivE_ = (ImageView) convertView.findViewById(id.vote_submit_select_image_e_);


        holder.rg01 = (LinearLayout) convertView.findViewById(id.radioGroup1_01);
        holder.cb1_01 = (CheckBox) convertView.findViewById(id.radioBtn1_01);
        holder.cb1_02 = (CheckBox) convertView.findViewById(id.radioBtn1_02);
        holder.cb1_03 = (CheckBox) convertView.findViewById(id.radioBtn1_03);
        holder.cb1_04 = (CheckBox) convertView.findViewById(id.radioBtn1_04);

        holder.rg02 = (LinearLayout) convertView.findViewById(id.radioGroup2_01);
        holder.cb2_01 = (CheckBox) convertView.findViewById(id.radioBtn2_01);
        holder.cb2_02 = (CheckBox) convertView.findViewById(id.radioBtn2_02);
        holder.cb2_03 = (CheckBox) convertView.findViewById(id.radioBtn2_03);
        holder.cb2_04 = (CheckBox) convertView.findViewById(id.radioBtn2_04);

        holder.rg03 = (LinearLayout) convertView.findViewById(id.radioGroup3_01);
        holder.cb3_01 = (CheckBox) convertView.findViewById(id.radioBtn3_01);
        holder.cb3_02 = (CheckBox) convertView.findViewById(id.radioBtn3_02);
        holder.cb3_03 = (CheckBox) convertView.findViewById(id.radioBtn3_03);
        holder.cb3_04 = (CheckBox) convertView.findViewById(id.radioBtn3_04);

        holder.rg04 = (LinearLayout) convertView.findViewById(id.radioGroup4_01);
        holder.cb4_01 = (CheckBox) convertView.findViewById(id.radioBtn4_01);
        holder.cb4_02 = (CheckBox) convertView.findViewById(id.radioBtn4_02);
        holder.cb4_03 = (CheckBox) convertView.findViewById(id.radioBtn4_03);
        holder.cb4_04 = (CheckBox) convertView.findViewById(id.radioBtn4_04);

        holder.rg05 = (LinearLayout) convertView.findViewById(id.radioGroup5_01);
        holder.cb5_01 = (CheckBox) convertView.findViewById(id.radioBtn5_01);
        holder.cb5_02 = (CheckBox) convertView.findViewById(id.radioBtn5_02);
        holder.cb5_03 = (CheckBox) convertView.findViewById(id.radioBtn5_03);
        holder.cb5_04 = (CheckBox) convertView.findViewById(id.radioBtn5_04);


        holder.result01 = (TextView) convertView.findViewById(id.result_01);
        holder.result02 = (TextView) convertView.findViewById(id.result_02);
        holder.result03 = (TextView) convertView.findViewById(id.result_03);
        holder.result04 = (TextView) convertView.findViewById(id.result_04);
        holder.result05 = (TextView) convertView.findViewById(id.result_05);
        holder.btnMakeFXT = (Button) convertView.findViewById(id.btn_make_fxt);

        holder.sp01_1 = (Spinner) convertView.findViewById(id.spinner01_01);
        holder.sp01_2 = (Spinner) convertView.findViewById(id.spinner01_02);
        holder.jst01 = (EditText) convertView.findViewById(id.jst01_01);

        holder.sp01_1_2 = (Spinner) convertView.findViewById(id.spinner01_01_02);
        holder.sp01_2_2 = (Spinner) convertView.findViewById(id.spinner01_02_02);
        holder.jst01_2 = (EditText) convertView.findViewById(id.jst01_02);
        holder.sp01_1_3 = (Spinner) convertView.findViewById(id.spinner01_01_03);
        holder.sp01_2_3 = (Spinner) convertView.findViewById(id.spinner01_02_03);
        holder.jst01_3 = (EditText) convertView.findViewById(id.jst01_03);
        holder.sp01_1_4 = (Spinner) convertView.findViewById(id.spinner01_01_04);
        holder.sp01_2_4 = (Spinner) convertView.findViewById(id.spinner01_02_04);
        holder.jst01_4 = (EditText) convertView.findViewById(id.jst01_04);
        holder.sp01_1_5 = (Spinner) convertView.findViewById(id.spinner01_01_05);
        holder.sp01_2_5 = (Spinner) convertView.findViewById(id.spinner01_02_05);
        holder.jst01_5 = (EditText) convertView.findViewById(id.jst01_05);
        holder.sp01_1_6 = (Spinner) convertView.findViewById(id.spinner01_01_06);
        holder.sp01_2_6 = (Spinner) convertView.findViewById(id.spinner01_02_06);
        holder.jst01_6 = (EditText) convertView.findViewById(id.jst01_06);

        holder.sp02_1 = (Spinner) convertView.findViewById(id.spinner02_01);
        holder.sp02_2 = (Spinner) convertView.findViewById(id.spinner02_02);
        holder.jst02 = (EditText) convertView.findViewById(id.jst02_01);

        holder.sp02_1_2 = (Spinner) convertView.findViewById(id.spinner02_01_02);
        holder.sp02_2_2 = (Spinner) convertView.findViewById(id.spinner02_02_02);
        holder.jst02_2 = (EditText) convertView.findViewById(id.jst02_02);
        holder.sp02_1_3 = (Spinner) convertView.findViewById(id.spinner02_01_03);
        holder.sp02_2_3 = (Spinner) convertView.findViewById(id.spinner02_02_03);
        holder.jst02_3 = (EditText) convertView.findViewById(id.jst02_03);
        holder.sp02_1_4 = (Spinner) convertView.findViewById(id.spinner02_01_04);
        holder.sp02_2_4 = (Spinner) convertView.findViewById(id.spinner02_02_04);
        holder.jst02_4 = (EditText) convertView.findViewById(id.jst02_04);
        holder.sp02_1_5 = (Spinner) convertView.findViewById(id.spinner02_01_05);
        holder.sp02_2_5 = (Spinner) convertView.findViewById(id.spinner02_02_05);
        holder.jst02_5 = (EditText) convertView.findViewById(id.jst02_05);
        holder.sp02_1_6 = (Spinner) convertView.findViewById(id.spinner02_01_06);
        holder.sp02_2_6 = (Spinner) convertView.findViewById(id.spinner02_02_06);
        holder.jst02_6 = (EditText) convertView.findViewById(id.jst02_06);

        holder.sp03_1 = (Spinner) convertView.findViewById(id.spinner03_01);
        holder.sp03_2 = (Spinner) convertView.findViewById(id.spinner03_02);
        holder.jst03 = (EditText) convertView.findViewById(id.jst03_01);

        holder.sp03_1_2 = (Spinner) convertView.findViewById(id.spinner03_01_02);
        holder.sp03_2_2 = (Spinner) convertView.findViewById(id.spinner03_02_02);
        holder.jst03_2 = (EditText) convertView.findViewById(id.jst03_02);
        holder.sp03_1_3 = (Spinner) convertView.findViewById(id.spinner03_01_03);
        holder.sp03_2_3 = (Spinner) convertView.findViewById(id.spinner03_02_03);
        holder.jst03_3 = (EditText) convertView.findViewById(id.jst03_03);
        holder.sp03_1_4 = (Spinner) convertView.findViewById(id.spinner03_01_04);
        holder.sp03_2_4 = (Spinner) convertView.findViewById(id.spinner03_02_04);
        holder.jst03_4 = (EditText) convertView.findViewById(id.jst03_04);
        holder.sp03_1_5 = (Spinner) convertView.findViewById(id.spinner03_01_05);
        holder.sp03_2_5 = (Spinner) convertView.findViewById(id.spinner03_02_05);
        holder.jst03_5 = (EditText) convertView.findViewById(id.jst03_05);
        holder.sp03_1_6 = (Spinner) convertView.findViewById(id.spinner03_01_06);
        holder.sp03_2_6 = (Spinner) convertView.findViewById(id.spinner03_02_06);
        holder.jst03_6 = (EditText) convertView.findViewById(id.jst03_06);


        holder.sp04_1 = (Spinner) convertView.findViewById(id.spinner04_01);
        holder.sp04_2 = (Spinner) convertView.findViewById(id.spinner04_02);
        holder.jst04 = (EditText) convertView.findViewById(id.jst04_01);

        holder.sp04_1_2 = (Spinner) convertView.findViewById(id.spinner04_01_02);
        holder.sp04_2_2 = (Spinner) convertView.findViewById(id.spinner04_02_02);
        holder.jst04_2 = (EditText) convertView.findViewById(id.jst04_02);
        holder.sp04_1_3 = (Spinner) convertView.findViewById(id.spinner04_01_03);
        holder.sp04_2_3 = (Spinner) convertView.findViewById(id.spinner04_02_03);
        holder.jst04_3 = (EditText) convertView.findViewById(id.jst04_03);
        holder.sp04_1_4 = (Spinner) convertView.findViewById(id.spinner04_01_04);
        holder.sp04_2_4 = (Spinner) convertView.findViewById(id.spinner04_02_04);
        holder.jst04_4 = (EditText) convertView.findViewById(id.jst04_04);
        holder.sp04_1_5 = (Spinner) convertView.findViewById(id.spinner04_01_05);
        holder.sp04_2_5 = (Spinner) convertView.findViewById(id.spinner04_02_05);
        holder.jst04_5 = (EditText) convertView.findViewById(id.jst04_05);
        holder.sp04_1_6 = (Spinner) convertView.findViewById(id.spinner04_01_06);
        holder.sp04_2_6 = (Spinner) convertView.findViewById(id.spinner04_02_06);
        holder.jst04_6 = (EditText) convertView.findViewById(id.jst04_06);

        holder.sp05_1 = (Spinner) convertView.findViewById(id.spinner05_01);
        holder.sp05_2 = (Spinner) convertView.findViewById(id.spinner05_02);
        holder.jst05 = (EditText) convertView.findViewById(id.jst05_01);

        holder.sp05_1_2 = (Spinner) convertView.findViewById(id.spinner05_01_02);
        holder.sp05_2_2 = (Spinner) convertView.findViewById(id.spinner05_02_02);
        holder.jst05_2 = (EditText) convertView.findViewById(id.jst05_02);
        holder.sp05_1_3 = (Spinner) convertView.findViewById(id.spinner05_01_03);
        holder.sp05_2_3 = (Spinner) convertView.findViewById(id.spinner05_02_03);
        holder.jst05_3 = (EditText) convertView.findViewById(id.jst05_03);
        holder.sp05_1_4 = (Spinner) convertView.findViewById(id.spinner05_01_04);
        holder.sp05_2_4 = (Spinner) convertView.findViewById(id.spinner05_02_04);
        holder.jst05_4 = (EditText) convertView.findViewById(id.jst05_04);
        holder.sp05_1_5 = (Spinner) convertView.findViewById(id.spinner05_01_05);
        holder.sp05_2_5 = (Spinner) convertView.findViewById(id.spinner05_02_05);
        holder.jst05_5 = (EditText) convertView.findViewById(id.jst05_05);
        holder.sp05_1_6 = (Spinner) convertView.findViewById(id.spinner05_01_06);
        holder.sp05_2_6 = (Spinner) convertView.findViewById(id.spinner05_02_06);
        holder.jst05_6 = (EditText) convertView.findViewById(id.jst05_06);

        holder.btnMakeJST = (Button) convertView.findViewById(id.btn_make_jst);


        holder.ll0102 = (LinearLayout) convertView.findViewById(id.ll_01_02);
        holder.ll0103 = (LinearLayout) convertView.findViewById(id.ll_01_03);
        holder.ll0104 = (LinearLayout) convertView.findViewById(id.ll_01_04);
        holder.ll0105 = (LinearLayout) convertView.findViewById(id.ll_01_05);
        holder.ll0106 = (LinearLayout) convertView.findViewById(id.ll_01_06);

        holder.ll0202 = (LinearLayout) convertView.findViewById(id.ll_02_02);
        holder.ll0203 = (LinearLayout) convertView.findViewById(id.ll_02_03);
        holder.ll0204 = (LinearLayout) convertView.findViewById(id.ll_02_04);
        holder.ll0205 = (LinearLayout) convertView.findViewById(id.ll_02_05);
        holder.ll0206 = (LinearLayout) convertView.findViewById(id.ll_02_06);

        holder.ll0302 = (LinearLayout) convertView.findViewById(id.ll_03_02);
        holder.ll0303 = (LinearLayout) convertView.findViewById(id.ll_03_03);
        holder.ll0304 = (LinearLayout) convertView.findViewById(id.ll_03_04);
        holder.ll0305 = (LinearLayout) convertView.findViewById(id.ll_03_05);
        holder.ll0306 = (LinearLayout) convertView.findViewById(id.ll_03_06);

        holder.ll0402 = (LinearLayout) convertView.findViewById(id.ll_04_02);
        holder.ll0403 = (LinearLayout) convertView.findViewById(id.ll_04_03);
        holder.ll0404 = (LinearLayout) convertView.findViewById(id.ll_04_04);
        holder.ll0405 = (LinearLayout) convertView.findViewById(id.ll_04_05);
        holder.ll0406 = (LinearLayout) convertView.findViewById(id.ll_04_06);

        holder.ll0502 = (LinearLayout) convertView.findViewById(id.ll_05_02);
        holder.ll0503 = (LinearLayout) convertView.findViewById(id.ll_05_03);
        holder.ll0504 = (LinearLayout) convertView.findViewById(id.ll_05_04);
        holder.ll0505 = (LinearLayout) convertView.findViewById(id.ll_05_05);
        holder.ll0506 = (LinearLayout) convertView.findViewById(id.ll_05_06);

        holder.ll05Title= (TextView) convertView.findViewById(id.ll_05_title);
        holder.ll05Content= (LinearLayout) convertView.findViewById(id.ll_05_content);

        if(this.dbPath.contains("KJ1_")){
            holder.ll05Title.setVisibility(View.GONE);
            holder.ll05Content.setVisibility(View.GONE);
        }else{
            holder.ll05Title.setVisibility(View.VISIBLE);
            holder.ll05Content.setVisibility(View.VISIBLE);
        }

        holder.btn01 = (Button) convertView.findViewById(id.btn_01);
        holder.btn01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ll0102.getVisibility() == View.GONE) {
                    holder.ll0102.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0103.getVisibility() == View.GONE) {
                    holder.ll0103.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0104.getVisibility() == View.GONE) {
                    holder.ll0104.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0105.getVisibility() == View.GONE) {
                    holder.ll0105.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0106.getVisibility() == View.GONE) {
                    holder.ll0106.setVisibility(View.VISIBLE);
                    return;
                }

            }
        });
        holder.btn02 = (Button) convertView.findViewById(id.btn_02);
        holder.btn02.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ll0202.getVisibility() == View.GONE) {
                    holder.ll0202.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0203.getVisibility() == View.GONE) {
                    holder.ll0203.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0204.getVisibility() == View.GONE) {
                    holder.ll0204.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0205.getVisibility() == View.GONE) {
                    holder.ll0205.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0206.getVisibility() == View.GONE) {
                    holder.ll0206.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });
        holder.btn03 = (Button) convertView.findViewById(id.btn_03);
        holder.btn03.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ll0302.getVisibility() == View.GONE) {
                    holder.ll0302.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0303.getVisibility() == View.GONE) {
                    holder.ll0303.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0304.getVisibility() == View.GONE) {
                    holder.ll0304.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0305.getVisibility() == View.GONE) {
                    holder.ll0305.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0306.getVisibility() == View.GONE) {
                    holder.ll0306.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });
        holder.btn04 = (Button) convertView.findViewById(id.btn_04);
        holder.btn04.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ll0402.getVisibility() == View.GONE) {
                    holder.ll0402.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0403.getVisibility() == View.GONE) {
                    holder.ll0403.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0404.getVisibility() == View.GONE) {
                    holder.ll0404.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0405.getVisibility() == View.GONE) {
                    holder.ll0405.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0506.getVisibility() == View.GONE) {
                    holder.ll0506.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });
        holder.btn05 = (Button) convertView.findViewById(id.btn_05);
        holder.btn05.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ll0502.getVisibility() == View.GONE) {
                    holder.ll0502.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0503.getVisibility() == View.GONE) {
                    holder.ll0503.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0504.getVisibility() == View.GONE) {
                    holder.ll0504.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0505.getVisibility() == View.GONE) {
                    holder.ll0505.setVisibility(View.VISIBLE);
                    return;
                }
                if (holder.ll0506.getVisibility() == View.GONE) {
                    holder.ll0506.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });


        String[] mItems01 = mContext.getResources().getStringArray(R.array.sp_01);
        String[] mItems02 = mContext.getResources().getStringArray(R.array.sp_02);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter01 = new ArrayAdapter<String>(mContext, R.layout.list_item_sp, mItems01);
        ArrayAdapter<String> _Adapter02 = new ArrayAdapter<String>(mContext, R.layout.list_item_sp, mItems02);
        //绑定 Adapter到控件
        holder.sp01_1.setAdapter(_Adapter01);
        holder.sp01_2.setAdapter(_Adapter02);
        holder.sp01_1_2.setAdapter(_Adapter01);
        holder.sp01_2_2.setAdapter(_Adapter02);
        holder.sp01_1_3.setAdapter(_Adapter01);
        holder.sp01_2_3.setAdapter(_Adapter02);
        holder.sp01_1_4.setAdapter(_Adapter01);
        holder.sp01_2_4.setAdapter(_Adapter02);
        holder.sp01_1_5.setAdapter(_Adapter01);
        holder.sp01_2_5.setAdapter(_Adapter02);
        holder.sp01_1_6.setAdapter(_Adapter01);
        holder.sp01_2_6.setAdapter(_Adapter02);

        holder.sp02_1.setAdapter(_Adapter01);
        holder.sp02_2.setAdapter(_Adapter02);
        holder.sp02_1_2.setAdapter(_Adapter01);
        holder.sp02_2_2.setAdapter(_Adapter02);
        holder.sp02_1_3.setAdapter(_Adapter01);
        holder.sp02_2_3.setAdapter(_Adapter02);
        holder.sp02_1_4.setAdapter(_Adapter01);
        holder.sp02_2_4.setAdapter(_Adapter02);
        holder.sp02_1_5.setAdapter(_Adapter01);
        holder.sp02_2_5.setAdapter(_Adapter02);
        holder.sp02_1_6.setAdapter(_Adapter01);
        holder.sp02_2_6.setAdapter(_Adapter02);

        holder.sp03_1.setAdapter(_Adapter01);
        holder.sp03_2.setAdapter(_Adapter02);
        holder.sp03_1_2.setAdapter(_Adapter01);
        holder.sp03_2_2.setAdapter(_Adapter02);
        holder.sp03_1_3.setAdapter(_Adapter01);
        holder.sp03_2_3.setAdapter(_Adapter02);
        holder.sp03_1_4.setAdapter(_Adapter01);
        holder.sp03_2_4.setAdapter(_Adapter02);
        holder.sp03_1_5.setAdapter(_Adapter01);
        holder.sp03_2_5.setAdapter(_Adapter02);
        holder.sp03_1_6.setAdapter(_Adapter01);
        holder.sp03_2_6.setAdapter(_Adapter02);

        holder.sp04_1.setAdapter(_Adapter01);
        holder.sp04_2.setAdapter(_Adapter02);
        holder.sp04_1_2.setAdapter(_Adapter01);
        holder.sp04_2_2.setAdapter(_Adapter02);
        holder.sp04_1_3.setAdapter(_Adapter01);
        holder.sp04_2_3.setAdapter(_Adapter02);
        holder.sp04_1_4.setAdapter(_Adapter01);
        holder.sp04_2_4.setAdapter(_Adapter02);
        holder.sp04_1_5.setAdapter(_Adapter01);
        holder.sp04_2_5.setAdapter(_Adapter02);
        holder.sp04_1_6.setAdapter(_Adapter01);
        holder.sp04_2_6.setAdapter(_Adapter02);

        holder.sp05_1.setAdapter(_Adapter01);
        holder.sp05_2.setAdapter(_Adapter02);
        holder.sp05_1_2.setAdapter(_Adapter01);
        holder.sp05_2_2.setAdapter(_Adapter02);
        holder.sp05_1_3.setAdapter(_Adapter01);
        holder.sp05_2_3.setAdapter(_Adapter02);
        holder.sp05_1_4.setAdapter(_Adapter01);
        holder.sp05_2_4.setAdapter(_Adapter02);
        holder.sp05_1_5.setAdapter(_Adapter01);
        holder.sp05_2_5.setAdapter(_Adapter02);
        holder.sp05_1_6.setAdapter(_Adapter01);
        holder.sp05_2_6.setAdapter(_Adapter02);


        holder.radioJSTButnLayout = (LinearLayout) convertView.findViewById(id.activity_jst_radioBtnLayout);
        holder.radioButnLayout = (LinearLayout) convertView.findViewById(id.activity_prepare_test_radioBtnLayout);
        holder.radioFXTButnLayout = (LinearLayout) convertView.findViewById(id.activity_fxt_radioBtnLayout);

        holder.totalText.setText(position + 1 + "/" + dataItems.size());

        holder.errorBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, MyErrorQuestionActivity.class);
                mContext.startActivity(intent);
            }
        });

      /*  if (TextUtils.isEmpty(dataItems.get(position).getOptionA())) {
            holder.layoutA.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(dataItems.get(position).getOptionB())) {
            holder.layoutB.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(dataItems.get(position).getOptionC())) {
            holder.layoutC.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(dataItems.get(position).getOptionD())) {
            holder.layoutD.setVisibility(View.GONE);
        }*/
        if (TextUtils.isEmpty(dataItems.get(position).getOptionE())) {
            holder.layoutE.setVisibility(View.GONE);
        }

        //判断是否文字图片题目
        //文字题目
      //  holder.ivA_.setVisibility(View.GONE);
      //  holder.ivB_.setVisibility(View.GONE);
      //  holder.ivC_.setVisibility(View.GONE);
      //  holder.ivD_.setVisibility(View.GONE);
      //  holder.ivE_.setVisibility(View.GONE);
     //   holder.tvA.setVisibility(View.VISIBLE);
     //   holder.tvB.setVisibility(View.VISIBLE);
     //   holder.tvC.setVisibility(View.VISIBLE);
     //   holder.tvD.setVisibility(View.VISIBLE);
      //  holder.tvE.setVisibility(View.VISIBLE);
     //   holder.tvA.setText("A." + dataItems.get(position).getOptionA());
     //   holder.tvB.setText("B." + dataItems.get(position).getOptionB());
     //   holder.tvC.setText("C." + dataItems.get(position).getOptionC());
     //   holder.tvD.setText("D." + dataItems.get(position).getOptionD());
     //   holder.tvE.setText("E." + dataItems.get(position).getOptionE());

        holder.tvA.setText("A" );
        holder.tvB.setText("B");
        holder.tvC.setText("C");
        holder.tvD.setText("D");

        holder.radioButnLayout.setVisibility(View.VISIBLE);
        holder.radioFXTButnLayout.setVisibility(View.GONE);
        holder.radioJSTButnLayout.setVisibility(View.GONE);
        //计算题
        if (dataItems.get(position).getQuestionType().equals("4")) {
            holder.radioButnLayout.setVisibility(View.GONE);
            holder.radioFXTButnLayout.setVisibility(View.GONE);
            holder.radioJSTButnLayout.setVisibility(View.VISIBLE);
            //DXTInfo
            String question = "(计算题)\r\n" + dataItems.get(position).getQuestionName();
            question += "\r\n" + dataItems.get(position).getQuestionName1()
                    .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
            question += "\r\n" + dataItems.get(position).getQuestionName2()
                    .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
            question += "\r\n" + dataItems.get(position).getQuestionName3()
                    .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
            question += "\r\n" + dataItems.get(position).getQuestionName4()
                    .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
            question += "\r\n" + dataItems.get(position).getQuestionName5()
                    .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");

            holder.question.setText(question);

            holder.btnMakeJST.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    int iScore = 0;
                    //第一大题
                    boolean bScore1 = true;
                    String answer1 = dataItems.get(position).getCorrectAnswer1().replace("：","").replace(" ","");
                    String[] answer1Array = answer1.split("\r\n");
                    for (int i = 0; i < answer1Array.length; i++) {
                        if (i == 0) {
                            //第一题第一个
                            String v1_1_1 = holder.sp01_1.getSelectedItem().toString();
                            String v1_2_1 = holder.sp01_2.getSelectedItem().toString();
                            String v1_3_1 = holder.jst01.getText().toString();
                            String aa=v1_1_1 + v1_2_1 + v1_3_1;
                            if (bScore1 && (v1_1_1 + v1_2_1 + v1_3_1).equals(answer1Array[0])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                        if (i == 1) {
                            //第一题第二个
                            String v1_1_2 = holder.sp01_1_2.getSelectedItem().toString();
                            String v1_2_2 = holder.sp01_2_2.getSelectedItem().toString();
                            String v1_3_2 = holder.jst01_2.getText().toString();
                            if (bScore1 && (v1_1_2 + v1_2_2 + v1_3_2).equals(answer1Array[1])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                        if (i == 2) {
                            //第一题第三个
                            String v1_1_3 = holder.sp01_1_3.getSelectedItem().toString();
                            String v1_2_3 = holder.sp01_2_3.getSelectedItem().toString();
                            String v1_3_3 = holder.jst01_3.getText().toString();
                            if (bScore1 && (v1_1_3 + v1_2_3 + v1_3_3).equals(answer1Array[2])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                        if (i == 3) {
                            //第一题第四个
                            String v1_1_4 = holder.sp01_1_4.getSelectedItem().toString();
                            String v1_2_4 = holder.sp01_2_4.getSelectedItem().toString();
                            String v1_3_4 = holder.jst01_4.getText().toString();
                            if (bScore1 && (v1_1_4 + v1_2_4 + v1_3_4).equals(answer1Array[3])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                        if (i == 4) {
                            //第一题第五个
                            String v1_1_5 = holder.sp01_1_5.getSelectedItem().toString();
                            String v1_2_5 = holder.sp01_2_5.getSelectedItem().toString();
                            String v1_3_5 = holder.jst01_5.getText().toString();
                            if (bScore1 && (v1_1_5 + v1_2_5 + v1_3_5).equals(answer1Array[4])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                        if (i == 5) {
                            //第一题第六个
                            String v1_1_6 = holder.sp01_1_6.getSelectedItem().toString();
                            String v1_2_6 = holder.sp01_2_6.getSelectedItem().toString();
                            String v1_3_6 = holder.jst01_6.getText().toString();
                            if (bScore1 && (v1_1_6 + v1_2_6 + v1_3_6).equals(answer1Array[5])) {
                                bScore1 = true;
                            } else {
                                bScore1 = false;
                            }
                        }
                    }
                    if (bScore1) {
                        iScore += 2;
                    } else {
                        iScore += 0;
                    }
                    //第二大题
                    boolean bScore2 = true;
                    String answer2 = dataItems.get(position).getCorrectAnswer2().replace("：","").replace(" ","");
                    String[] answer2Array = answer2.split("\r\n");
                    for (int i = 0; i < answer2Array.length; i++) {
                        if (i == 0) {
                            //第一题第一个
                            String v1_1_1 = holder.sp02_1.getSelectedItem().toString();
                            String v1_2_1 = holder.sp02_2.getSelectedItem().toString();
                            String v1_3_1 = holder.jst02.getText().toString();
                            if (bScore2 && (v1_1_1 + v1_2_1 + v1_3_1).equals(answer2Array[0])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                        if (i == 1) {
                            //第一题第二个
                            String v1_1_2 = holder.sp02_1_2.getSelectedItem().toString();
                            String v1_2_2 = holder.sp02_2_2.getSelectedItem().toString();
                            String v1_3_2 = holder.jst02_2.getText().toString();
                            if (bScore2 && (v1_1_2 + v1_2_2 + v1_3_2).equals(answer2Array[1])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                        if (i == 2) {
                            //第一题第三个
                            String v1_1_3 = holder.sp02_1_3.getSelectedItem().toString();
                            String v1_2_3 = holder.sp02_2_3.getSelectedItem().toString();
                            String v1_3_3 = holder.jst02_3.getText().toString();
                            if (bScore2 && (v1_1_3 + v1_2_3 + v1_3_3).equals(answer2Array[2])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                        if (i == 3) {
                            //第一题第四个
                            String v1_1_4 = holder.sp02_1_4.getSelectedItem().toString();
                            String v1_2_4 = holder.sp02_2_4.getSelectedItem().toString();
                            String v1_3_4 = holder.jst02_4.getText().toString();
                            if (bScore2 && (v1_1_4 + v1_2_4 + v1_3_4).equals(answer2Array[3])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                        if (i == 4) {
                            //第一题第五个
                            String v1_1_5 = holder.sp02_1_5.getSelectedItem().toString();
                            String v1_2_5 = holder.sp02_2_5.getSelectedItem().toString();
                            String v1_3_5 = holder.jst02_5.getText().toString();
                            if (bScore2 && (v1_1_5 + v1_2_5 + v1_3_5).equals(answer2Array[4])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                        if (i == 5) {
                            //第一题第六个
                            String v1_1_6 = holder.sp02_1_6.getSelectedItem().toString();
                            String v1_2_6 = holder.sp02_2_6.getSelectedItem().toString();
                            String v1_3_6 = holder.jst02_6.getText().toString();
                            if (bScore2 && (v1_1_6 + v1_2_6 + v1_3_6).equals(answer2Array[5])) {
                                bScore2 = true;
                            } else {
                                bScore2 = false;
                            }
                        }
                    }
                    if (bScore2) {
                        iScore += 2;
                    } else {
                        iScore += 0;
                    }

                    //第三大题
                    boolean bScore3 = true;
                    String answe3 = dataItems.get(position).getCorrectAnswer3().replace("：","").replace(" ","");
                    String[] answer3Array = answe3.split("\r\n");
                    for (int i = 0; i < answer3Array.length; i++) {
                        if (i == 0) {
                            //第一题第一个
                            String v1_1_1 = holder.sp03_1.getSelectedItem().toString();
                            String v1_2_1 = holder.sp03_2.getSelectedItem().toString();
                            String v1_3_1 = holder.jst03.getText().toString();
                            if (bScore3 && (v1_1_1 + v1_2_1 + v1_3_1).equals(answer3Array[0])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                        if (i == 1) {
                            //第一题第二个
                            String v1_1_2 = holder.sp03_1_2.getSelectedItem().toString();
                            String v1_2_2 = holder.sp03_2_2.getSelectedItem().toString();
                            String v1_3_2 = holder.jst03_2.getText().toString();
                            if (bScore3 && (v1_1_2 + v1_2_2 + v1_3_2).equals(answer3Array[1])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                        if (i == 2) {
                            //第一题第三个
                            String v1_1_3 = holder.sp03_1_3.getSelectedItem().toString();
                            String v1_2_3 = holder.sp03_2_3.getSelectedItem().toString();
                            String v1_3_3 = holder.jst03_3.getText().toString();
                            if (bScore3 && (v1_1_3 + v1_2_3 + v1_3_3).equals(answer3Array[2])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                        if (i == 3) {
                            //第一题第四个
                            String v1_1_4 = holder.sp03_1_4.getSelectedItem().toString();
                            String v1_2_4 = holder.sp03_2_4.getSelectedItem().toString();
                            String v1_3_4 = holder.jst03_4.getText().toString();
                            if (bScore3 && (v1_1_4 + v1_2_4 + v1_3_4).equals(answer3Array[3])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                        if (i == 4) {
                            //第一题第五个
                            String v1_1_5 = holder.sp03_1_5.getSelectedItem().toString();
                            String v1_2_5 = holder.sp03_2_5.getSelectedItem().toString();
                            String v1_3_5 = holder.jst03_5.getText().toString();
                            if (bScore3 && (v1_1_5 + v1_2_5 + v1_3_5).equals(answer3Array[4])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                        if (i == 5) {
                            //第一题第六个
                            String v1_1_6 = holder.sp03_1_6.getSelectedItem().toString();
                            String v1_2_6 = holder.sp03_2_6.getSelectedItem().toString();
                            String v1_3_6 = holder.jst03_6.getText().toString();
                            if (bScore3 && (v1_1_6 + v1_2_6 + v1_3_6).equals(answer3Array[5])) {
                                bScore3 = true;
                            } else {
                                bScore3 = false;
                            }
                        }
                    }
                    if (bScore3) {
                        iScore += 2;
                    } else {
                        iScore += 0;
                    }

                    //第四大题
                    boolean bScore4 = true;
                    String answe4 = dataItems.get(position).getCorrectAnswer4().replace("：","").replace(" ","");
                    String[] answer4Array = answe4.split("\r\n");
                    for (int i = 0; i < answer4Array.length; i++) {
                        if (i == 0) {
                            //第一题第一个
                            String v1_1_1 = holder.sp04_1.getSelectedItem().toString();
                            String v1_2_1 = holder.sp04_2.getSelectedItem().toString();
                            String v1_3_1 = holder.jst04.getText().toString();
                            if (bScore4 && (v1_1_1 + v1_2_1 + v1_3_1).equals(answer4Array[0])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                        if (i == 1) {
                            //第一题第二个
                            String v1_1_2 = holder.sp04_1_2.getSelectedItem().toString();
                            String v1_2_2 = holder.sp04_2_2.getSelectedItem().toString();
                            String v1_3_2 = holder.jst04_2.getText().toString();
                            if (bScore4 && (v1_1_2 + v1_2_2 + v1_3_2).equals(answer4Array[1])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                        if (i == 2) {
                            //第一题第三个
                            String v1_1_3 = holder.sp04_1_3.getSelectedItem().toString();
                            String v1_2_3 = holder.sp04_2_3.getSelectedItem().toString();
                            String v1_3_3 = holder.jst04_3.getText().toString();
                            if (bScore4 && (v1_1_3 + v1_2_3 + v1_3_3).equals(answer4Array[2])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                        if (i == 3) {
                            //第一题第四个
                            String v1_1_4 = holder.sp04_1_4.getSelectedItem().toString();
                            String v1_2_4 = holder.sp04_2_4.getSelectedItem().toString();
                            String v1_3_4 = holder.jst04_4.getText().toString();
                            if (bScore4 && (v1_1_4 + v1_2_4 + v1_3_4).equals(answer4Array[3])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                        if (i == 4) {
                            //第一题第五个
                            String v1_1_5 = holder.sp04_1_5.getSelectedItem().toString();
                            String v1_2_5 = holder.sp04_2_5.getSelectedItem().toString();
                            String v1_3_5 = holder.jst04_5.getText().toString();
                            if (bScore4 && (v1_1_5 + v1_2_5 + v1_3_5).equals(answer4Array[4])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                        if (i == 5) {
                            //第一题第六个
                            String v1_1_6 = holder.sp04_1_6.getSelectedItem().toString();
                            String v1_2_6 = holder.sp04_2_6.getSelectedItem().toString();
                            String v1_3_6 = holder.jst04_6.getText().toString();
                            if (bScore4 && (v1_1_6 + v1_2_6 + v1_3_6).equals(answer4Array[5])) {
                                bScore4 = true;
                            } else {
                                bScore4 = false;
                            }
                        }
                    }
                    if (bScore4) {
                        iScore += 2;
                    } else {
                        iScore += 0;
                    }

                    //第五大题
                    boolean bScore5 = true;
                    String answe5 = dataItems.get(position).getCorrectAnswer5().replace("：","").replace(" ","");
                    String[] answer5Array = answe5.split("\r\n");
                    for (int i = 0; i < answer5Array.length; i++) {
                        if (i == 0) {
                            //第一题第一个
                            String v1_1_1 = holder.sp05_1.getSelectedItem().toString();
                            String v1_2_1 = holder.sp05_2.getSelectedItem().toString();
                            String v1_3_1 = holder.jst05.getText().toString();
                            if (bScore5 && (v1_1_1 + v1_2_1 + v1_3_1).equals(answer5Array[0])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                        if (i == 1) {
                            //第一题第二个
                            String v1_1_2 = holder.sp05_1_2.getSelectedItem().toString();
                            String v1_2_2 = holder.sp05_2_2.getSelectedItem().toString();
                            String v1_3_2 = holder.jst05_2.getText().toString();
                            if (bScore5 && (v1_1_2 + v1_2_2 + v1_3_2).equals(answer5Array[1])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                        if (i == 2) {
                            //第一题第三个
                            String v1_1_3 = holder.sp05_1_3.getSelectedItem().toString();
                            String v1_2_3 = holder.sp05_2_3.getSelectedItem().toString();
                            String v1_3_3 = holder.jst05_3.getText().toString();
                            if (bScore5 && (v1_1_3 + v1_2_3 + v1_3_3).equals(answer5Array[2])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                        if (i == 3) {
                            //第一题第四个
                            String v1_1_4 = holder.sp05_1_4.getSelectedItem().toString();
                            String v1_2_4 = holder.sp05_2_4.getSelectedItem().toString();
                            String v1_3_4 = holder.jst05_4.getText().toString();
                            if (bScore5 && (v1_1_4 + v1_2_4 + v1_3_4).equals(answer5Array[3])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                        if (i == 4) {
                            //第一题第五个
                            String v1_1_5 = holder.sp05_1_5.getSelectedItem().toString();
                            String v1_2_5 = holder.sp05_2_5.getSelectedItem().toString();
                            String v1_3_5 = holder.jst05_5.getText().toString();
                            if (bScore5 && (v1_1_5 + v1_2_5 + v1_3_5).equals(answer5Array[4])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                        if (i == 5) {
                            //第一题第六个
                            String v1_1_6 = holder.sp05_1_6.getSelectedItem().toString();
                            String v1_2_6 = holder.sp05_2_6.getSelectedItem().toString();
                            String v1_3_6 = holder.jst05_6.getText().toString();
                            if (bScore5 && (v1_1_6 + v1_2_6 + v1_3_6).equals(answer5Array[5])) {
                                bScore5 = true;
                            } else {
                                bScore5 = false;
                            }
                        }
                    }
                    if (bScore5) {
                        iScore += 2;
                    } else {
                        iScore += 0;
                    }


                    holder.wrongLayout.setVisibility(View.VISIBLE);
                    holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                    holder.btnMakeJST.setVisibility(View.GONE);
                    if (map.containsKey(position)) {
                        return;
                    }
                    map.put(position, true);
                    dataItems.get(position).setIsSelect("0");
                    //保存数据
                    SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                    questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                    questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                    questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                    questionInfo.setScore(String.valueOf(iScore));
                    questionInfo.setIs_correct(isCorrect);
                    mContext.questionInfos.add(questionInfo);


                }
            });

            holder.layoutA.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (map.containsKey(position)) {
                        return;
                    }
                    map.put(position, true);

                    if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                        // mContext.setCurrentView(position + 1);
                        holder.ivA.setImageResource(drawable.ic_practice_test_right);
                        holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                        isCorrect = ConstantUtil.isCorrect;
                    } else {
                        isCorrect = ConstantUtil.isError;
                        errortopicNum += 1;


                        holder.ivA.setImageResource(drawable.ic_practice_test_wrong);
                        holder.tvA.setTextColor(Color.parseColor("#d53235"));
                        //提示
                        holder.wrongLayout.setVisibility(View.VISIBLE);
                        holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                        //显示正确选项
                        if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                            holder.ivA.setImageResource(drawable.ic_practice_test_right);
                            holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                        } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                            holder.ivB.setImageResource(drawable.ic_practice_test_right);
                            holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                        } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                            holder.ivC.setImageResource(drawable.ic_practice_test_right);
                            holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                        } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                            holder.ivD.setImageResource(drawable.ic_practice_test_right);
                            holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                        } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                            holder.ivE.setImageResource(drawable.ic_practice_test_right);
                            holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                        }
                    }
                    //保存数据
                    SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                    questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                    questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                    questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                    questionInfo.setScore(dataItems.get(position).getScore());
                    questionInfo.setIs_correct(isCorrect);
                    mContext.questionInfos.add(questionInfo);
                    dataItems.get(position).setIsSelect("0");
                }
            });


        } else  //分析题
            if (dataItems.get(position).getQuestionType().equals("3")) {
                holder.radioButnLayout.setVisibility(View.GONE);
                holder.radioFXTButnLayout.setVisibility(View.VISIBLE);
                holder.radioJSTButnLayout.setVisibility(View.GONE);
                //DXTInfo
                //题目赋值
                String question = "(分析题)\r\n" + dataItems.get(position).getQuestionName();
                question += "\r\n" + dataItems.get(position).getQuestionName1()
                        .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
                question += "\r\n" + dataItems.get(position).getQuestionName2()
                        .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
                question += "\r\n" + dataItems.get(position).getQuestionName3()
                        .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
                question += "\r\n" + dataItems.get(position).getQuestionName4()
                        .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");
                question += "\r\n" + dataItems.get(position).getQuestionName5()
                        .replace("A.", "\r\nA.").replace("B.", "\r\nB.").replace("C.", "\r\nC.").replace("D.", "\r\nD.");

                holder.question.setText(question);

                holder.btnMakeFXT.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.wrongLayout.setVisibility(View.VISIBLE);
                        holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                        holder.btnMakeFXT.setVisibility(View.GONE);
                        if (map.containsKey(position)) {
                            return;
                        }

                        isCorrect = ConstantUtil.isCorrect;
                        int fxtScore = 0;
                        map.put(position, true);
                        dataItems.get(position).setIsSelect("0");

                        String strResult1 = "";
                        if (holder.cb1_01.isChecked()) {
                            strResult1 += "A";
                        }
                        if (holder.cb1_02.isChecked()) {
                            strResult1 += "B";
                        }
                        if (holder.cb1_03.isChecked()) {
                            strResult1 += "C";
                        }
                        if (holder.cb1_04.isChecked()) {
                            strResult1 += "D";
                        }
                        if (strResult1.equals(dataItems.get(position).getCorrectAnswer1())) {
                            holder.result01.setText("正确");
                            fxtScore += 2;
                        } else {
                            holder.result01.setText("错误，正确答案为:" + dataItems.get(position).getCorrectAnswer1());
                        }

                        String strResult2 = "";
                        if (holder.cb2_01.isChecked()) {
                            strResult2 += "A";
                        }
                        if (holder.cb2_02.isChecked()) {
                            strResult2 += "B";
                        }
                        if (holder.cb2_03.isChecked()) {
                            strResult2 += "C";
                        }
                        if (holder.cb2_04.isChecked()) {
                            strResult2 += "D";
                        }
                        if (strResult2.equals(dataItems.get(position).getCorrectAnswer2())) {
                            holder.result02.setText("正确");
                        } else {
                            holder.result02.setText("错误，正确答案为:" + dataItems.get(position).getCorrectAnswer2());
                        }

                        String strResult3 = "";
                        if (holder.cb3_01.isChecked()) {
                            strResult3 += "A";
                        }
                        if (holder.cb3_02.isChecked()) {
                            strResult3 += "B";
                        }
                        if (holder.cb3_03.isChecked()) {
                            strResult3 += "C";
                        }
                        if (holder.cb3_04.isChecked()) {
                            strResult3 += "D";
                        }
                        if (strResult3.equals(dataItems.get(position).getCorrectAnswer3())) {
                            holder.result03.setText("正确");
                            fxtScore += 2;
                        } else {
                            holder.result03.setText("错误，正确答案为:" + dataItems.get(position).getCorrectAnswer3());
                        }

                        String strResult4 = "";
                        if (holder.cb4_01.isChecked()) {
                            strResult4 += "A";
                        }
                        if (holder.cb4_02.isChecked()) {
                            strResult4 += "B";
                        }
                        if (holder.cb4_03.isChecked()) {
                            strResult4 += "C";
                        }
                        if (holder.cb4_04.isChecked()) {
                            strResult4 += "D";
                        }
                        if (strResult4.equals(dataItems.get(position).getCorrectAnswer4())) {
                            holder.result04.setText("正确");
                            fxtScore += 2;
                        } else {
                            holder.result04.setText("错误，正确答案为:" + dataItems.get(position).getCorrectAnswer4());
                        }

                        String strResult5 = "";
                        if (holder.cb5_01.isChecked()) {
                            strResult5 += "A";
                        }
                        if (holder.cb5_02.isChecked()) {
                            strResult5 += "B";
                        }
                        if (holder.cb5_03.isChecked()) {
                            strResult5 += "C";
                        }
                        if (holder.cb5_04.isChecked()) {
                            strResult5 += "D";
                        }
                        if (strResult5.equals(dataItems.get(position).getCorrectAnswer5())) {
                            holder.result05.setText("正确");
                            fxtScore += 2;
                        } else {
                            holder.result05.setText("错误，正确答案为:" + dataItems.get(position).getCorrectAnswer5());
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(String.valueOf(fxtScore));
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
                //选项
                holder.layoutA.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {


                        if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                            //  mContext.setCurrentView(position + 1);
                            holder.ivA.setImageResource(drawable.ic_practice_test_right);
                            holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;


                            holder.ivA.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvA.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });


            } else if (dataItems.get(position).getQuestionType().equals("0")) {
                //DXTInfo//判断题型

                holder.question.setText("(单选题)\r\n" + dataItems.get(position).getQuestionName());

                holder.layoutA.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);

                        if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                            // mContext.setCurrentView(position + 1);
                            holder.ivA.setImageResource(drawable.ic_practice_test_right);
                            holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;

                            holder.ivA.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvA.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);

                        dataItems.get(position).setIsSelect("0");
                    }
                });
                holder.layoutB.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                            //  mContext.setCurrentView(position + 1);
                            holder.ivB.setImageResource(drawable.ic_practice_test_right);
                            holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;

                            holder.ivB.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvB.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
                holder.layoutC.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                            //   mContext.setCurrentView(position + 1);
                            holder.ivC.setImageResource(drawable.ic_practice_test_right);
                            holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;


                            holder.ivC.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvC.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
                holder.layoutD.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                            // mContext.setCurrentView(position + 1);
                            holder.ivD.setImageResource(drawable.ic_practice_test_right);
                            holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;

                            holder.ivD.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvD.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
                holder.layoutE.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                            //  mContext.setCurrentView(position + 1);
                            holder.ivE.setImageResource(drawable.ic_practice_test_right);
                            holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;


                            holder.ivE.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvE.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
            } else if (dataItems.get(position).getQuestionType().equals("1")) {
                //多选题
                holder.question.setText("(多选题)\r\n" + dataItems.get(position).getQuestionName());

                holder.layoutA.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mapClick.put(position, true);
                        if (map.containsKey(position)) {
                            return;
                        }
                        if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                            holder.ivA.setImageResource(drawable.ic_practice_test_right);
                            holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                            if (position == viewItems.size() - 1) {
                                answerLast.append("A");
                            } else {
                                answer.append("A");
                            }
                        } else {
                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(position, isCorrect);
                            errortopicNum += 1;


                            map.put(position, true);
                            holder.ivA.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvA.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(position).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(position).setIsSelect("0");
                        }
                        resultA = "A";
                    }
                });
                holder.layoutB.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mapClick.put(position, true);
                        if (map.containsKey(position)) {
                            return;
                        }
                        if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                            holder.ivB.setImageResource(drawable.ic_practice_test_right);
                            holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                            if (position == viewItems.size() - 1) {
                                answerLast.append("B");
                            } else {
                                answer.append("B");
                            }
                        } else {
                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(position, isCorrect);
                            errortopicNum += 1;


                            map.put(position, true);
                            holder.ivB.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvB.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(position).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(position).setIsSelect("0");
                        }
                        resultB = "B";
                    }
                });
                holder.layoutC.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mapClick.put(position, true);
                        if (map.containsKey(position)) {
                            return;
                        }
                        if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                            holder.ivC.setImageResource(drawable.ic_practice_test_right);
                            holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                            if (position == viewItems.size() - 1) {
                                answerLast.append("C");
                            } else {
                                answer.append("C");
                            }
                        } else {
                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(position, isCorrect);
                            errortopicNum += 1;

                            map.put(position, true);
                            holder.ivC.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvC.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(position).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(position).setIsSelect("0");
                        }
                        resultC = "C";
                    }
                });
                holder.layoutD.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mapClick.put(position, true);
                        if (map.containsKey(position)) {
                            return;
                        }
                        if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                            holder.ivD.setImageResource(drawable.ic_practice_test_right);
                            holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                            if (position == viewItems.size() - 1) {
                                answerLast.append("D");
                            } else {
                                answer.append("D");
                            }
                        } else {
                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(position, isCorrect);
                            errortopicNum += 1;

                            map.put(position, true);
                            holder.ivD.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvD.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(position).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(position).setIsSelect("0");
                        }
                        resultD = "D";
                    }
                });
                holder.layoutE.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mapClick.put(position, true);
                        if (map.containsKey(position)) {
                            return;
                        }
                        if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                            holder.ivE.setImageResource(drawable.ic_practice_test_right);
                            holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                            if (position == viewItems.size() - 1) {
                                answerLast.append("E");
                            } else {
                                answer.append("E");
                            }
                        } else {
                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(position, isCorrect);
                            errortopicNum += 1;

                            map.put(position, true);
                            holder.ivE.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvE.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("A")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("B")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(position).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(position).setIsSelect("0");
                        }
                        resultE = "E";
                    }
                });

            } else {
                //判断题
                holder.question.setText("(判断题)\r\n" + dataItems.get(position).getQuestionName());
                holder.layoutA.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("对")) {
                            //   mContext.setCurrentView(position + 1);
                            holder.ivA.setImageResource(drawable.ic_practice_test_right);
                            holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;


                            holder.ivA.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvA.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("对")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("错")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });
                holder.layoutB.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (map.containsKey(position)) {
                            return;
                        }
                        map.put(position, true);
                        if (dataItems.get(position).getCorrectAnswer().contains("错")) {
                            //  mContext.setCurrentView(position + 1);
                            holder.ivB.setImageResource(drawable.ic_practice_test_right);
                            holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            isCorrect = ConstantUtil.isCorrect;
                        } else {
                            isCorrect = ConstantUtil.isError;
                            errortopicNum += 1;


                            holder.ivB.setImageResource(drawable.ic_practice_test_wrong);
                            holder.tvB.setTextColor(Color.parseColor("#d53235"));
                            //提示
                            holder.wrongLayout.setVisibility(View.VISIBLE);
                            holder.explaindetailTv.setText("" + dataItems.get(position).getAnalysis());
                            //显示正确选项
                            if (dataItems.get(position).getCorrectAnswer().contains("对")) {
                                holder.ivA.setImageResource(drawable.ic_practice_test_right);
                                holder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("错")) {
                                holder.ivB.setImageResource(drawable.ic_practice_test_right);
                                holder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("C")) {
                                holder.ivC.setImageResource(drawable.ic_practice_test_right);
                                holder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("D")) {
                                holder.ivD.setImageResource(drawable.ic_practice_test_right);
                                holder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            } else if (dataItems.get(position).getCorrectAnswer().contains("E")) {
                                holder.ivE.setImageResource(drawable.ic_practice_test_right);
                                holder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }
                        }
                        //保存数据
                        SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                        questionInfo.setQuestionId(dataItems.get(position).getQuestionId());
                        questionInfo.setQuestionType(dataItems.get(position).getQuestionType());
                        questionInfo.setRealAnswer(dataItems.get(position).getCorrectAnswer());
                        questionInfo.setScore(dataItems.get(position).getScore());
                        questionInfo.setIs_correct(isCorrect);
                        mContext.questionInfos.add(questionInfo);
                        dataItems.get(position).setIsSelect("0");
                    }
                });

            }

        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#2b89e9"));

        SpannableStringBuilder builder1 = new SpannableStringBuilder(holder.question.getText().toString());
        builder1.setSpan(blueSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.question.setText(builder1);

        // 最后一页修改"下一步"按钮文字
        if (position == viewItems.size() - 1) {
            holder.nextBtn.setVisibility(View.GONE);
            //  holder.nextText.setText("提交");
            //  holder.nextImage.setImageResource(drawable.vote_submit_finish);
        } else {
            holder.nextBtn.setVisibility(View.VISIBLE);
        }
        holder.previousBtn.setOnClickListener(new LinearOnClickListener(position - 1, false, position, holder));
        holder.totalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        holder.nextBtn.setOnClickListener(new LinearOnClickListener(position + 1, true, position, holder));
        holder.submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.uploadExamination(errortopicNum);
            }
        });
        container.addView(viewItems.get(position));
        return viewItems.get(position);
    }

    /**
     * @author 设置上一步和下一步按钮监听
     */
    class LinearOnClickListener implements OnClickListener {

        private int mPosition;
        private int mPosition1;
        private boolean mIsNext;
        private ViewHolder viewHolder;

        public LinearOnClickListener(int position, boolean mIsNext, int position1, ViewHolder viewHolder) {
            mPosition = position;
            mPosition1 = position1;
            this.viewHolder = viewHolder;
            this.mIsNext = mIsNext;
        }

        @Override
        public void onClick(View v) {
            if (mPosition == viewItems.size()) {
                //单选
                if (dataItems.get(mPosition1).getQuestionType().equals("1")) {
                    //判断多选时的点击
                    map.put(mPosition1, true);

                    if (mapMultiSelect.containsKey(mPosition1)) {
                        //提交答题
                        mContext.uploadExamination(errortopicNum);
                    } else {
                        String ssStr = dataItems.get(mPosition1).getCorrectAnswer();
                        ssStr = ssStr.replace("|", "");

                        if (mPosition == viewItems.size()) {
                            if (answerLast.toString().contains("A")) {
                                answer1.append("A");
                            }
                            if (answerLast.toString().contains("B")) {
                                answer1.append("B");
                            }
                            if (answerLast.toString().contains("C")) {
                                answer1.append("C");
                            }
                            if (answerLast.toString().contains("D")) {
                                answer1.append("D");
                            }
                            if (answerLast.toString().contains("E")) {
                                answer1.append("E");
                            }

                        } else {
                            if (answer.toString().contains("A")) {
                                answer1.append("A");
                            }
                            if (answer.toString().contains("B")) {
                                answer1.append("B");
                            }
                            if (answer.toString().contains("C")) {
                                answer1.append("C");
                            }
                            if (answer.toString().contains("D")) {
                                answer1.append("D");
                            }
                            if (answer.toString().contains("E")) {
                                answer1.append("E");
                            }
                        }

                        if (answer1.toString().equals(ssStr)) {
                            //清除答案
                            answer.delete(0, answer.length());
                            answer1.delete(0, answer1.length());
                            isCorrect = ConstantUtil.isCorrect;
                            mapMultiSelect.put(mPosition1, ConstantUtil.isCorrect);
                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(mPosition1).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(mPosition1).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(mPosition1).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(mPosition1).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(mPosition1).setIsSelect("0");
                            //提交答题
                            mContext.uploadExamination(errortopicNum);
                        } else {
                            //清除答案
                            answer.delete(0, answer.length());
                            answer1.delete(0, answer1.length());
                            errortopicNum += 1;
                            isCorrect = ConstantUtil.isError;

                            isCorrect = ConstantUtil.isError;
                            mapMultiSelect.put(mPosition1, ConstantUtil.isError);

                            //提示
                            viewHolder.wrongLayout.setVisibility(View.VISIBLE);
                            viewHolder.explaindetailTv.setText("" + dataItems.get(mPosition1).getAnalysis());
                            //保存数据
                            SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                            questionInfo.setQuestionId(dataItems.get(mPosition1).getQuestionId());
                            questionInfo.setQuestionType(dataItems.get(mPosition1).getQuestionType());
                            questionInfo.setRealAnswer(dataItems.get(mPosition1).getCorrectAnswer());
                            questionInfo.setScore(dataItems.get(mPosition1).getScore());
                            questionInfo.setIs_correct(isCorrect);
                            mContext.questionInfos.add(questionInfo);
                            dataItems.get(mPosition1).setIsSelect("0");
                            //显示正确选项
                            if (dataItems.get(mPosition1).getCorrectAnswer().contains("A")) {
                                viewHolder.ivA.setImageResource(drawable.ic_practice_test_right);
                                viewHolder.tvA.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(mPosition1).getCorrectAnswer().contains("B")) {
                                viewHolder.ivB.setImageResource(drawable.ic_practice_test_right);
                                viewHolder.tvB.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(mPosition1).getCorrectAnswer().contains("C")) {
                                viewHolder.ivC.setImageResource(drawable.ic_practice_test_right);
                                viewHolder.tvC.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(mPosition1).getCorrectAnswer().contains("D")) {
                                viewHolder.ivD.setImageResource(drawable.ic_practice_test_right);
                                viewHolder.tvD.setTextColor(Color.parseColor("#61bc31"));
                            }
                            if (dataItems.get(mPosition1).getCorrectAnswer().contains("E")) {
                                viewHolder.ivE.setImageResource(drawable.ic_practice_test_right);
                                viewHolder.tvE.setTextColor(Color.parseColor("#61bc31"));
                            }

                        }

                    }
                } else {
                    mContext.uploadExamination(errortopicNum);
                }
            } else {
                if (mPosition == -1) {
                    Toast.makeText(mContext, "已经是第一页", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //单选
                    if (dataItems.get(mPosition1).getQuestionType().equals("0")) {
                        if (mIsNext) {
                          /*  if (!map.containsKey(mPosition1)) {
                                Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            } */
                        }
                        isNext = mIsNext;
                        mContext.setCurrentView(mPosition);
                    } else if (dataItems.get(mPosition1).getQuestionType().equals("1")) {
                        if (mIsNext) {
                            //判断多选时的点击
                            if (!map.containsKey(mPosition1)) {
                              /*  if (!mapClick.containsKey(mPosition1)) {
                                    Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT).show();
                                    return;
                                } */
                            }
                            map.put(mPosition1, true);

                            if (mapMultiSelect.containsKey(mPosition1)) {
                                //清除答案
                                answer.delete(0, answer.length());
                                //选过的，直接跳转下一题
                                isNext = mIsNext;
                                mContext.setCurrentView(mPosition);
                            } else {
                                String ssStr = dataItems.get(mPosition1).getCorrectAnswer();
                                ssStr = ssStr.replace("|", "");
                                if (answer.toString().contains("A")) {
                                    answer1.append("A");
                                }
                                if (answer.toString().contains("B")) {
                                    answer1.append("B");
                                }
                                if (answer.toString().contains("C")) {
                                    answer1.append("C");
                                }
                                if (answer.toString().contains("D")) {
                                    answer1.append("D");
                                }
                                if (answer.toString().contains("E")) {
                                    answer1.append("E");
                                }
                                if (answer1.toString().equals(ssStr)) {
                                    //清除答案
                                    answer.delete(0, answer.length());
                                    answer1.delete(0, answer1.length());
                                    isCorrect = ConstantUtil.isCorrect;
                                    mapMultiSelect.put(mPosition1, ConstantUtil.isCorrect);
                                    //保存数据
                                    SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                                    questionInfo.setQuestionId(dataItems.get(mPosition1).getQuestionId());
                                    questionInfo.setQuestionType(dataItems.get(mPosition1).getQuestionType());
                                    questionInfo.setRealAnswer(dataItems.get(mPosition1).getCorrectAnswer());
                                    questionInfo.setScore(dataItems.get(mPosition1).getScore());
                                    questionInfo.setIs_correct(isCorrect);
                                    mContext.questionInfos.add(questionInfo);
                                    dataItems.get(mPosition1).setIsSelect("0");
                                    isNext = mIsNext;
                                    mContext.setCurrentView(mPosition);
                                } else {
                                    //清除答案
                                    answer.delete(0, answer.length());
                                    answer1.delete(0, answer1.length());
                                    errortopicNum += 1;
                                    isCorrect = ConstantUtil.isError;

                                    isCorrect = ConstantUtil.isError;
                                    mapMultiSelect.put(mPosition1, ConstantUtil.isError);

                                    //提示
                                    viewHolder.wrongLayout.setVisibility(View.VISIBLE);
                                    viewHolder.explaindetailTv.setText("" + dataItems.get(mPosition1).getAnalysis());
                                    //保存数据
                                    SaveQuestionInfo questionInfo = new SaveQuestionInfo();
                                    questionInfo.setQuestionId(dataItems.get(mPosition1).getQuestionId());
                                    questionInfo.setQuestionType(dataItems.get(mPosition1).getQuestionType());
                                    questionInfo.setRealAnswer(dataItems.get(mPosition1).getCorrectAnswer());
                                    questionInfo.setScore(dataItems.get(mPosition1).getScore());
                                    questionInfo.setIs_correct(isCorrect);
                                    mContext.questionInfos.add(questionInfo);
                                    dataItems.get(mPosition1).setIsSelect("0");
                                    //显示正确选项
                                    if (dataItems.get(mPosition1).getCorrectAnswer().contains("A")) {
                                        viewHolder.ivA.setImageResource(drawable.ic_practice_test_right);
                                        viewHolder.tvA.setTextColor(Color.parseColor("#61bc31"));
                                    }
                                    if (dataItems.get(mPosition1).getCorrectAnswer().contains("B")) {
                                        viewHolder.ivB.setImageResource(drawable.ic_practice_test_right);
                                        viewHolder.tvB.setTextColor(Color.parseColor("#61bc31"));
                                    }
                                    if (dataItems.get(mPosition1).getCorrectAnswer().contains("C")) {
                                        viewHolder.ivC.setImageResource(drawable.ic_practice_test_right);
                                        viewHolder.tvC.setTextColor(Color.parseColor("#61bc31"));
                                    }
                                    if (dataItems.get(mPosition1).getCorrectAnswer().contains("D")) {
                                        viewHolder.ivD.setImageResource(drawable.ic_practice_test_right);
                                        viewHolder.tvD.setTextColor(Color.parseColor("#61bc31"));
                                    }
                                    if (dataItems.get(mPosition1).getCorrectAnswer().contains("E")) {
                                        viewHolder.ivE.setImageResource(drawable.ic_practice_test_right);
                                        viewHolder.tvE.setTextColor(Color.parseColor("#61bc31"));
                                    }
                                }
                            }
                        } else {
                            mContext.setCurrentView(mPosition);
                        }

                    } else {
                        if (mIsNext) {
                          /*  if (!map.containsKey(mPosition1)) {
                                Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            } */
                        }

                        isNext = mIsNext;
                        mContext.setCurrentView(mPosition);
                    }
                }
            }

        }

    }

    @Override
    public int getCount() {
        if (viewItems == null)
            return 0;
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //错题数
    public int errorTopicNum() {
        if (errortopicNum != 0) {
            return errortopicNum;
        }
        return 0;
    }

    public class ViewHolder {

        LinearLayout radioJSTButnLayout;
        LinearLayout radioFXTButnLayout;
        LinearLayout radioButnLayout;

        TextView questionType;
        TextView question;
        LinearLayout previousBtn, nextBtn, errorBtn, submitBtn, totalBtn;
        TextView nextText;
        TextView totalText;
        ImageView nextImage;
        LinearLayout wrongLayout;
        TextView explaindetailTv;
        LinearLayout layoutA;
        LinearLayout layoutB;
        LinearLayout layoutC;
        LinearLayout layoutD;
        LinearLayout layoutE;
        ImageView ivA;
        ImageView ivB;
        ImageView ivC;
        ImageView ivD;
        ImageView ivE;
        TextView tvA;
        TextView tvB;
        TextView tvC;
        TextView tvD;
        TextView tvE;
        ImageView ivA_;
        ImageView ivB_;
        ImageView ivC_;
        ImageView ivD_;
        ImageView ivE_;

        LinearLayout rg01;
        CheckBox cb1_01;
        CheckBox cb1_02;
        CheckBox cb1_03;
        CheckBox cb1_04;

        LinearLayout rg02;
        CheckBox cb2_01;
        CheckBox cb2_02;
        CheckBox cb2_03;
        CheckBox cb2_04;


        LinearLayout rg03;
        CheckBox cb3_01;
        CheckBox cb3_02;
        CheckBox cb3_03;
        CheckBox cb3_04;


        LinearLayout rg04;
        CheckBox cb4_01;
        CheckBox cb4_02;
        CheckBox cb4_03;
        CheckBox cb4_04;


        LinearLayout rg05;
        CheckBox cb5_01;
        CheckBox cb5_02;
        CheckBox cb5_03;
        CheckBox cb5_04;

        TextView result01;
        TextView result02;
        TextView result03;
        TextView result04;
        TextView result05;
        Button btnMakeFXT;


        Spinner sp01_1;
        Spinner sp01_2;
        EditText jst01;

        Spinner sp01_1_2;
        Spinner sp01_2_2;
        EditText jst01_2;

        Spinner sp01_1_3;
        Spinner sp01_2_3;
        EditText jst01_3;

        Spinner sp01_1_4;
        Spinner sp01_2_4;
        EditText jst01_4;

        Spinner sp01_1_5;
        Spinner sp01_2_5;
        EditText jst01_5;

        Spinner sp01_1_6;
        Spinner sp01_2_6;
        EditText jst01_6;

        Spinner sp02_1;
        Spinner sp02_2;
        EditText jst02;

        Spinner sp02_1_2;
        Spinner sp02_2_2;
        EditText jst02_2;

        Spinner sp02_1_3;
        Spinner sp02_2_3;
        EditText jst02_3;

        Spinner sp02_1_4;
        Spinner sp02_2_4;
        EditText jst02_4;
        Spinner sp02_1_5;
        Spinner sp02_2_5;
        EditText jst02_5;
        Spinner sp02_1_6;
        Spinner sp02_2_6;
        EditText jst02_6;

        Spinner sp03_1;
        Spinner sp03_2;
        EditText jst03;

        Spinner sp03_1_2;
        Spinner sp03_2_2;
        EditText jst03_2;
        Spinner sp03_1_3;
        Spinner sp03_2_3;
        EditText jst03_3;
        Spinner sp03_1_4;
        Spinner sp03_2_4;
        EditText jst03_4;
        Spinner sp03_1_5;
        Spinner sp03_2_5;
        EditText jst03_5;
        Spinner sp03_1_6;
        Spinner sp03_2_6;
        EditText jst03_6;

        Spinner sp04_1;
        Spinner sp04_2;
        EditText jst04;

        Spinner sp04_1_2;
        Spinner sp04_2_2;
        EditText jst04_2;
        Spinner sp04_1_3;
        Spinner sp04_2_3;
        EditText jst04_3;
        Spinner sp04_1_4;
        Spinner sp04_2_4;
        EditText jst04_4;
        Spinner sp04_1_5;
        Spinner sp04_2_5;
        EditText jst04_5;
        Spinner sp04_1_6;
        Spinner sp04_2_6;
        EditText jst04_6;

        Spinner sp05_1;
        Spinner sp05_2;
        EditText jst05;

        Spinner sp05_1_2;
        Spinner sp05_2_2;
        EditText jst05_2;
        Spinner sp05_1_3;
        Spinner sp05_2_3;
        EditText jst05_3;
        Spinner sp05_1_4;
        Spinner sp05_2_4;
        EditText jst05_4;
        Spinner sp05_1_5;
        Spinner sp05_2_5;
        EditText jst05_5;
        Spinner sp05_1_6;
        Spinner sp05_2_6;
        EditText jst05_6;

        Button btn01;
        Button btn02;
        Button btn03;
        Button btn04;
        Button btn05;


        LinearLayout ll0102;
        LinearLayout ll0103;
        LinearLayout ll0104;
        LinearLayout ll0105;
        LinearLayout ll0106;

        LinearLayout ll0202;
        LinearLayout ll0203;
        LinearLayout ll0204;
        LinearLayout ll0205;
        LinearLayout ll0206;

        LinearLayout ll0302;
        LinearLayout ll0303;
        LinearLayout ll0304;
        LinearLayout ll0305;
        LinearLayout ll0306;

        LinearLayout ll0402;
        LinearLayout ll0403;
        LinearLayout ll0404;
        LinearLayout ll0405;
        LinearLayout ll0406;

        LinearLayout ll0502;
        LinearLayout ll0503;
        LinearLayout ll0504;
        LinearLayout ll0505;
        LinearLayout ll0506;

        TextView ll05Title;
        LinearLayout ll05Content;




        Button btnMakeJST;

    }

    public void showDialog() {
        final BottomSheet.Builder sheet = new BottomSheet.Builder(mContext);
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_dailog, null);
        EasyRecyclerView list = (EasyRecyclerView) v.findViewById(id.list);
        //设置recycler布局管理器

        GridLayoutManager mGrdiLayoutManager = new GridLayoutManager(mContext, 5);

        list.setLayoutManager(mGrdiLayoutManager);
        //初始化adapter
        ItemListAdapter mAdapter = new ItemListAdapter(mContext);
        list.setAdapterWithProgress(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mContext.setCurrentView(position);
                sheet.close();
            }
        });
        List<String> mModelList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            mModelList.add(String.format("第%s题", i + 1));
        }
        mAdapter.addAll(mModelList);


        sheet.setTitle("选题");
        sheet.setView(v);
        sheet.show();
    }

}
