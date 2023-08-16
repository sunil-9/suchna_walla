package com.example.suchnawalla.adapter;

import static android.content.ContentValues.TAG;
import static com.example.suchnawalla.util.Constants.KEY_COLLECTION_REACTIONS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suchnawalla.R;
import com.example.suchnawalla.helper.FileDownloader;
import com.example.suchnawalla.model.NoticeModel;
import com.example.suchnawalla.model.ReactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    List<NoticeModel> noticeModelList;
    List<ReactionModel> reactionModelList;
    int reaction = 0;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    NoticeModel noticeModel;
    Context context;

    public NoticeAdapter() {
    }

    public NoticeAdapter(Context context, List<NoticeModel> noticeModelList) {
        this.noticeModelList = noticeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false));
    }


    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        noticeModel = noticeModelList.get(position);
        holder.tvTitle.setText(noticeModel.getTitle());
        holder.tvDesc.setText(noticeModel.getDesc());
        holder.tvHappy.setText(String.valueOf(noticeModel.getHappy()));
        holder.tvSad.setText(String.valueOf(noticeModel.getSad()));
        holder.tvDate.setText(noticeModel.getDate());
        if (noticeModel.getReaction() == 1)
            holder.ivSad.setImageDrawable(context.getDrawable(R.drawable.ic_sad_active));
        else if (noticeModel.getReaction() == 2)
            holder.ivHappy.setImageDrawable(context.getDrawable(R.drawable.ic_happy_active));

        if (noticeModel.getImageUrl() != null) {
            holder.cvImage.setVisibility(View.VISIBLE);
            Picasso
                    .get()
                    .load(noticeModel.getImageUrl())
                    .into(holder.ivImage);

        }

        if (noticeModel.getDocumentUrl() != null) {
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
        holder.ivHappy.setOnClickListener(v -> {
            reaction = 2; // 0 for sad, 1 for happy
            if(noticeModelList.get(position).getReaction()==reaction)
                Toast.makeText(context, "You have already reacted", Toast.LENGTH_SHORT).show();
            else
                changeReaction(reaction, holder, noticeModelList.get(position));
        });
        holder.ivSad.setOnClickListener(v -> {
            reaction = 1; // 0 for sad, 1 for happy
            if(noticeModelList.get(position).getReaction()==reaction)
                Toast.makeText(context, "You have already reacted", Toast.LENGTH_SHORT).show();
            else
                changeReaction(reaction, holder, noticeModelList.get(position));
        });

        holder.tvSad.setOnClickListener(v -> {
            reaction = 1; // 0 for sad, 1 for happy
            if(noticeModelList.get(position).getReaction()==reaction)
                Toast.makeText(context, "You have already reacted", Toast.LENGTH_SHORT).show();
            else
                changeReaction(reaction, holder, noticeModelList.get(position));

        });
        holder.tvHappy.setOnClickListener(v -> {
            reaction = 2; // 0 for sad, 1 for happy
            if(noticeModelList.get(position).getReaction()==reaction)
                Toast.makeText(context, "You have already reacted", Toast.LENGTH_SHORT).show();
            else
                changeReaction(reaction, holder, noticeModelList.get(position));
        });
    }

//public CompletableFuture<Integer> hasReactedAsync(NoticeModel noticeModel) {
//    CompletableFuture<Integer> future = new CompletableFuture<>();
//    firebaseFirestore.collection(KEY_COLLECTION_REACTIONS)
//            .document(noticeModel.getId())
//            .get()
//            .addOnSuccessListener(documentSnapshot -> {
//                ReactionModel reactionModel = documentSnapshot.toObject(ReactionModel.class);
//                if (reactionModel != null) {
//                    if (reactionModel.getReaction() == 1) {
//                        future.complete(1);
//                    } else if (reactionModel.getReaction() == 2) {
//                        future.complete(2);
//                    } else {
//                        future.complete(0);
//                    }
//                } else {
//                    future.complete(0);
//                }
//            })
//            .addOnFailureListener(future::completeExceptionally);
//
//    return future;
//}

    public void changeReaction(int reaction, NoticeAdapter.ViewHolder holder, NoticeModel noticeModel) {

        firebaseFirestore
                .collection(KEY_COLLECTION_REACTIONS)
                .document(noticeModel.getId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ReactionModel reactionModel1 = documentSnapshot.toObject(ReactionModel.class);
                    if (reactionModel1 == null) {
                        reactionModel1 = new ReactionModel();
                        reactionModel1.setNoticeID(noticeModel.getId());
                        reactionModel1.setUserID(firebaseAuth.getCurrentUser().getUid());
                        reactionModel1.setReaction(reaction);
                    } else {
                        if (reaction == 1)
                            noticeModel.setHappy(noticeModel.getHappy() - 1);
                        else if (reaction == 2)
                            noticeModel.setSad(noticeModel.getSad() - 1);
                        reactionModel1.setReaction(reaction);
                    }
                    if (reaction == 1)
                        noticeModel.setSad(noticeModel.getSad() + 1);
                    else if (reaction == 2)
                        noticeModel.setHappy(noticeModel.getHappy() + 1);
                    reactionModel1.setReaction(reaction);
                    //update the reaction
                    firebaseFirestore.collection(KEY_COLLECTION_REACTIONS)
                            .document(noticeModel.getId())
                            .set(reactionModel1)
                            .addOnSuccessListener(aVoid -> {
//                                Toast.makeText(context, "reaction updated", Toast.LENGTH_SHORT).show();
                            });
                    //update the notice
                    firebaseFirestore.collection("Notice")
                            .document(noticeModel.getId())
                            .update("sad", noticeModel.getSad(), "happy", noticeModel.getHappy())
                            .addOnSuccessListener(aVoid -> {
//                                Toast.makeText(context, "notice updated", Toast.LENGTH_SHORT).show();
                            });

                });
        if (reaction == 1) {
            holder.ivHappy.setImageDrawable(context.getDrawable(R.drawable.ic_happy));
            holder.ivSad.setImageDrawable(context.getDrawable(R.drawable.ic_sad_active));
        } else if (reaction == 2) {
            holder.ivSad.setImageDrawable(context.getDrawable(R.drawable.ic_sad));
            holder.ivHappy.setImageDrawable(context.getDrawable(R.drawable.ic_happy_active));
        }
    }

    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvHappy, tvSad, tvDownload, tvDate;
        ImageView ivImage, ivHappy, ivSad;
        CardView cvImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvHappy = itemView.findViewById(R.id.tvHappy);
            tvSad = itemView.findViewById(R.id.tvSad);
            tvDownload = itemView.findViewById(R.id.tvDownload);
            ivImage = itemView.findViewById(R.id.ivImage);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivSad = itemView.findViewById(R.id.ivSad);
            ivHappy = itemView.findViewById(R.id.ivHappy);
        }
    }
}
