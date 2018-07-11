package com.example.hp.mycampus.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.News;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.HeroViewHolder> {

    // 数据
    private List<News> list;
    // 上下文环境
    private Context context;
    // 构造函数
    public RecycleAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // 这个方法返回viewholder，创建一个viewholder
    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 关联相关样式
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        HeroViewHolder heroViewHolder = new HeroViewHolder(v);
        return heroViewHolder;
    }

    @Override
    public void onBindViewHolder(HeroViewHolder holder, int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/"+ list.get(position).getPhotoId())));
        holder.name.setText(list.get(position).getName());
        holder.descrip.setText(list.get(position).getDes());
        // 点击事件也可以写在这里
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // RecycleView的特点之一，必须是自定义viewholder
    class HeroViewHolder extends RecyclerView.ViewHolder{

        CardView cardView; // 这个是为了之后可以添加点击事件
        ImageView imageView;
        TextView name;
        TextView descrip;

        public HeroViewHolder(View itemView) {
            super(itemView);
            // 和平时写viewholder是一样的
            cardView = (CardView) itemView.findViewById(R.id.cv_item);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            descrip = (TextView) itemView.findViewById(R.id.tv_des);
        }
    }
}