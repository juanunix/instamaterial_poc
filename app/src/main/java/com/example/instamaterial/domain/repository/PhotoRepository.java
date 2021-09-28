package com.example.instamaterial.domain.repository;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.model.UnpublishedPhoto;

import java.util.List;

public interface PhotoRepository {
    ObservableTask<Photo> getPhoto(String photoId);

    ObservableTask<List<Photo>> getPhotos();

    ObservableTask<Photo> publishPhoto(UnpublishedPhoto unpublishedPhoto);

    ObservableTask<String> uploadPhoto(String photoUri);
}
