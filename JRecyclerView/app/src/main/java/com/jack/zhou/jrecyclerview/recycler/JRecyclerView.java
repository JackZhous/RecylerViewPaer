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
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jack.zhou.jrecyclerview.R;
import com.jack.zhou.jrecyclerview.adapter.JAdapter;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;
import com.jack.zhou.jrecyclerview.util.JLog;

/**
 * Created by "jackzhous" on 2016/7/29.
 */
public class JRecyclerView extends RecyclerView {

    private static final String TAG = "JRecyclerView";

    private JAdapter adapter;
    private Context context;
    private int header_layout;                          //头部布局文件
    private int body_layout;                            //胸部布局文件
    private LayoutManager manager;
    private GridLayoutManager default_manager;          //默认布局是表格布局

    private JViewHolder viewHolder;


    public JRecyclerView(Context context){

        super(context);
        this.context = context;

        initDefult(null);

    }

    public JRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        initDefult(attrs);

    }

    public JRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        initDefult(attrs);
    }

    /**
     * 初始化默认数据
     */
    public void initDefult(AttributeSet attrs){
        JLog.print(TAG, "initDefult");
        if(null != attrs){
            JLog.print(TAG, "not null");
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.recycler);
            header_layout = array.getResourceId(R.styleable.recycler_head, 0);
            body_layout = array.getResourceId(R.styleable.recycler_body, 0);
            array.recycle();
        }

        default_manager = new GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false);
        default_manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (0 == position) {
                    JLog.print(TAG, "spn " + default_manager.getSpanCount());
                    return default_manager.getSpanCount();
                }
                return 1;
            }
        });

    }

    /**
     * 初始化适配器
     */
    public void initAdapter(){

        adapter = new JAdapter(viewHolder, header_layout, body_layout);

    }


    public void setViewHolder(JViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }


    public void setManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
    }

    public void setContext(Context context) {
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
        if(null == manager){
            this.setLayoutManager(default_manager);
        }else{
            this.setLayoutManager(manager);
        }
        initAdapter();

        setAdapter(adapter);
    }

}


