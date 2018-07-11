package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.adapter.RecycleAdapter;
import com.example.hp.mycampus.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends Activity {

    // 1. 声明一个对象，一个适配器 和 一个数据结构
    RecyclerView recyclerView;
    RecycleAdapter adapter;
    List<News> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // 2. 获取对象
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 3. 设定 布局管理，如果设定为 gridLayoutManger 则为 Grid 布局
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        // 4. 初始化数据
        list = new ArrayList<>();
        initData();
        // 5. 实例化一个adapter,并建立关联
        adapter = new RecycleAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        News news0=new News((String) this.getResources().getText(R.string.news_name_1),(String) this.getResources().getText(R.string.news_des_1),"school.png");
        News news1=new News((String) this.getResources().getText(R.string.news_name_2),(String) this.getResources().getText(R.string.news_des_2),"school.png");
        News news2=new News((String) this.getResources().getText(R.string.news_name_3),(String) this.getResources().getText(R.string.news_des_3),"school.png");
        News news3=new News((String) this.getResources().getText(R.string.news_name_1),(String) this.getResources().getText(R.string.news_des_1),"school.png");
        News news4=new News((String) this.getResources().getText(R.string.news_name_2),(String) this.getResources().getText(R.string.news_des_2),"school.png");
        News news5=new News((String) this.getResources().getText(R.string.news_name_3),(String) this.getResources().getText(R.string.news_des_3),"school.png");

        list.add(news0);
        list.add(news1);
        list.add(news2);
        list.add(news3);
        list.add(news4);
        list.add(news5);
    }
}

