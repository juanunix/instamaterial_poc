package com.example.instamaterial.data.repository.photo;


import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.model.UnpublishedPhoto;

import java.util.List;

public interface PhotoDataSource {
    ObservableTask<Photo> getPhoto(String photoId);

    ObservableTask<List<Photo>> getPhotos();

    ObservableTask<Photo> publishPhoto(UnpublishedPhoto unpublishedPhoto);

    ObservableTask<String> uploadPhoto(String photoUri);
}
