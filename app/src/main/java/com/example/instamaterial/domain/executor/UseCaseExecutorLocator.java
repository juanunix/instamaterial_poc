package com.example.instamaterial.domain.executor;

public interface UseCaseExecutorLocator {
    ThreadExecutor threadExecutor();

    PostExecutor postExecutor();

    UseCaseExecutor useCaseExecutor();
}
