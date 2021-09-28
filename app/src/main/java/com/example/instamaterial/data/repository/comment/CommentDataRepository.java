package com.example.instamaterial.data.repository.comment;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Comment;
import com.example.instamaterial.domain.model.UnpublishedComment;
import com.example.instamaterial.domain.repository.CommentRepository;

import java.util.List;

public class CommentDataRepository implements CommentRepository {
    private final CommentDataSource commentDataSource;

    public CommentDataRepository(CommentDataSource commentDataSource) {
        this.commentDataSource = commentDataSource;
    }

    @Override
    public ObservableTask<List<Comment>> getComments(String photoId) {
        return commentDataSource.getComments(photoId);
    }

    @Override
    public ObservableTask<Comment> publishComment(UnpublishedComment unpublishedComment) {
        return commentDataSource.publishComment(unpublishedComment);
    }
}
