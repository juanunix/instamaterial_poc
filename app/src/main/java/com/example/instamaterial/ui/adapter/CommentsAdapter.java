package com.example.instamaterial.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instamaterial.R;
import com.example.instamaterial.ui.model.CommentItem;

import java.util.ArrayList;
import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private int lastAnimatedPosition = -1;
    private final int avatarSize;

    private final List<CommentItem> commentItems = new ArrayList<>();

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public CommentsAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        ((CommentViewHolder) viewHolder).bindView(commentItems.get(position), avatarSize);
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    public void addAll(List<CommentItem> commentItems, boolean animated) {
        this.commentItems.addAll(commentItems);
        if (animated) {
            notifyItemRangeInserted(0, commentItems.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public boolean add(CommentItem commentItem) {
        boolean result = false;
        if (getItemPosition(commentItem.getCommentId()) != -1) {
            commentItems.add(commentItem);
            notifyItemInserted(commentItems.size());
            result = true;
        }
        return result;
    }

    private int getItemPosition(String commentId) {
        int position = -1;
        for (int i = 0; i < commentItems.size() && position == -1; i++) {
            CommentItem commentItem = commentItems.get(i);
            if (commentItem.getCommentId().equals(commentId)) {
                position = i;
            }
        }
        return position;
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserAvatar;
        TextView tvComment;
        CommentItem commentItem;

        public CommentViewHolder(View view) {
            super(view);
            //ButterKnife.bind(this, view);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            ;
            tvComment = itemView.findViewById(R.id.tvComment);
            ;
        }

        public void bindView(CommentItem commentItem, int avatarSize) {
            this.commentItem = commentItem;

            /*Picasso.with(itemView.getContext())
                    .load(Uri.parse(commentItem.getUserAvatarUrl()))
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .into(ivUserAvatar);*/

            Glide.with(itemView.getContext())
                    .load(Uri.parse(commentItem.getUserAvatarUrl()))
                    .into(ivUserAvatar);

            tvComment.setText(commentItem.getContent());
        }
    }
}
