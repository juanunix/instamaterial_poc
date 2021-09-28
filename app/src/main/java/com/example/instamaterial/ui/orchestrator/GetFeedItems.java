package com.example.instamaterial.ui.orchestrator;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.Subscriber;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.interactors.GetPhotos;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.ui.model.FeedItem;

import java.util.LinkedList;
import java.util.List;

public class GetFeedItems extends UseCase<String, List<FeedItem>> {
    private final GetPhotos getPhotos;
    private final GetFeedItem getFeedItem;

    public GetFeedItems(UseCaseExecutor useCaseExecutor, GetPhotos getPhotos,
                        GetFeedItem getFeedItem) {
        super(useCaseExecutor);
        this.getPhotos = getPhotos;
        this.getFeedItem = getFeedItem;
    }

    @Override
    public ObservableTask<List<FeedItem>> createObservableTask(String input) {
        return new ObservableTask<List<FeedItem>>() {
            @Override
            public void run(Subscriber<List<FeedItem>> result) {
                List<FeedItem> feedItems = new LinkedList<>();
                List<Photo> photos = getPhotos.createObservableTask(null).getResult();

                for (Photo photo : photos) {
                    FeedItem feedItem = getFeedItem.createObservableTask(photo.getId()).getResult();
                    if (feedItem != null) {
                        feedItems.add(feedItem);
                    }
                }

                result.onSuccess(feedItems);
            }
        };
    }
}
