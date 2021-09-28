package com.example.instamaterial.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.instamaterial.R;
import com.example.instamaterial.databinding.ActivityMainBinding;
import com.example.instamaterial.ui.activity.BaseActivity;
import com.example.instamaterial.ui.activity.comments.CommentsActivity;
import com.example.instamaterial.ui.adapter.FeedAdapter;
import com.example.instamaterial.ui.adapter.FeedItemAnimator;
import com.example.instamaterial.ui.locator.AppServiceLocator;
import com.example.instamaterial.ui.model.FeedItem;
import com.example.instamaterial.ui.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class MainActivity extends BaseActivity implements FeedAdapter.OnFeedItemClickListener, MainPresenter.View {

    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private static final String EXTRA_PHOTO_ID = "photo_id";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    private ActivityMainBinding binding;
    private FeedAdapter feedAdapter;
    private boolean pendingIntroAnimation;
    private MainPresenter mainPresenter;

    public static Intent getCallingIntent(Context context, String photoId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(MainActivity.ACTION_SHOW_LOADING_ITEM);
        intent.putExtra(EXTRA_PHOTO_ID, photoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();

        getWindow().setBackgroundDrawable(null);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
            mainPresenter.requestLoggedUser();
        }
    }

    private void initPresenter() {
        MainPresenterLocator presenterLocator =
                AppServiceLocator.getInstance().plusActivityServiceLocator();
        mainPresenter = presenterLocator.mainPresenter(this);
        mainPresenter.attach(this);
    }

    @Override
    public void setupFeed(String userId) {
        feedAdapter = new FeedAdapter(this, userId);
        feedAdapter.setOnFeedItemClickListener(this);

        binding.rvFeed.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFeed.setItemAnimator(new FeedItemAnimator());
        binding.rvFeed.setAdapter(feedAdapter);
    }

    @Override
    public void updateFeed(List<FeedItem> feedItems) {
        binding.rvFeed.setVisibility(View.VISIBLE);
        binding.btnCreate.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
        binding.llNoElements.setVisibility(View.GONE);
        feedAdapter.addAll(feedItems, true);
    }

    @Override
    public void updatePhotoLike(String photoId, String userId, int likeTapFlags) {
        feedAdapter.updatePhotoLike(photoId, userId, likeTapFlags);
    }

    @Override
    public void addFeedItem(final FeedItem feedItem) {
        binding.llNoElements.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.rvFeed.smoothScrollToPosition(0);
                feedAdapter.addLoadingView(feedItem);
            }
        }, 500);
    }

    @Override
    public void showEmptyFeed() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.btnCreate.setVisibility(View.VISIBLE);
        binding.llNoElements.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginUser() {
    }

    @Override
    public void showErrorWhileRequestingFeed() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.btnCreate.setVisibility(View.GONE);
        Snackbar
                .make(findViewById(R.id.root), R.string.feed_cannot_request_elements,
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> mainPresenter.requestFeedItems())
                .show();
    }

    @Override
    public void showErrorWhileLikingPhoto() {
        Snackbar
                .make(findViewById(R.id.root), R.string.feed_cannot_send_action,
                        Snackbar.LENGTH_INDEFINITE)
                .show();
    }

    @Override
    public void showErrorWhileUpdatingFeed() {
        Snackbar
                .make(findViewById(R.id.root), R.string.feed_error_updating_feed,
                        Snackbar.LENGTH_INDEFINITE)
                .show();
    }

    @Override
    public void showErrorNotLoggedUser() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.GONE);
        binding.llNoElements.setVisibility(View.GONE);
        Snackbar
                .make(findViewById(R.id.root), R.string.user_not_logged,
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> loginUser())
                .show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            String photoId = intent.getStringExtra(EXTRA_PHOTO_ID);
            mainPresenter.requestFeedItem(photoId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out) {
            mainPresenter.requestSignOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startIntroAnimation() {

        binding.btnCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        /*getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);*/
        binding.btnCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(800)
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }

    @Override
    public void onLikeClick(FeedItem feedItem, int likeSource) {
        mainPresenter.onRequestLike(feedItem.getPhotoId(), likeSource);
    }

    @Override
    public void onCommentsClick(View v, FeedItem feedItem) {
        final Intent intent =
                CommentsActivity.getCallingIntent(this, feedItem.getPhotoId(),
                        mainPresenter.getCurrentUserUid());
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        binding.btnCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += binding.btnCreate.getWidth() / 2;
        //TODO PENDIENTE
    /*TakePhotoActivity.startCameraFromLocation(startingLocation, this);
    overridePendingTransition(0, 0);*/
    }
}