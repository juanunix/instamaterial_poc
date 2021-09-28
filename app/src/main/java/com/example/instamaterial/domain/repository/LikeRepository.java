package com.example.instamaterial.domain.repository;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Like;

import java.util.List;

public interface LikeRepository {
    ObservableTask<List<Like>> getLikes(String photoId);

    ObservableTask<Boolean> toggleLike(Like like);
}
