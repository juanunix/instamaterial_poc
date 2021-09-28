package com.example.instamaterial.domain.interactors;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Comment;
import com.example.instamaterial.domain.repository.CommentRepository;

import java.util.List;

public class GetPhotoComments extends UseCase<String, List<Comment>> {
    private final CommentRepository commentRepository;

    public GetPhotoComments(
            UseCaseExecutor useCaseExecutor, CommentRepository commentRepository) {
        super(useCaseExecutor);
        this.commentRepository = commentRepository;
    }

    @Override
    public ObservableTask<List<Comment>> createObservableTask(final String photoId) {
        return commentRepository.getComments(photoId);
    }
}
