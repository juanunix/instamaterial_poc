package com.example.instamaterial.data.locator;


import com.example.instamaterial.data.repository.comment.CommentDataRepository;
import com.example.instamaterial.data.repository.comment.CommentDataSource;
import com.example.instamaterial.data.repository.comment.FakeCommentDataSource;
import com.example.instamaterial.data.repository.like.FakeLikeDataSource;
import com.example.instamaterial.data.repository.like.LikeDataRepository;
import com.example.instamaterial.data.repository.like.LikeDataSource;
import com.example.instamaterial.data.repository.loggedUser.AuthenticatedUserDataRepository;
import com.example.instamaterial.data.repository.loggedUser.FakeLoggedUserDataSource;
import com.example.instamaterial.data.repository.loggedUser.LoggedUserDataSource;
import com.example.instamaterial.data.repository.photo.FakePhotoDataSource;
import com.example.instamaterial.data.repository.photo.PhotoDataRepository;
import com.example.instamaterial.data.repository.photo.PhotoDataSource;
import com.example.instamaterial.data.repository.user.FakeUserDataSource;
import com.example.instamaterial.data.repository.user.UserDataRepository;
import com.example.instamaterial.data.repository.user.UserDataSource;
import com.example.instamaterial.domain.repository.AuthenticatedUserRepository;
import com.example.instamaterial.domain.repository.CommentRepository;
import com.example.instamaterial.domain.repository.LikeRepository;
import com.example.instamaterial.domain.repository.PhotoRepository;
import com.example.instamaterial.domain.repository.RepositoryLocator;
import com.example.instamaterial.domain.repository.UserRepository;


public class DataServiceLocator implements RepositoryLocator {

    private PhotoRepository photoRepository;
    private PhotoDataSource photoDataSource;

    private UserRepository userRepository;
    private UserDataSource userDataSource;

    private LikeRepository likeRepository;
    private LikeDataSource likeDataSource;

    private CommentRepository commentRepository;
    private CommentDataSource commentDataSource;

    private AuthenticatedUserRepository authenticatedUserRepository;
    private LoggedUserDataSource loggedUserDataSource;

    public static DataServiceLocator getInstance() {
        return Instance.instance;
    }

    @Override
    public PhotoRepository photoRepository() {
        if (photoRepository == null) {
            photoRepository = new PhotoDataRepository(photoDataSource());
        }
        return photoRepository;
    }

    private PhotoDataSource photoDataSource() {
        if (photoDataSource == null) {
            photoDataSource = new FakePhotoDataSource();
        }
        return photoDataSource;
    }

    @Override
    public UserRepository userRepository() {
        if (userRepository == null) {
            userRepository = new UserDataRepository(userDataSource());
        }
        return userRepository;
    }

    private UserDataSource userDataSource() {
        if (userDataSource == null) {
            userDataSource = new FakeUserDataSource();
        }
        return userDataSource;
    }

    @Override
    public LikeRepository likeRepository() {
        if (likeRepository == null) {
            likeRepository = new LikeDataRepository(likeDataSource());
        }
        return likeRepository;
    }

    private LikeDataSource likeDataSource() {
        if (likeDataSource == null) {
            likeDataSource = new FakeLikeDataSource();
        }
        return likeDataSource;
    }

    @Override
    public CommentRepository commentRepository() {
        if (commentRepository == null) {
            commentRepository = new CommentDataRepository(commentDataSource());
        }
        return commentRepository;
    }

    private CommentDataSource commentDataSource() {
        if (commentDataSource == null) {
            commentDataSource = new FakeCommentDataSource();
        }
        return commentDataSource;
    }

    @Override
    public AuthenticatedUserRepository loggedUserRepository() {
        if (authenticatedUserRepository == null) {
            authenticatedUserRepository = new AuthenticatedUserDataRepository(loggedUserDataSource());
        }
        return authenticatedUserRepository;
    }

    private LoggedUserDataSource loggedUserDataSource() {
        if (loggedUserDataSource == null) {
            loggedUserDataSource = new FakeLoggedUserDataSource();
        }
        return loggedUserDataSource;
    }

    private static class Instance {
        private static final DataServiceLocator instance = new DataServiceLocator();
    }
}
