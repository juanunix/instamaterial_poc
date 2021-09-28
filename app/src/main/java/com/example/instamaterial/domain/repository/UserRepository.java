package com.example.instamaterial.domain.repository;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.User;

public interface UserRepository {
    ObservableTask<User> getUser(String userId);

    ObservableTask<Boolean> putUser(User user);
}
