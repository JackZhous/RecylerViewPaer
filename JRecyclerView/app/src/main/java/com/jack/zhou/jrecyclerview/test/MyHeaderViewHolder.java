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

import com.jack.zhou.jrecyclerview.R;
import com.jack.zhou.jrecyclerview.adapter.JAdapter;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;
import com.jack.zhou.jrecyclerview.util.JLog;

import java.util.ArrayList;

/**
 * 头部布局holder,可以自定义头部布局，这里面取出来就行
 * Created by "jackzhous" on 2016/8/3.
 */
public class MyHeaderViewHolder extends JViewHolder {

    private static final String TAG = "MyHeaderViewHolder";
    public LinearLayout mDotContainer;                             //头部存放小圆点的容器
    public ViewPager viewPager;                                    //头部存放导航图片
    private Drawable unselectDot;
    private Drawable selectDot;
    private Activity context;

    private ArrayList<ImageView> headerImageList;                       //头被内容

    public MyHeaderViewHolder(Activity context,View itemView) {
        super(itemView, JAdapter.TYPE_HEADER);
        this.context = context;
        mDotContainer = (LinearLayout)itemView.findViewById(R.id.dot);
        viewPager = (ViewPager)itemView.findViewById(R.id.view_pager);

        int[] draw = new int[]{R.drawable.ic_answer_banner, R.drawable.ic_certified_id, R.drawable.ic_group_header_bg};

        for(int i = 0; i < draw.length; i++){
            ImageView v = new ImageView(context);
            ViewGroup.LayoutParams image_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setImageDrawable(context.getResources().getDrawable(draw[i]));
            v.setLayoutParams(image_params);
            headerImageList.add(v);
        }

    }

    @Override
    public void onBindView(JViewHolder j_holder, int position) {
        MyHeaderViewHolder holder = null;
        if(j_holder instanceof MyHeaderViewHolder){
            holder = (MyHeaderViewHolder)j_holder;
        }else{
            JLog.print(TAG, "holder error");
            return;
        }

        initViewPager(holder);
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

    /**
     * 初始化viewpager小圆点
     * @param holder
     */
    private void initViewPager(MyHeaderViewHolder holder){

        ViewPager pager = holder.viewPager;
        LinearLayout linearLayout = holder.mDotContainer;
        if(null != linearLayout && linearLayout.getChildAt(0) != null){                                     //纺织重复添加
            return;
        }
        unselectDot = context.getDrawable(R.drawable.ic_circle_white);
        Drawable.ConstantState state_user = unselectDot.getConstantState();
        selectDot = (state_user == null) ? unselectDot : state_user.newDrawable().mutate();
        DrawableCompat.setTint(selectDot, ContextCompat.getColor(context, R.color.colorPrimary));

        ViewGroup.LayoutParams dot_params = new ViewGroup.LayoutParams(30,30);
        ImageView v = new ImageView(context);
        v.setImageDrawable(selectDot);
        v.setLayoutParams(dot_params);
        linearLayout.addView(v, 0);
        for(int i = 1; i < headerImageList.size(); i++){
            v = new ImageView(context);
            v.setImageDrawable(unselectDot);
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
