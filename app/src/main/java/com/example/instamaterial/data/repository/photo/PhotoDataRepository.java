package com.example.instamaterial.data.repository.photo;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.model.UnpublishedPhoto;
import com.example.instamaterial.domain.repository.PhotoRepository;

import java.util.List;

public class PhotoDataRepository implements PhotoRepository {
    private final PhotoDataSource photoDataSource;

    public PhotoDataRepository(PhotoDataSource photoDataSource) {
        this.photoDataSource = photoDataSource;
    }

    @Override
    public ObservableTask<Photo> getPhoto(String photoId) {
        return photoDataSource.getPhoto(photoId);
    }

    @Override
    public ObservableTask<List<Photo>> getPhotos() {
        return photoDataSource.getPhotos();
    }

    @Override
    public ObservableTask<Photo> publishPhoto(UnpublishedPhoto unpublishedPhoto) {
        return photoDataSource.publishPhoto(unpublishedPhoto);
    }

    @Override
    public ObservableTask<String> uploadPhoto(String photoUri) {
        return photoDataSource.uploadPhoto(photoUri);
    }
}
