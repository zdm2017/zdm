package com.example.answer;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answer.util.ACache;
import com.example.answer.util.MD5;

public class CJLockActivity extends Activity {

    private Button startBtn;

    private ImageView left;
    private TextView title;

    private TextView etIMEI;
    private EditText etCode;
    private Button btnCopy;
    private Button btnPast;

    private ACache mACache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lock);
        mACache= ACache.get(this);
        left = (ImageView) findViewById(R.id.left);
        title = (TextView) findViewById(R.id.title);
        startBtn = (Button) findViewById(R.id.start);

        etIMEI = (TextView) findViewById(R.id.imei_et);
        etCode = (EditText) findViewById(R.id.code_et);
        btnCopy = (Button) findViewById(R.id.copy_btn);
        btnCopy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                copyFromEditText1();
            }
        });
        btnPast = (Button) findViewById(R.id.past_btn);
        btnPast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pasteToResult();
            }
        });

        left.setVisibility(View.GONE);
        title.setText("初级会计题库");
        etIMEI.setText(getIMIE());
        //etCode.setText(getCode(etIMEI.getText().toString().trim()));
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String imei=etIMEI.getText().toString().trim();
                String code=etCode.getText().toString().trim();

                if(TextUtils.isEmpty(code)){
                    Toast.makeText(CJLockActivity.this,"请重新输入注册码",Toast.LENGTH_SHORT).show();
                    return;
                }
                String result=getCode(imei);
                if(result.equals(code)){
                    mACache.put(imei+"b","SUCCESS");
                    Intent intent=new Intent(CJLockActivity.this,CJFilesActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(CJLockActivity.this,"注册码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }



            }
        });



    }

    private String getIMIE(){
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    private String getCode(String s){
        return MD5.getMD5(s.getBytes()).substring(0,8);
    }
    private ClipboardManager mClipboard = null;
    private void copyFromEditText1() {

        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        }

        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text",
                etIMEI.getText());

        // Set the clipboard's primary clip.
        mClipboard.setPrimaryClip(clip);
    }

    private void pasteToResult() {
        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }

        String resultString = "";
        // 检查剪贴板是否有内容
        if (!mClipboard.hasPrimaryClip()) {
            Toast.makeText(CJLockActivity.this,
                    "请先复制内容", Toast.LENGTH_SHORT).show();
        }
        else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();

            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item
                        .coerceToText(CJLockActivity.this);

                resultString += str;
            }

        }
        etCode.setText(resultString);
    }



}
