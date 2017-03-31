package com.example.answer.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.widget.Toast;

import com.example.answer.CJAnalogyExaminationActivity;
import com.example.answer.view.BaseRecyclerListFragment;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbmobi on 15/10/12.
 */
public class CJFileListFragment extends BaseRecyclerListFragment {


    private FileListAdapter mAdapter;
    private String mType = "";
    private String mTtitle = "";
    private List<DbFilesEntity> mModelList = new ArrayList<>();
    private String DB_PATH = "data/data/com.example.answer/";

    public static CJFileListFragment newInstance(String type) {
        CJFileListFragment f = new CJFileListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("type", type);
        f.setArguments(mBundle);
        return f;
    }

    @Override
    public void init() {
        DB_PATH = Environment.getExternalStorageDirectory().toString() + "/CJ/";
        if(getArguments()!=null && getArguments().getString("type")!=null){
            mType=getArguments().getString("type");
        }
    }

    @Override
    public void initDatas() {

        //设置recycler布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThisContext());
        //mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager mGrdiLayoutManager = null;
        if (isTablet(getThisContext())) {
            mGrdiLayoutManager = new GridLayoutManager(getThisContext(), 3);
        } else {
            mGrdiLayoutManager = new GridLayoutManager(getThisContext(), 1);
        }
        mList.setLayoutManager(mLayoutManager);


        //初始化adapter
        mAdapter = new FileListAdapter(getThisContext());
        mList.setAdapterWithProgress(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DbFilesEntity item = mAdapter.getItem(position);
                if (item != null) {
                    String dbFulPath = DB_PATH + item.getContent();
                    File dbFile = new File(dbFulPath);
                    if (dbFile.exists()) {
                        dbFile.delete();
                    }
                    try {
                        copyDataBase(item.getContent());
                        Bundle mBundle = new Bundle();
                        mBundle.putString("db", dbFulPath);
                        Intent intent = new Intent(getActivity(), CJAnalogyExaminationActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getThisContext(), "复制数据库失败", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


      //  mList.setRefreshListener(this);
        onRefresh();
    }


    private void copyDataBase(String dbName) throws IOException {

        //Open your local db as the input stream
        InputStream myInput = getThisContext().getAssets().open("CJ/"+dbName);

        // Path to the just created empty db
        String outFileName = DB_PATH + dbName;
        if(!new File(DB_PATH).exists()){
            new File(DB_PATH).mkdir();
        }
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        if (menu.hasVisibleItems()) {
            menu.setGroupVisible(0, false);
        }
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }

    @Override
    public void onListStart() {

        String[] files = null;
        AssetManager assets = null;
        try {
            assets = getActivity().getAssets();
            //获取/assets/目录下所有文件
            files = assets.list("CJ");
            int index=1;
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                String fileStart="KJ"+mType;
                if (fileName.endsWith(".db") && fileName.startsWith(fileStart)) {
                    DbFilesEntity item = new DbFilesEntity();
                    item.setName("考试试题"+String.valueOf(index));
                    item.setContent(fileName);
                    mModelList.add(item);
                    index++;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mList.showRecycler();
        mAdapter.clear();

        mAdapter.addAll(mModelList);

    }

    @Override
    public void onListMore() {

    }


}
