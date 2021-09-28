package com.example.instamaterial.data.repository.loggedUser;

import com.example.instamaterial.domain.common.ObservableTask;

public interface LoggedUserDataSource {
    ObservableTask<String> get();
}
