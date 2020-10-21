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

import java.util.List;

/**
 * @author lgh on 2020/10/14:14:58
 * @description 将recyclerview作为item
 */
public class RvItem implements RViewItem<ItemType> {

    private static final String TAG = "RvItem";

    //    private RecyclerView.RecycledViewPool mViewPool;

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
        creatHolder(holder, entity, position);
    }

    private void creatHolder(RViewHolder holder, ItemType entity, int position) {
        holder.setIsRecyclable(false);
        Context context = holder.getConvertView().getContext();
        List<RvInfo.Bean> datas = ((RvInfo) entity).getInfos();

        RViewCreate<RvInfo.Bean> rvItem = new RViewCreate<RvInfo.Bean>() {
            @Override
            public Context context() {
                return context;
            }

            @Override
            public SwipeRefreshLayout createSwipeRefresh() {
                return null;
            }

            @Override
            public RecyclerView createRecyclerView() {
                RecyclerView recyclerView = holder.getView(R.id.item_rv);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(RecyclerView.HORIZONTAL);
                //                manager.setRecycleChildrenOnDetach(true);//设置回收
                if (position == 0)
                    manager.setReverseLayout(true);//反转绘制
                recyclerView.setLayoutManager(manager);
                //                recyclerView.setRecycledViewPool(mViewPool);//设置缓存pool
                return recyclerView;
            }

            @Override
            public RViewAdapter<RvInfo.Bean> createRViewAdapter() {
                RViewAdapter<RvInfo.Bean> adapter = new RViewAdapter<>(datas, mBeanRViewItem);
                adapter.setItemListener(listener);
                return adapter;
            }

            @Override
            public boolean isSupportPaging() {
                return false;
            }
        };

        RViewHelper<RvInfo.Bean> beanRViewHelper = new RViewHelper.Builder<>(rvItem, null).build();
        beanRViewHelper.notifyAdapterDataSetChanged(((RvInfo) entity).getInfos());

    }


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

    ItemListener<RvInfo.Bean> listener = (view, entity, position) -> {
        view.findViewById(R.id.item_rv_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "you click the image", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.item_rv_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "you click the content text", Toast.LENGTH_SHORT).show();
            }
        });

    };
}
