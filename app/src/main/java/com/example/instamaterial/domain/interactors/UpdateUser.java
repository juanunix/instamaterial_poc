package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.User;
import com.example.instamaterial.domain.repository.UserRepository;

public class UpdateUser extends UseCase<User, Boolean> {
    private final UserRepository userRepository;

    public UpdateUser(UseCaseExecutor useCaseExecutor, UserRepository userRepository) {
        super(useCaseExecutor);
        this.userRepository = userRepository;
    }

    @Override
    public ObservableTask<Boolean> createObservableTask(User user) {
        return userRepository.putUser(user);
    }
}
