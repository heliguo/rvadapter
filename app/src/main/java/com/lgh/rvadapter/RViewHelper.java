package com.lgh.rvadapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;
import com.lgh.rvadapter.core.RViewCreate;

import java.util.List;

/**
 * author:lgh on 2019-11-14 15:45
 * 辅助类
 */
public class RViewHelper<T extends ItemType> {

    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper swipeRefreshHelper;
    private RecyclerView recyclerView;
    private RViewAdapter<T> adapter;
    private int startPageNumber = 1;
    private boolean isSupportPaging;
    private SwipeRefreshHelper.SwipeRefreshListener swipeRefreshListener;
    private int currentPageNumber;//当前页
    private int mOrientation = RecyclerView.VERTICAL;

    private RViewHelper(Builder<T> builder) {
        this.swipeRefreshLayout = builder.create.createSwipeRefresh();
        this.context = builder.create.context();
        this.adapter = builder.create.createRViewAdapter();
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
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager)) {
                Log.e("==========", "LinearLayoutManager: ");
            }
            if (layoutManager instanceof GridLayoutManager) {
                Log.e("==========", "GridLayoutManager: ");
            }
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                Log.e("==========", "StaggeredGridLayoutManager: ");
            }
        }else {
            layoutManager = new LinearLayoutManager(context);
        }
        recyclerView.setLayoutManager(layoutManager);
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

    public void notifyAdapterDataSetChanged(List<T> datas) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setOrientation(int orientation) {
        ThreadLocal<String> local = ThreadLocal.withInitial(() -> "supplier");
        local.get();
        local.remove();
        if (mOrientation == orientation)
            return;
        this.mOrientation = orientation;
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(orientation);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 0;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.requestLayout();
    }

    public static class Builder<T extends ItemType> {
        private RViewCreate<T> create;
        private SwipeRefreshHelper.SwipeRefreshListener listener;

        public Builder(RViewCreate<T> create, SwipeRefreshHelper.SwipeRefreshListener listener) {
            this.create = create;
            this.listener = listener;
        }

        public RViewHelper<T> build() {
            return new RViewHelper<>(this);
        }
    }
}
