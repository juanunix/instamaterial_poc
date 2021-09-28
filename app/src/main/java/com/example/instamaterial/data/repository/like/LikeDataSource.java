package com.example.instamaterial.data.repository.like;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Like;

import java.util.List;

public interface LikeDataSource {
    ObservableTask<List<Like>> getLikes(String photoId);

    ObservableTask<Boolean> toggleLike(Like like);
}
