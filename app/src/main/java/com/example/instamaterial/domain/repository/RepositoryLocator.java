package com.example.instamaterial.domain.repository;

public interface RepositoryLocator {
    PhotoRepository photoRepository();

    UserRepository userRepository();

    LikeRepository likeRepository();

    CommentRepository commentRepository();

    AuthenticatedUserRepository loggedUserRepository();
}
