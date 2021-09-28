package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.repository.AuthenticatedUserRepository;

public class GetAuthenticatedUserUid extends UseCase<Void, String> {
    private final AuthenticatedUserRepository authenticatedUserRepository;

    public GetAuthenticatedUserUid(UseCaseExecutor useCaseExecutor,
                                   AuthenticatedUserRepository authenticatedUserRepository) {
        super(useCaseExecutor);
        this.authenticatedUserRepository = authenticatedUserRepository;
    }

    @Override
    public ObservableTask<String> createObservableTask(Void input) {
        return authenticatedUserRepository.getUserUid();
    }
}