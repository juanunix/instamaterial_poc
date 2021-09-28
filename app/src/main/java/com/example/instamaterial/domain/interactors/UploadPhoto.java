package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.repository.PhotoRepository;

public class UploadPhoto extends UseCase<String, String> {
    private final PhotoRepository photoRepository;

    public UploadPhoto(UseCaseExecutor useCaseExecutor, PhotoRepository photoRepository) {
        super(useCaseExecutor);
        this.photoRepository = photoRepository;
    }

    @Override
    public ObservableTask<String> createObservableTask(String localPhotoUri) {
        return photoRepository.uploadPhoto(localPhotoUri);
    }
}
