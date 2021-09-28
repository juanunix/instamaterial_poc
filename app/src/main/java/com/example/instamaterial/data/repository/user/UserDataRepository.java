package com.example.instamaterial.data.repository.user;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.User;
import com.example.instamaterial.domain.repository.UserRepository;

public class UserDataRepository implements UserRepository {
    private final UserDataSource userDataSource;

    public UserDataRepository(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public ObservableTask<User> getUser(String userId) {
        return userDataSource.get(userId);
    }

    @Override
    public ObservableTask<Boolean> putUser(User user) {
        return userDataSource.put(user);
    }
}
