# JRecyclerView
A ViewPager+RecyclerView implemention base on RecyclerView. Support the header(ViewPager) and the body(RecyclerView)

# 项目效果图
(https://github.com/JackZhous/RecylerViewPaer/tree/master/JRecyclerView/app/src/test/java/demo.gif)

# 特性
1. 扩展自RecyclerView的一款组件JRecyclerView
2. 支持头部ViewPager的导航栏
3. 支持胸部自定义的布局显示
2. 头部head和胸部body高度自定义化，可根据自己的需求自定义布局layout
3. 用户可根据自己需要设置布局管理器，也可以使用默认的表格布局

# 用法

## import


```gradle
// Yes, I have switched to jitpack.io.

repositories {
    ...
    maven { url "https://jitpack.io" }
    ...
}

dependencies {
    ...
    ...
}
```
## Basic Usage:

``` xml
<com.jack.zhou.jrecyclerview.recycler.JRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        app:head="@layout/recyler_header"
        app:body="@layout/recycler_content"
        android:id="@+id/recycler"/>
	<!--app:head 添加头部布局  app:body 添加胸部布局 -->
```

## code

### 代码简介
1. 获取JrecyclerView组件id
2. 为其设置JViewHolder, 自定义ViewHolder实现JViewHolder接口，实现方法可参考项目中的MyHeaderViewHolder实例类
3. 显示JRecyclerView

```java
        recyclerView = (JRecyclerView)this.findViewById(R.id.recycler);                             //找到其id
        MyHeaderViewHolder viewHolder = new MyHeaderViewHolder(this);                               //自定义一个形如MyHeaderViewHolder，该holder必须实现JViewHolder接口，并实现其内部的方法
        recyclerView.setViewHolder(viewHolder);                                                     //为你的JRecyclerView设置JViewHolder
//        recyclerView.setGrid_count(3);                                                            //设置默认表格布局的表格列数
//        recyclerView.setManager(new LinearLayoutManager(this));                                   //你可以设置LayoutManager也可以不用设置，我内部默认了一个有两列的GridLayoutManager
//        recyclerView.setHeader_layout(R.layout.recyler_header);                                   //布局可以这里设置，也可以在xml里面使用app:head="@layout/recyler_header"
//        recyclerView.setBody_layout(R.layout.recycler_content);
//        recyclerView.setContext(this);
        recyclerView.startToShow();                                                                 //开始显示
```

License
-------

    Copyright 2016 jackzhous

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
