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

package com.jack.zhou.jrecyclerview.util;

import android.util.Log;

/**
 * Created by "jackzhous" on 2016/8/1.
 */
public class JLog {

    private static final String JTAG = "jackzhous";
    public static boolean isDebug = false;

    public static void print(String TAG, String message){
        if(isDebug){
            Log.i("author: " + JTAG + " -- " + TAG, " msg: " + message);
        }
    }

}
