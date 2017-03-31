package com.example.answer.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.answer.R;
import com.example.answer.fragment.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


/**
 * Created by zbmobi on 15/10/12.
 */
public abstract class BaseRecyclerListFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    public EasyRecyclerView mList;


    public abstract void init();

    public abstract void initDatas();

    public abstract void onListStart();

    public abstract void onListMore();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //设置该fragment能为activity添加menu菜单项
        setHasOptionsMenu(true);

        init();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list_recycler;
    }


    @Override
    protected void initDataBind() {
        mList = (EasyRecyclerView) rootView.findViewById(R.id.list);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThisContext());
        //mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager mGrdiLayoutManager = null;
        if (isTablet(getThisContext())) {
            mGrdiLayoutManager = new GridLayoutManager(getThisContext(), 3);
        } else {
            mGrdiLayoutManager = new GridLayoutManager(getThisContext(), 1);
        }
        mList.setLayoutManager(mGrdiLayoutManager);

        initDatas();
    }


    @Override
    public void onRefresh() {

        onListStart();
    }

    @Override
    public void onLoadMore() {
        onListMore();
    }


}
