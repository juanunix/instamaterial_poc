package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.User;
import com.example.instamaterial.domain.repository.UserRepository;

public class GetUser extends UseCase<String, User> {
    private final UserRepository userRepository;

    public GetUser(UseCaseExecutor useCaseExecutor, UserRepository userRepository) {
        super(useCaseExecutor);
        this.userRepository = userRepository;
    }

    @Override
    public ObservableTask<User> createObservableTask(final String userId) {
        return userRepository.getUser(userId);
    }
}
