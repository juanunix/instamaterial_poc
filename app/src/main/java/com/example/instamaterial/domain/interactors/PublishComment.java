package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Comment;
import com.example.instamaterial.domain.model.UnpublishedComment;
import com.example.instamaterial.domain.repository.CommentRepository;

public class PublishComment extends UseCase<UnpublishedComment, Comment> {
    private final CommentRepository commentRepository;

    public PublishComment(UseCaseExecutor useCaseExecutor, CommentRepository commentRepository) {
        super(useCaseExecutor);
        this.commentRepository = commentRepository;
    }

    @Override
    public ObservableTask<Comment> createObservableTask(final UnpublishedComment unpublishedComment) {
        return commentRepository.publishComment(unpublishedComment);
    }
}
