package com.app.payoneer.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.payoneer.ui.payments.PaymentsViewModel;
import com.app.payoneer.ui.payments.Repository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaymentsViewModel.class)) {
            return (T) new PaymentsViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }

}
