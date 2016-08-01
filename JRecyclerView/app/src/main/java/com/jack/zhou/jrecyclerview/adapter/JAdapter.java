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
public class JAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;                           //头部
    private static final int TYPE_CONTENT = 1;                          //胸部
    private static final String TAG = "JAdapter";

    private LayoutInflater inflater;
    private Activity context;
    private View headerView;
    private View bodyView;

    private ArrayList<Drawable> bodyImageList;
    private ArrayList<String>   bodyTextList;                           //body内容
    private ArrayList<ImageView> headerImageList;                       //头被内容
    private Drawable mDot;
    private Drawable mDotSelectColor;


    public JAdapter(Activity context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JLog.print(TAG, "onCreateViewHolder -- viewType = " + viewType);
        RecyclerView.ViewHolder holder = null;
        if(viewType == TYPE_HEADER){
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_header, null);
            holder = new HeaderViewHolder(headerView);
        }else{
            bodyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content, null);
            holder = new BodyViewHolder(bodyView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JLog.print(TAG, "onBindViewHolder -- position = " + position);
        if(holder instanceof BodyViewHolder){
            ((BodyViewHolder)holder).image.setImageDrawable(bodyImageList.get(position));
            ((BodyViewHolder)holder).tv.setText(bodyTextList.get(position));
        }else{
            //((HeaderViewHolder)holder).viewPager
        }
    }

    @Override
    public int getItemCount() {
        return bodyImageList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }

    /**
     * 头部viewholder
     */
    private class HeaderViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout mDotContainer;                             //头部存放小圆点的容器
        public ViewPager viewPager;                                    //头部存放导航图片

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mDotContainer = (LinearLayout)itemView.findViewById(R.id.dot);
            viewPager = (ViewPager)itemView.findViewById(R.id.view_pager);
        }
    }

    /**
     * 胸部viewholder
     */
    private class BodyViewHolder extends  RecyclerView.ViewHolder{
        public ImageView image;
        public TextView tv;

        public BodyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.imageView);
            tv = (TextView)itemView.findViewById(R.id.tv_info);
        }
    }

    //==========================setter==============================================================
    public void setBodyImageList(ArrayList<Drawable> bodyImageList) {
        this.bodyImageList = bodyImageList;
    }

    public void setBodyTextList(ArrayList<String> bodyTextList) {
        this.bodyTextList = bodyTextList;
    }

    public void setHeaderImageList(ArrayList<ImageView> headerImageList) {
        this.headerImageList = headerImageList;
    }

    public void setmDot(Drawable drawable){
        this.mDot = drawable;
    }

    public void setmDotSelectColor(int color){
        if(mDot != null){
            Drawable.ConstantState state_user = mDot.getConstantState();
            mDotSelectColor = (state_user == null) ? mDotSelectColor : state_user.newDrawable().mutate();
            DrawableCompat.setTint(mDotSelectColor, ContextCompat.getColor(context, color));
        }
    }
    //==========================setter==============================================================


    //====================viewpager适配及监听========================================================
    private class PagerAdapter extends android.support.v4.view.PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % headerImageList.size();
            if(position < 0){
                position = position + headerImageList.size();
            }

            ImageView image = headerImageList.get(position);                                            //查询当前view是否已被添加 如果被添加就让其父删除他
            ViewParent parent = image.getParent();
            if(null != parent){
                ((ViewGroup)parent).removeView(image);
            }

            container.addView(image);
            return image;
        }
    }


    private class PageListener implements ViewPager.OnPageChangeListener{
        private View cachView = null;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            JLog.print(TAG, "onPageSelected position = " + position);
            position = position % headerImageList.size();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //====================viewpager适配及监听========================================================

}
