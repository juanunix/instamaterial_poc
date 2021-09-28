package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Like;
import com.example.instamaterial.domain.repository.LikeRepository;

public class LikePhoto extends UseCase<Like, Boolean> {
    private final LikeRepository likeRepository;

    public LikePhoto(UseCaseExecutor useCaseExecutor, LikeRepository likeRepository) {
        super(useCaseExecutor);
        this.likeRepository = likeRepository;
    }

    @Override
    public ObservableTask<Boolean> createObservableTask(final Like like) {
        return likeRepository.toggleLike(like);
    }
}