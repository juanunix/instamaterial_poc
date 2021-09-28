package com.example.instamaterial.domain.interactors;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.repository.PhotoRepository;

public class GetPhoto extends UseCase<String, Photo> {
    private final PhotoRepository photoRepository;

    public GetPhoto(UseCaseExecutor useCaseExecutor, PhotoRepository photoRepository) {
        super(useCaseExecutor);
        this.photoRepository = photoRepository;
    }

    @Override
    public ObservableTask<Photo> createObservableTask(final String photoId) {
        return photoRepository.getPhoto(photoId);
    }
}
