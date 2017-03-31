package com.example.answer.dao;

import android.content.Context;

import com.example.answer.bean.DXTInfo;
import com.example.answer.bean.DuoXTInfo;
import com.example.answer.bean.FXTInfo;
import com.example.answer.bean.JSTInfo;
import com.example.answer.bean.PanDTInfo;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

import java.util.List;


public class ExamDao {

    static LiteOrm liteOrm;

    public ExamDao(Context context, String db) {
   //     if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(context, db);
            config.debugged = true; // open the log
            config.dbVersion = 1; // set database version
            config.onUpdateListener = null; // set database update listener
            liteOrm = LiteOrm.newSingleInstance(config);
   //     }
    }


    public List<DXTInfo> getDanXList() {

        return liteOrm.query(DXTInfo.class);
    }

    public List<DuoXTInfo> getDuoXList() {

        return liteOrm.query(DuoXTInfo.class);
    }

    public List<PanDTInfo> getPanDTList() {

        return liteOrm.query(PanDTInfo.class);
    }

    public List<JSTInfo> getJSTList() {

        return liteOrm.query(JSTInfo.class);
    }

    public List<FXTInfo> getFXTList() {

        return liteOrm.query(FXTInfo.class);
    }


}
