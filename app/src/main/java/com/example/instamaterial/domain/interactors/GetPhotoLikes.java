package com.example.instamaterial.domain.interactors;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Like;
import com.example.instamaterial.domain.repository.LikeRepository;

import java.util.List;

public class GetPhotoLikes extends UseCase<String, List<Like>> {
    private final LikeRepository likeRepository;

    public GetPhotoLikes(UseCaseExecutor useCaseExecutor, LikeRepository likeRepository) {
        super(useCaseExecutor);
        this.likeRepository = likeRepository;
    }

    @Override
    public ObservableTask<List<Like>> createObservableTask(final String photoId) {
        return likeRepository.getLikes(photoId);
    }
}
