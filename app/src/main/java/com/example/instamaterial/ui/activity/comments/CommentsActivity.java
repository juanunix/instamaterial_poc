package com.example.instamaterial.ui.activity.comments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instamaterial.R;
import com.example.instamaterial.databinding.ActivityCommentsBinding;
import com.example.instamaterial.ui.activity.BaseActivity;
import com.example.instamaterial.ui.adapter.CommentsAdapter;
import com.example.instamaterial.ui.locator.AppServiceLocator;
import com.example.instamaterial.ui.model.CommentItem;
import com.example.instamaterial.ui.utils.Utils;
import com.example.instamaterial.ui.view.SendCommentButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class CommentsActivity extends BaseActivity implements SendCommentButton.OnSendClickListener,
        CommentsPresenter.View {

    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    public static final String PHOTO_ID = "photo_id";
    public static final String USER_ID = "user_id";

    /*R.id.contentRoot LinearLayout
    contentRoot;
    R.id.rvComments RecyclerView
    rvComments;
    R.id.llNoComments LinearLayout
    llNoComments;
    R.id.llAddComment LinearLayout
    llAddComment;
    R.id.etComment EditText
    etComment;
    R.id.btnSendComment SendCommentButton
    btnSendComment;
    R.id.flUploading FrameLayout
    flUploading;*/

    private ActivityCommentsBinding binding;
    private LinearLayoutManager linearLayoutManager;
    private CommentsAdapter commentsAdapter;
    private CommentsPresenter commentsPresenter;
    private String photoId;
    private String userId;
    private boolean uploading;

    public static Intent getCallingIntent(Context context, String photoId, String userId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(PHOTO_ID, photoId);
        intent.putExtra(USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();
        photoId = getIntent().getStringExtra(PHOTO_ID);
        userId = getIntent().getStringExtra(USER_ID);


        if (savedInstanceState == null) {
            binding.contentRoot.getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            binding.contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                            startIntroAnimation();
                            return true;
                        }
                    });
            commentsPresenter.requestComments(photoId);
        }
        binding.btnSendComment.setOnSendClickListener(this);
    }

    private void initPresenter() {
        CommentsPresenterLocator presenterLocator =
                AppServiceLocator.getInstance().plusActivityServiceLocator();
        commentsPresenter = presenterLocator.commentsPresenter();
        commentsPresenter.attach(this);
    }

    @Override
    public void setupComments() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        binding.rvComments.setLayoutManager(linearLayoutManager);

        commentsAdapter = new CommentsAdapter(this);
        binding.rvComments.setAdapter(commentsAdapter);
        binding.rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                /*if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }*/
            }
        });
    }

    @Override
    public void onFinishedRequestingComments() {
    }

    @Override
    public void updateComments(List<CommentItem> commentItems) {
        commentsAdapter.addAll(commentItems, true);
        binding.rvComments.setVisibility(View.VISIBLE);
        binding.llNoComments.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyComments() {
        binding.rvComments.setVisibility(View.GONE);
        binding.llNoComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorWhileRequestingComments() {
        Snackbar
                .make(binding.contentRoot, R.string.comments_cannot_request_comments, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> commentsPresenter.requestComments(photoId))
                .show();
    }

    @Override
    public void onCommentAdded(CommentItem commentItem) {
        uploading = false;
        binding.flUploading.setVisibility(View.GONE);
        binding.llNoComments.setVisibility(View.GONE);
        binding.rvComments.setVisibility(View.VISIBLE);
        if (commentsAdapter.add(commentItem)) {
            linearLayoutManager.smoothScrollToPosition(binding.rvComments, null, commentsAdapter.getItemCount());
        }
    }

    @Override
    public void showUploading() {
        binding.flUploading.setVisibility(View.VISIBLE);
        uploading = true;
    }

    @Override
    public void showErrorWhileUploading() {
        uploading = false;
        binding.flUploading.setVisibility(View.GONE);
        Snackbar
                .make(findViewById(R.id.root), R.string.comments_cannot_publish_comment,
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> commentsPresenter.requestPublishComment(photoId, binding.etComment.getText().toString()))
                .show();
    }

    private void startIntroAnimation() {
        binding.contentRoot.setScaleY(0.1f);
        binding.contentRoot.setPivotY(getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0));
        binding.llAddComment.setTranslationY(200);
        binding.contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        binding.llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        if (!uploading) {
            binding.contentRoot.animate()
                    .translationY(Utils.getScreenHeight(this))
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            CommentsActivity.super.onBackPressed();
                            overridePendingTransition(0, 0);
                        }
                    })
                    .start();
        }
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
            commentsPresenter.requestPublishComment(photoId, binding.etComment.getText().toString());
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            binding.etComment.setText(null);
            binding.btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(binding.etComment.getText())) {
            binding.btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }
}
