package com.jack.zhou.jrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用法：
 *  1. 该类用于适配器适配的holder用的
 *     头部和胸部必须继承该类，并且实现public abstract void set();方法，该方法用于实现其布局里面元素的设置
 * Created by "jackzhous" on 2016/8/3.
 */
public abstract class JViewHolder extends RecyclerView.ViewHolder {

    private int holderType;                       //holdertype类型

    public JViewHolder(View itemView,int viewType) {
        super(itemView);
        setHolderType(viewType);
    }

    public int getHolderType(){
        return holderType;
    }

    public void setHolderType(int viewType){
        this.holderType = viewType;
    }

    /**
     * 实现你自己布局文件里面元素的设定
     */
    public abstract void onBindView(JViewHolder j_holder, int position);

}
