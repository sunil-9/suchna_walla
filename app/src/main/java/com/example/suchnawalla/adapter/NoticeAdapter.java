package com.example.suchnawalla.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suchnawalla.R;
import com.example.suchnawalla.model.NoticeModel;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    List<NoticeModel> noticeModelList;
    Context context;
        public NoticeAdapter() {
        }

    public NoticeAdapter( Context context,List<NoticeModel> noticeModelList) {
        this.noticeModelList = noticeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notice,parent,false));
    }

        @Override
        public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
            NoticeModel noticeModel = noticeModelList.get(position);
            holder.tvTitle.setText(noticeModel.getTitle());
            holder.tvDesc.setText(noticeModel.getDesc());
            holder.tvHappy.setText(String.valueOf(noticeModel.getHappy()));
            holder.tvSad.setText(String.valueOf(noticeModel.getSad()));
        }

        @Override
        public int getItemCount() {
            return noticeModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle,tvDesc,tvHappy,tvSad;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDesc= itemView.findViewById(R.id.tvDesc);
                tvHappy = itemView.findViewById(R.id.tvHappy);
                tvSad = itemView.findViewById(R.id.tvSad);
            }
        }
}
