/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tees.checklist.ui.screens.load;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tees.checklist.service.LoadService;

/**
 * Factory for ViewModels
 */
public class LoadViewModelFactory implements ViewModelProvider.Factory {

    private LoadService mService;
    public LoadViewModelFactory(LoadService service) {
        mService = service;
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoadViewModel.class)) {
            return (T) new LoadViewModel(mService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
