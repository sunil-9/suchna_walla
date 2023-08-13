package com.example.suchnawalla.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suchnawalla.R;
import com.example.suchnawalla.helper.FileDownloader;
import com.example.suchnawalla.model.NoticeModel;

import java.io.File;
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

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
            ProgressDialog progress = new ProgressDialog(context);
            progress.setTitle("Loading");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

            NoticeModel noticeModel = noticeModelList.get(position);
            holder.tvTitle.setText(noticeModel.getTitle());
            holder.tvDesc.setText(noticeModel.getDesc());
            holder.tvHappy.setText(String.valueOf(noticeModel.getHappy()));
            holder.tvSad.setText(String.valueOf(noticeModel.getSad()));
            if(noticeModel.getDocumentUrl()!=null){
                holder.tvDownload.setVisibility(View.VISIBLE);
                holder.tvDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progress.show();
                        FileDownloader.downloadPdf(context, noticeModel.getDocumentUrl(), noticeModel.getDocumentName(), new FileDownloader.DownloadListener() {
                            @Override
                            public void onDownloadComplete(File pdfFile) {
                                progress.dismiss();
                                Toast.makeText(context, "download complete.", Toast.LENGTH_SHORT).show();
                                // You can open the PDF file using an intent or any other method you prefer
                            }

                            @Override
                            public void onDownloadFailed(String errorMessage) {
                                progress.dismiss();
                                Toast.makeText(context, "download failed.", Toast.LENGTH_SHORT).show();
                                // Handle download failure
                            }
                        });
                    }
                });
            }

            holder.tvSad.setOnClickListener(v -> {
                holder.tvSad.setTextColor(context.getResources().getColor(R.color.red));
                holder.tvHappy.setTextColor(R.color.colorPrimary);
            });
        }

        @Override
        public int getItemCount() {
            return noticeModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle,tvDesc,tvHappy,tvSad,tvDownload;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDesc= itemView.findViewById(R.id.tvDesc);
                tvHappy = itemView.findViewById(R.id.tvHappy);
                tvSad = itemView.findViewById(R.id.tvSad);
                tvDownload = itemView.findViewById(R.id.tvDownload);
            }
        }
}
