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

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jack.zhou.jrecyclerview.R;
import com.jack.zhou.jrecyclerview.adapter.JAdapter;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;
import com.jack.zhou.jrecyclerview.util.JLog;

/**
 * Created by "jackzhous" on 2016/7/29.
 */
public class JRecyclerView extends RecyclerView{

    private static final String TAG = "JRecyclerView";
    private RecyclerView recyclerView;
    private JAdapter adapter;
    private Activity context;
    private int header_layout;                          //头部布局文件
    private int body_layout;                            //胸部布局文件
    private LayoutManager manager;
    private GridLayoutManager default_manager;          //默认布局是表格布局

    private JViewHolder headHolder;
    private JViewHolder bodyHolder;


    public JRecyclerView(Activity context){
        super(context);
        this.context = context;

        JLog.print(TAG, "construct 1");
    }

    public JRecyclerView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initDefult(attrs);

        JLog.print(TAG, "construct 2");
    }

    public JRecyclerView(Activity context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        JLog.print(TAG, "construct 3");

        initDefult(attrs);
    }

    /**
     * 初始化默认数据
     */
    public void initDefult(AttributeSet attrs){

        if(null != attrs){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.recycler);
            header_layout = array.getResourceId(R.styleable.recycler_head, 0);
            body_layout = array.getResourceId(R.styleable.recycler_body, 0);
        }

        default_manager = new GridLayoutManager(context,2);
        default_manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (0 == position) {
                    return default_manager.getSpanCount();
                }
                return 1;
            }
        });

        manager = default_manager;
    }

    /**
     * 初始化适配器
     */
    public void initAdapter(){

        adapter = new JAdapter(context, header_layout, body_layout);
        adapter.setBodyViewHolder(bodyHolder);
        adapter.setHeaderViewHolder(headHolder);
    }


    public void setHeadHolder(JViewHolder headHolder) {
        this.headHolder = headHolder;
    }

    public void setBodyHolder(JViewHolder bodyHolder) {
        this.bodyHolder = bodyHolder;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setAdapter(JAdapter adapter) {
        this.adapter = adapter;
    }

    public void setManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    /**
     * 设置头部布局文件索引
     * @param header_layout
     */
    public void setHeader_layout(int header_layout) {
        this.header_layout = header_layout;
    }

    /**
     * 设置胸部布局文件索引
     * @param body_layout
     */
    public void setBody_layout(int body_layout) {
        this.body_layout = body_layout;
    }

    /**
     * 设置其recycleview并开始显示
     */
    public void startToShow(){

        initAdapter();
        this.setLayoutManager(manager);
        this.setAdapter(adapter);
    }

}
