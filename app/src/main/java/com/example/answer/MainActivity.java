package com.example.answer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends Activity {

    private Button startBtn;
    private Button startBtn2;
    private ImageView left;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        left = (ImageView) findViewById(R.id.left);
        title = (TextView) findViewById(R.id.title);
        startBtn = (Button) findViewById(R.id.start1);
        startBtn2 = (Button) findViewById(R.id.start2);
        left.setVisibility(View.GONE);
        title.setText("龙腾题库");

        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, CJFilesActivity.class);
                startActivity(intent);
            }
        });

        startBtn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, FilesActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            initPermissions();
            checkPermissions();
        }
    }

    private MultiplePermissionsListener allPermissionsListener;


    /**
     * 初始化权限
     */
    protected void initPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener=new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.getDeniedPermissionResponses().size() > 0) {
                    checkPermissions();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                if (token != null) {
                    token.continuePermissionRequest();
                }
            }
        };
        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener);
        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);

    }

    /**
     * 检查权限
     */
    protected void checkPermissions() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(allPermissionsListener,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        );
    }


}
