package com.example.answer.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.answer.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录Adapter
 *
 * @author zbmobi
 */
public class FileListAdapter extends RecyclerArrayAdapter<DbFilesEntity> {
    public FileListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent);
    }

    class ItemViewHolder extends BaseViewHolder<DbFilesEntity> {



        @Bind(R.id.notes_content_tv)
        TextView notesContentTv;


        public ItemViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_files);
            //初始化ButterKnife
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(DbFilesEntity item) {

            if (!TextUtils.isEmpty(item.getName())) {

                notesContentTv.setText(item.getName());
            }

        }
    }

}
