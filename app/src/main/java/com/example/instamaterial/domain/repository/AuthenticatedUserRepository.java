package com.example.instamaterial.domain.repository;

import com.example.instamaterial.domain.common.ObservableTask;

public interface AuthenticatedUserRepository {
    ObservableTask<String> getUserUid();
}
