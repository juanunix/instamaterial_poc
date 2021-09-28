package com.example.instamaterial.data.repository.like;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Like;
import com.example.instamaterial.domain.repository.LikeRepository;

import java.util.List;

public class LikeDataRepository implements LikeRepository {
    private final LikeDataSource likeDataSource;

    public LikeDataRepository(LikeDataSource likeDataSource) {
        this.likeDataSource = likeDataSource;
    }

    @Override
    public ObservableTask<List<Like>> getLikes(String photoId) {
        return likeDataSource.getLikes(photoId);
    }

    @Override
    public ObservableTask<Boolean> toggleLike(Like like) {
        return likeDataSource.toggleLike(like);
    }
}
