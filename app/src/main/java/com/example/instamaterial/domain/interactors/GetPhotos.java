package com.example.instamaterial.domain.interactors;

import com.example.instamaterial.domain.common.ObservableTask;
import com.example.instamaterial.domain.common.UseCase;
import com.example.instamaterial.domain.executor.UseCaseExecutor;
import com.example.instamaterial.domain.model.Photo;
import com.example.instamaterial.domain.repository.PhotoRepository;

import java.util.List;

public class GetPhotos extends UseCase<Void, List<Photo>> {
    private final PhotoRepository photoRepository;

    public GetPhotos(UseCaseExecutor useCaseExecutor, PhotoRepository photoRepository) {
        super(useCaseExecutor);
        this.photoRepository = photoRepository;
    }

    @Override
    public ObservableTask<List<Photo>> createObservableTask(Void input) {
        return photoRepository.getPhotos();
    }
}
