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

package com.jack.zhou.jrecyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.zhou.jrecyclerview.R;
import com.jack.zhou.jrecyclerview.util.JLog;

import java.util.ArrayList;

/**
 * Created by "jackzhous" on 2016/7/29.
 */
public class JAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;                           //头部
    public static final int TYPE_CONTENT = 1;                          //胸部
    private static final String TAG = "JAdapter";

    private int header_layout;
    private int body_layout;
    private JViewHolder viewHolder;


    public JAdapter(JViewHolder viewHolder, int header, int bodyer) {
        JLog.print(TAG, "JAdapter -- header = " + header);
        this.header_layout = header;
        this.body_layout = bodyer;
        this.viewHolder = viewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JLog.print(TAG, "onCreateViewHolder -- viewType = " + viewType);
        View view = null;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(header_layout, null);
            viewHolder.findHead(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(body_layout, null);
            viewHolder.findBody(view);
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JLog.print(TAG, "onBindViewHolder -- position = " + position);
        if (getItemViewType(position) == TYPE_CONTENT) {                                        //说明是胸部
            position = position - 1;                                                            //头部占用了一个位置，所以胸部必须减掉头部的1
            viewHolder.setBody(position);
        } else {
            viewHolder.setHead();
        }
    }

    @Override
    public int getItemCount() {

        JLog.print(TAG, "getItemCount  " + viewHolder.size());
        return viewHolder.size();
    }

    @Override
    public int getItemViewType(int position) {
        JLog.print(TAG, "getItemViewType  " );
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }


    private class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }
    }

}