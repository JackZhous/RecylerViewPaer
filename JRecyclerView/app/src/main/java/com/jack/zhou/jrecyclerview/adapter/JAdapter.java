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
            position = position - 1;                                                        //头部占用了一个位置，所以胸部必须减掉头部的1
            ((BodyViewHolder)holder).image.setImageDrawable(bodyImageList.get(position));
            ((BodyViewHolder)holder).tv.setText(bodyTextList.get(position));
        }else{
            initViewPager(holder);
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

            image = (ImageView)itemView.findViewById(R.id.image);
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

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }


    private class PageListener implements ViewPager.OnPageChangeListener{
        private ImageView cachView = null;
        private LinearLayout linearLayout;

        public PageListener(LinearLayout linearLayout){
            this.linearLayout = linearLayout;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            JLog.print(TAG, "onPageSelected position = " + position);
            position = position % headerImageList.size();

            if(cachView != null){
                cachView.setImageDrawable(mDot);
            }
            ImageView v = (ImageView)linearLayout.getChildAt(position);
            if(null != v){
                v.setImageDrawable(mDotSelectColor);
            }
            cachView = v;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 初始化viewpager小圆点
     * @param holder
     */
    private void initViewPager(RecyclerView.ViewHolder holder){
        HeaderViewHolder headHolder = null;
        if(holder instanceof  HeaderViewHolder){
            headHolder = (HeaderViewHolder)holder;
        }else{
            JLog.print(TAG, "initViewPager holder错误");
            return;
        }
        ViewPager pager = headHolder.viewPager;
        LinearLayout linearLayout = headHolder.mDotContainer;

        ViewGroup.LayoutParams dot_params = new ViewGroup.LayoutParams(30,30);
        //ViewGroup.LayoutParams image_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView v = new ImageView(context);
        v.setImageDrawable(mDotSelectColor);
        v.setLayoutParams(dot_params);
        linearLayout.addView(v, 0);
        for(int i = 1; i < headerImageList.size(); i++){
            v = new ImageView(context);
            v.setImageDrawable(mDot);
            v.setLayoutParams(dot_params);
            linearLayout.addView(v, i);
        }

        pager.setAdapter(new PagerAdapter());
        pager.setOnPageChangeListener(new PageListener(linearLayout));
        pager.setPageMargin(20);
        pager.setCurrentItem(headerImageList.size()*100);
    }
    //====================viewpager适配及监听========================================================

}
