package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.Subscriber;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.model.UnpublishedPhoto;
import com.example.instamaterial.domain.repository.PhotoRepository;

public class PublishPhoto extends UseCase<UnpublishedPhoto, Photo> {
    private final UploadPhoto uploadPhoto;
    private final PhotoRepository photoRepository;

    public PublishPhoto(UseCaseExecutor useCaseExecutor,
                        UploadPhoto uploadPhoto,
                        PhotoRepository photoRepository) {
        super(useCaseExecutor);
        this.uploadPhoto = uploadPhoto;
        this.photoRepository = photoRepository;
    }

    @Override
    public ObservableTask<Photo> createObservableTask(final UnpublishedPhoto unpublishedPhoto) {
        return new ObservableTask<Photo>() {
            @Override
            public void run(Subscriber<Photo> result) {
                String photoUri = uploadPhoto.createObservableTask(unpublishedPhoto.getPhotoUri()).getResult();
                photoRepository.publishPhoto(unpublishedPhoto.withPhotoUri(photoUri)).run(result);
            }
        };
    }
}
