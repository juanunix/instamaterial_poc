package com.example.instamaterial.data.repository.comment;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Comment;
import com.example.instamaterial.domain.model.UnpublishedComment;

import java.util.List;

public interface CommentDataSource {
    ObservableTask<List<Comment>> getComments(String photoId);

    ObservableTask<Comment> publishComment(UnpublishedComment unpublishedComment);
}
