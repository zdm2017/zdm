package com.example.answer.adapter;

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
public class ItemListAdapter extends RecyclerArrayAdapter<String> {
    public ItemListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent);
    }

    class ItemViewHolder extends BaseViewHolder<String> {



        @Bind(R.id.title)
        TextView title;


        public ItemViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_exam);
            //初始化ButterKnife
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(String item) {

            if (!TextUtils.isEmpty(item)) {

                title.setText(item);
            }

        }
    }

}
