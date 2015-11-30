package com.dream.one.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.one.R;
import com.dream.one.activity.WebActivity;
import com.dream.one.model.ArticleItem;
import com.dream.one.service.HttpHelper;
import com.joooonho.SelectableRoundedImageView;


import java.util.List;

/**
 * Created by CNKI-0000 on 2015/11/30.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ArticleItem> articleItems;
    public RecycleViewAdapter(List<ArticleItem> articleItems){
        this.articleItems = articleItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list_item_card_big,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;

        }catch (Exception e){

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final ArticleItem articleItem = articleItems.get(position);
        if(articleItem.contengImg != null){
            HttpHelper.getInstance().displayImage(articleItem.contengImg,viewHolder.contentImg);
        }
        if(articleItem.useranme == null){
            articleItem.useranme = "匿名";
        }
        viewHolder.usernameView.setText(articleItem.useranme);
        viewHolder.typeView.setText(articleItem.typeName);
        if(articleItem.userLogo != null){
            HttpHelper.getInstance().displayImage(articleItem.userLogo,viewHolder.userLogoView);
        }
        viewHolder.titleView.setText(articleItem.title);
        viewHolder.dateView.setText(articleItem.date);
        viewHolder.contentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url",articleItem.url);
                intent.setClass(v.getContext(),WebActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView contentImg;
        TextView titleView;
        SelectableRoundedImageView userLogoView;
        TextView usernameView;
        TextView typeView;
        TextView dateView;

        public ViewHolder(final View itemView) {
            super(itemView);
            contentImg = (ImageView) itemView.findViewById(R.id.iv_big_contentImg);
            titleView = (TextView) itemView.findViewById(R.id.tv_big_title);
            userLogoView = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_big_userlogo);
            usernameView = (TextView) itemView.findViewById(R.id.tv_big_username);
            typeView = (TextView) itemView.findViewById(R.id.tv_big_type);
            dateView = (TextView) itemView.findViewById(R.id.tv_big_date);
        }
    }
}
