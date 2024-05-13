package com.example.lyos.CustomAdapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lyos.Models.Song;
import com.example.lyos.Models.UserInfo;
import com.example.lyos.Models.YoutubeVideo;
import com.example.lyos.R;
import com.example.lyos.databinding.OtherSongOptionsBottomSheetDialogLayoutBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class YoutubeVideoRecycleViewAdapter extends RecyclerView.Adapter<YoutubeVideoRecycleViewAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<YoutubeVideo> list;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewItem;
        public ImageView imageViewMoreOptionsAction;
        public TextView textViewTitle;
        public TextView textViewUserName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            imageViewMoreOptionsAction = itemView.findViewById(R.id.imageViewMoreOptionsAction);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
        }
    }

    public YoutubeVideoRecycleViewAdapter(Context context, ArrayList<YoutubeVideo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_youtube_object_recycleview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoutubeVideo item = list.get(position);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewUserName.setText(item.getChannelTitle());

        // Load image using Glide library
        Glide.with(context).load(item.getImageUrl()).into(holder.imageViewItem);

        holder.imageViewMoreOptionsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(item);
            }
        });
    }
    private OtherSongOptionsBottomSheetDialogLayoutBinding dialogLayoutBinding;
    private void showDialog(YoutubeVideo item) {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogLayoutBinding = OtherSongOptionsBottomSheetDialogLayoutBinding.inflate(inflater);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLayoutBinding.getRoot());

        Glide.with(context).load(item.getImageUrl()).into(dialogLayoutBinding.imageView);

        dialogLayoutBinding.textViewTitle.setText(item.getTitle());
        dialogLayoutBinding.textViewUserName.setText(item.getChannelTitle());
//        LinearLayout layoutLike = dialog.findViewById(R.id.layoutLike);
//        LinearLayout layoutAddToNextUp = dialog.findViewById(R.id.layoutAddToNextUp);
//        LinearLayout layoutAddToPlaylist = dialog.findViewById(R.id.layoutAddToPlaylist);
//        LinearLayout layoutGoToUserProfile = dialog.findViewById(R.id.layoutGoToUserProfile);
//        LinearLayout layoutViewComment = dialog.findViewById(R.id.layoutViewComment);

        dialogLayoutBinding.layoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
