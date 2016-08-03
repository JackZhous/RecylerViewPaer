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

    private View headerView;
    private View bodyView;

    private ArrayList<Drawable> bodyImageList;
    private ArrayList<String> bodyTextList;                           //body内容

    private int header_layout;
    private int body_layout;

    private JViewHolder headerViewHolder;
    private JViewHolder bodyViewHolder;


    public JAdapter(Activity context, int header, int bodyer) {
        this.header_layout = header;
        this.body_layout = bodyer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JLog.print(TAG, "onCreateViewHolder -- viewType = " + viewType);
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_HEADER) {
            headerView = LayoutInflater.from(parent.getContext()).inflate(header_layout, null);
           //holder = new HeaderViewHolder(headerView);
        } else {
            bodyView = LayoutInflater.from(parent.getContext()).inflate(body_layout, null);
            holder = new BodyViewHolder(bodyView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JLog.print(TAG, "onBindViewHolder -- position = " + position);
        if (holder instanceof BodyViewHolder) {
            position = position - 1;                                                        //头部占用了一个位置，所以胸部必须减掉头部的1
            ((BodyViewHolder) holder).image.setImageDrawable(bodyImageList.get(position));
            ((BodyViewHolder) holder).tv.setText(bodyTextList.get(position));
        } else {
            //initViewPager(holder);
        }
    }

    @Override
    public int getItemCount() {
        return bodyImageList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }

    /**
     * 胸部viewholder
     */
    private class BodyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView tv;

        public BodyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            tv = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }

    public void setHeaderViewHolder(JViewHolder headerViewHolder) {
        this.headerViewHolder = headerViewHolder;
    }

    public void setBodyViewHolder(JViewHolder bodyViewHolder) {
        this.bodyViewHolder = bodyViewHolder;
    }
}