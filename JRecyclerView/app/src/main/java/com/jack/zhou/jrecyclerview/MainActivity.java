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

package com.jack.zhou.jrecyclerview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jack.zhou.jrecyclerview.adapter.JAdapter;
import com.jack.zhou.jrecyclerview.recycler.JRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("viewpager + recyclerView");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initResources();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initResources(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler);


        init();
    }

    public void init(){

        JRecyclerView jview = new JRecyclerView(this, recyclerView);
        ArrayList<ImageView> headerImageList = new ArrayList<>();
        int[] draw = new int[]{R.drawable.ic_answer_banner, R.drawable.ic_certified_id, R.drawable.ic_group_header_bg};

        for(int i = 0; i < draw.length; i++){
            ImageView v = new ImageView(this);
            ViewGroup.LayoutParams image_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setImageDrawable(getResources().getDrawable(draw[i]));
            v.setLayoutParams(image_params);
            headerImageList.add(v);
        }

        JAdapter adapter = new JAdapter(this);
        adapter.setHeaderImageList(headerImageList);
        adapter.setmDot(getDrawable(R.drawable.ic_circle_white));
        adapter.setmDotSelectColor(R.color.colorPrimary);

        draw = new int[]{R.drawable.img_tips_error_banner_tv, R.drawable.img_tips_error_load_error, R.drawable.img_tips_error_no_permission, R.drawable.img_tips_error_not_foud, R.drawable.img_tips_error_not_loin, R.drawable.img_tips_error_space_no_data, R.drawable.img_tips_error_space_no_permission, R.drawable.img_tips_live_room_locked};
        ArrayList<Drawable> bodyImageList = new ArrayList<>();
        ArrayList<String> bodyTextList = new ArrayList<>();
        for(int i = 0 ; i < draw.length; i++){
            bodyImageList.add(getDrawable(draw[i]));
            bodyTextList.add("图片简介"+i);
        }
        adapter.setBodyImageList(bodyImageList);
        adapter.setBodyTextList(bodyTextList);

        jview.setAdapter(adapter);
        jview.setManager(new GridLayoutManager(this,2));
        jview.startToShow();
    }

}
