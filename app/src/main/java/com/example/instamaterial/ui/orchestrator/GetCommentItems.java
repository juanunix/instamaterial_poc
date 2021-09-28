package com.example.instamaterial.ui.orchestrator;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.Subscriber;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.interactors.GetPhotoComments;
import com.example.instamaterial.domain.model.Comment;
import com.example.instamaterial.ui.model.CommentItem;

import java.util.LinkedList;
import java.util.List;

public class GetCommentItems extends UseCase<String, List<CommentItem>> {
    private final GetPhotoComments getPhotoComments;
    private final GetCommentItem getCommentItem;

    public GetCommentItems(UseCaseExecutor useCaseExecutor, GetPhotoComments getPhotoComments,
                           GetCommentItem getCommentItem) {
        super(useCaseExecutor);
        this.getPhotoComments = getPhotoComments;
        this.getCommentItem = getCommentItem;
    }

    @Override
    public ObservableTask<List<CommentItem>> createObservableTask(final String photoId) {
        return new ObservableTask<List<CommentItem>>() {
            @Override
            public void run(Subscriber<List<CommentItem>> result) {
                List<CommentItem> commentItems = new LinkedList<>();
                List<Comment> comments = getPhotoComments.createObservableTask(photoId).getResult();
                if (comments != null) {
                    for (Comment comment : comments) {
                        commentItems.add(getCommentItem.createObservableTask(comment).getResult());
                    }
                }
                result.onSuccess(commentItems);
            }
        };
    }
}
