/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :Class4
 *        description :
 *
 *         created by jackzhous at  11/07/2016 12:12:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.zhou.jrecyclerview.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by "jackzhous" on 2016/7/29.
 */
public class JRecyclerAdapter {

    private static final String TAG = "JRecyclerAdapter";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private Context context;

    public JRecyclerAdapter(Context context){
        this.context = context;

    }


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 设置其recycleview并开始显示
     */
    public void startToShow(){

    }

    /**
     * 检查其成员是否配置完成，没有完成需要提示
     * @return
     */
    private boolean check_data_complete(){
        String error_msg = null;
        boolean ret = false;
        if(recyclerView == null){
            error_msg = "请设置recycleview";
        }else if(adapter == null){
            error_msg = "请配置recycler的适配器adapter";
        }else if(null == manager){
            error_msg = "请配置recyclee的布局管理器";
        }
        error_msg = "配置正确";
        return true;
    }

}
