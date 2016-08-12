package com.jack.zhou.jrecyclerview.adapter;

import android.view.View;

/**
 * 用法：
 *
 * Created by "jackzhous" on 2016/8/3.
 */
public interface JViewHolder {

    /**
     * 传入的是头部布局文件转换后的view，在改方法内你可以找到你布局文件的组件id
     * @param v
     */
    void findHead(View v);


    /**
     * 传入的是胸部布局文件转换后的view，在改方法内你可以找到你布局文件的组件id
     * @param v
     */
    void findBody(View v);


    /**
     * 设置你布局文件里面组件的内容
     * 头部默认是第0个元素，就不需要传位置了
     */
    void setHead();


    /**
     * 设置你布局文件里面组件的内容
     * position  需要显示哪个位置的item
     */
    void setBody(int position);

    /**
     * 你需要显示多少个item
     * @return item个数 = 头部 + 胸部
     */
    int size();

}
