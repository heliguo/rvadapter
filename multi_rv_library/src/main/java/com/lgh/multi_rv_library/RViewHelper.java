package com.lgh.multi_rv_library;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lgh.multi_rv_library.base.RViewAdapter;
import com.lgh.multi_rv_library.core.RViewCreate;

import java.util.List;

/**
 * author:lgh on 2019-11-14 15:45
 * 辅助类
 */
public class RViewHelper {

    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper swipeRefreshHelper;
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    private int startPageNumber = 1;
    private boolean isSupportPaging;
    private SwipeRefreshHelper.SwipeRefreshListener swipeRefreshListener;
    private int currentPageNumber;//当前页

    private RViewHelper(Builder builder) {
        this.swipeRefreshLayout = builder.create.createSwipeRefresh();
        this.context = builder.create.context();
        adapter = builder.create.createRViewAdapter();
        this.isSupportPaging = builder.create.isSupportPaging();
        this.recyclerView = builder.create.createRecyclerView();
        this.currentPageNumber = this.startPageNumber;
        this.swipeRefreshListener = builder.listener;
        if (swipeRefreshLayout != null) {
            swipeRefreshHelper = SwipeRefreshHelper.createSwipeRefreshHelper(swipeRefreshLayout);
        }
        init();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (swipeRefreshHelper != null) {
            swipeRefreshHelper.setSwipeRefreshListener(() -> {
                dismissSwipeRefresh();//停止刷新
                //重置页码
                currentPageNumber = startPageNumber;
                if (swipeRefreshListener != null)
                    swipeRefreshListener.OnRefresh();
            });
        }
        recyclerView.setAdapter(adapter);
    }

    private void dismissSwipeRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void notifyAdapterDataSetChanged(List datas) {
        //如果首次加载，下拉刷新
        if (currentPageNumber == startPageNumber) {
            adapter.updateDatas(datas);
        } else {
            adapter.addDatas(datas);
        }
        if (isSupportPaging) {
            //支持分页
            Log.e("RViewHelper", "notifyAdapterDataSetChanged: " + "分页功能");
        }

    }

    public static class Builder {
        private RViewCreate create;
        private SwipeRefreshHelper.SwipeRefreshListener listener;

        public Builder(RViewCreate create, SwipeRefreshHelper.SwipeRefreshListener listener ) {
            this.create = create;
            this.listener = listener;
        }

        public RViewHelper build() {
            return new RViewHelper(this);
        }
    }
}
