package com.example.instamaterial.data.repository.user;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.User;

public interface UserDataSource {
    ObservableTask<User> get(String userId);

    ObservableTask<Boolean> put(User user);
}
