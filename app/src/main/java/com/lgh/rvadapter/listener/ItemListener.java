package com.lgh.rvadapter.listener;

import android.view.View;

/**
 * author:lgh on 2019-11-14 16:12
 */
public interface ItemListener<T> {

    void onItemClick(View view, T entity, int position);

   default boolean onItemLongClick(View view, T entity, int position){
       return false;
   }
}
