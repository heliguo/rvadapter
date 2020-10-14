package com.lgh.rvadapter.impl.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lgh.rvadapter.R;
import com.lgh.rvadapter.RViewHelper;
import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;
import com.lgh.rvadapter.core.RViewCreate;
import com.lgh.rvadapter.holder.RViewHolder;
import com.lgh.rvadapter.impl.ItemTypeImpl;
import com.lgh.rvadapter.impl.bean.RvInfo;
import com.lgh.rvadapter.listener.ItemListener;
import com.lgh.rvadapter.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2020/10/14:14:58
 * @description 将recyclerview作为item
 */
public class RvItem implements RViewItem<ItemType> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<RvInfo.Bean> mInfos = new ArrayList<>();

    @Override
    public int getItemLayout() {
        return R.layout.item_rv_layout;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(ItemType entity, int position) {
        return entity.getType() == ItemTypeImpl.RV;
    }

    @Override
    public void convert(RViewHolder holder, ItemType entity, int position) {
        mContext = holder.getConvertView().getContext();
        mRecyclerView = holder.getView(R.id.item_rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        RViewHelper<RvInfo.Bean> beanRViewHelper = new RViewHelper.Builder<>(rvItem, null).build();
        if (entity instanceof RvInfo) {
            mInfos = ((RvInfo) entity).getInfos();
            beanRViewHelper.notifyAdapterDataSetChanged(mInfos);
        }
    }

    public RViewCreate<RvInfo.Bean> rvItem = new RViewCreate<RvInfo.Bean>() {

        @Override
        public Context context() {
            return mContext;
        }

        @Override
        public SwipeRefreshLayout createSwipeRefresh() {
            return null;
        }

        @Override
        public RecyclerView createRecyclerView() {

            return mRecyclerView;
        }

        @Override
        public RViewAdapter<RvInfo.Bean> createRViewAdapter() {
            RViewAdapter<RvInfo.Bean> adapter = new RViewAdapter<>(mInfos, mBeanRViewItem);
            adapter.setItemListener(listener);
            return adapter;
        }

        @Override
        public boolean isSupportPaging() {
            return false;
        }
    };

    RViewItem<RvInfo.Bean> mBeanRViewItem = new RViewItem<RvInfo.Bean>() {
        @Override
        public int getItemLayout() {
            return R.layout.item_rv_list;
        }

        @Override
        public boolean openClick() {
            return true;
        }

        @Override
        public boolean isItemView(RvInfo.Bean entity, int position) {
            return true;
        }

        @Override
        public void convert(RViewHolder holder, RvInfo.Bean entity, int position) {
            TextView textView = holder.getView(R.id.item_rv_txt);
            textView.setText(entity.getContent());
        }
    };

    ItemListener<RvInfo.Bean> listener = new ItemListener<RvInfo.Bean>() {
        @Override
        public void onItemClick(View view, RvInfo.Bean entity, int position) {
            Toast.makeText(mContext, entity.getContent(), Toast.LENGTH_SHORT).show();
        }

    };
}
