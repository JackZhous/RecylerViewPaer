package com.jack.zhou.jrecyclerview.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.zhou.jrecyclerview.R;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;
import com.jack.zhou.jrecyclerview.util.JLog;

import java.util.ArrayList;

/**
 * 显例holder，类描述：
 *          1. 该类必须实现JViewHolder接口及其方法
 *          2. 初始化你需要显示的数据，如下例的一些集合list
 *          3. 实现你的监听等
 * Created by "jackzhous" on 2016/8/3.
 */
public class MyHeaderViewHolder implements JViewHolder {

    private static final String TAG = "MyHeaderViewHolder";

    private Activity context;

    public LinearLayout   headDotContainer;                                                         //头部存放小圆点的容器
    public ViewPager      headViewPager;                                                            //头部ViewPager引用
    private ImageView     BodyImageView;                                                            //胸部ImageView组件引用
    private TextView      bodyInfo;                                                                 //胸部TextView组件

    private Drawable unselectDot;                                                                   //没有选中的小圆点图标
    private Drawable selectDot;                                                                     //选中的小圆点图标

    private ArrayList<ImageView> headerImageList = new ArrayList<>();                               //存放 头部导航栏图片list
    private ArrayList<Drawable>  bodyImageList = new ArrayList<>();                                 //存放 胸部每个Item显示的图片集合
    private ArrayList<String>    textList = new ArrayList<>();                                      //存放 头部每个Item显示的string集合

    public MyHeaderViewHolder(Activity context) {

        this.context = context;

        initDisplayData();

    }

    /**
     * 初始化显示的数组
     */
    private void initDisplayData(){
        int[] draw = new int[]{R.drawable.ic_answer_banner, R.drawable.ic_certified_id, R.drawable.ic_group_header_bg};                                         //头部轮播图片

        //将图片其添加到集合
        for(int i = 0; i < draw.length; i++){
            ImageView v = new ImageView(context);
            ViewGroup.LayoutParams image_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setImageDrawable(context.getResources().getDrawable(draw[i]));
            v.setLayoutParams(image_params);
            headerImageList.add(v);
        }

        //胸部需要显示的Item图片  并添加到集合
        draw = new int[]{R.drawable.img_tips_error_banner_tv, R.drawable.img_tips_error_load_error, R.drawable.img_tips_error_no_permission, R.drawable.img_tips_error_not_foud, R.drawable.img_tips_error_not_loin, R.drawable.img_tips_error_space_no_data, R.drawable.img_tips_error_space_no_permission, R.drawable.img_tips_live_room_locked};
        for(int i = 0; i < draw.length; i++){
            bodyImageList.add(context.getDrawable(draw[i]));
            textList.add("图片简介 " + i);
        }
    }

    /**
     * 描述：
     *      此方法里你必须实现：
     *      找出头部的组件ID，并把其索引赋值给你的变量
     * @param v  头部布局文件的View
     */
    @Override
    public void findHead(View v) {
        headDotContainer = (LinearLayout)v.findViewById(R.id.dot);
        headViewPager = (ViewPager)v.findViewById(R.id.view_pager);

    }

    /**
     * 描述：
     *      此方法里你必须实现：
     *      找出胸部的组件ID，并把其索引赋值给你的变量
     * @param v  胸部布局文件的View
     */
    @Override
    public void findBody(View v) {
        BodyImageView = (ImageView)v.findViewById(R.id.image);
        bodyInfo = (TextView)v.findViewById(R.id.tv_info);
    }

    /**
     * 描述：
     *      此方法里你必须实现：
     *      头部默认是第0个Item，所以没有传值
     *      你必须在此方法内部实现你组件的监听 或者赋值
     */
    @Override
    public void setHead() {
        if(null != headDotContainer && headDotContainer.getChildAt(0) != null){                                     //纺织重复添加
            return;
        }
        JLog.print(TAG, "header size " + headerImageList.size());
        unselectDot = context.getDrawable(R.drawable.ic_circle_white);
        Drawable.ConstantState state_user = unselectDot.getConstantState();
        selectDot = (state_user == null) ? unselectDot : state_user.newDrawable().mutate();
        DrawableCompat.setTint(selectDot, ContextCompat.getColor(context, R.color.colorPrimary));

        ViewGroup.LayoutParams dot_params = new ViewGroup.LayoutParams(30,30);
        ImageView view = new ImageView(context);
        view.setImageDrawable(selectDot);
        view.setLayoutParams(dot_params);
        headDotContainer.addView(view, 0);
        JLog.print(TAG, "header size " + headerImageList.size());
        for(int i = 1; i < headerImageList.size(); i++){
            view = new ImageView(context);
            view.setImageDrawable(unselectDot);
            view.setLayoutParams(dot_params);
            headDotContainer.addView(view, i);
        }

        headViewPager.setAdapter(new PagerAdapter());
        headViewPager.setOnPageChangeListener(new PageListener(headDotContainer));
        headViewPager.setPageMargin(20);
        headViewPager.setCurrentItem(headerImageList.size()*100);
    }

    /**
     * 描述：
     *      此方法里你必须实现：
     *      你必须在此方法内部实现你组件的监听 或者赋值
     *  position  是将要显示的item位置
     */
    @Override
    public void setBody(int position) {
        BodyImageView.setImageDrawable(bodyImageList.get(position));
        bodyInfo.setText(textList.get(position));
    }

    /**
     * 描述：
     *      此方法里你必须实现：
     *        返回显示的item个数 = 头部1个 + 胸部n个
     * @return
     */
    @Override
    public int size() {
        return bodyImageList.size() + 1;
    }



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
                cachView.setImageDrawable(unselectDot);
            }
            ImageView v = (ImageView)linearLayout.getChildAt(position);
            if(null != v){
                v.setImageDrawable(selectDot);
            }
            cachView = v;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //====================viewpager适配及监听========================================================
}
