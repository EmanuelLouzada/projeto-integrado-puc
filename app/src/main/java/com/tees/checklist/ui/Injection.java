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

package com.tees.checklist.ui;

import android.content.Context;

import com.tees.checklist.api.WebAPI;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.repository.ConfiguracoesRepository;
import com.tees.checklist.service.AprService;
import com.tees.checklist.service.DataToAdapterService;
import com.tees.checklist.service.LoadService;
import com.tees.checklist.service.LoginService;
import com.tees.checklist.service.UnloadService;
import com.tees.checklist.ui.screens.cabecalhoChecklist.CabecalhoChecklistViewModelFactory;
import com.tees.checklist.ui.screens.checklist.ChecklistViewModelFactory;
import com.tees.checklist.ui.screens.checklistDiarioEPC.ChecklistDiarioEpcViewModelFactory;
import com.tees.checklist.ui.screens.checklistDiarioEPI.ChecklistDiarioEpiViewModelFactory;
import com.tees.checklist.ui.screens.home.HomeViewModelFactory;
import com.tees.checklist.ui.screens.homeAPR.HomeAPRViewModelFactory;
import com.tees.checklist.ui.screens.inspecaoVeicular.InspecaoDiariaVeiculoViewModelFactory;
import com.tees.checklist.ui.screens.load.LoadViewModelFactory;
import com.tees.checklist.ui.screens.login.LoginViewModelFactory;
import com.tees.checklist.ui.screens.settings.SettingsViewModelFactory;
import com.tees.checklist.ui.screens.unload.UnloadViewModelFactory;

/**
 * Enables injection of data sources.
 */
public class Injection {


    private static LoginService provideLoginDataSource(Context context) {
        AppDatabase.setInstance(context);
        Preferences preferences = new Preferences(context);
        return new LoginService(AppDatabase.getInstance(), preferences);
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Context context) {
        LoginService service = provideLoginDataSource(context);
        return new LoginViewModelFactory(service);
    }


    private static LoadService provideCargaDataSource(Context context) {
        AppDatabase.setInstance(context);
        Preferences preferences = new Preferences(context);
        WebAPI.unsetAPI();
        WebAPI.setAPI(Constants.CARGA, preferences.getUser(), preferences.getSettings());
        return new LoadService(AppDatabase.getInstance(), WebAPI.getApi(), preferences);
    }

    public static LoadViewModelFactory provideCargaViewModelFactory(Context context) {
        LoadService service = provideCargaDataSource(context);
        return new LoadViewModelFactory(service);
    }


    private static UnloadService provideDescargaDataSource(Context context) {
        AppDatabase.setInstance(context);
        Preferences preferences = new Preferences(context);
        WebAPI.unsetAPI();
        WebAPI.setAPI(Constants.CARGA, preferences.getUser(), preferences.getSettings());
        return new UnloadService(AppDatabase.getInstance(), WebAPI.getApi(), preferences);
    }

    public static UnloadViewModelFactory provideDescargaViewModelFactory(Context context) {
        UnloadService service = provideDescargaDataSource(context);
        return new UnloadViewModelFactory(service, context);
    }


    private static ConfiguracoesRepository provideSettingsDataSource(Context context) {
        return new ConfiguracoesRepository(AppDatabase.setInstance(context));
    }

    public static SettingsViewModelFactory provideSettingsViewModelFactory(Context context) {
        ConfiguracoesRepository configuracoesRepository = provideSettingsDataSource(context);
        Preferences preferences = new Preferences(context);
        return new SettingsViewModelFactory(configuracoesRepository, preferences);
    }

    public static HomeViewModelFactory provideHomeFaturamentoViewModelFactory(Context context) {
        return new HomeViewModelFactory();
    }

    public static ChecklistViewModelFactory provideChecklistViewModelFactory(Context mContext) {
        return new ChecklistViewModelFactory();
    }


    private static DataToAdapterService provideDataToAdapterDataSource(Context context) {
        AppDatabase.setInstance(context);
        Preferences preferences = new Preferences(context);
        WebAPI.unsetAPI();
        WebAPI.setAPI(Constants.CARGA, preferences.getUser(), preferences.getSettings());
        return new DataToAdapterService(AppDatabase.getInstance(), WebAPI.getApi(), preferences);
    }

    public static CabecalhoChecklistViewModelFactory provideCabecalhoChecklistViewModelFactory(Context context) {
        DataToAdapterService service = provideDataToAdapterDataSource(context);
        return new CabecalhoChecklistViewModelFactory(service);
    }

    public static InspecaoDiariaVeiculoViewModelFactory provideInspecaoDiariaVeiculoViewModelFactory(Context context) {
        DataToAdapterService service = provideDataToAdapterDataSource(context);
        return new InspecaoDiariaVeiculoViewModelFactory(service);

    }

    public static ChecklistDiarioEpiViewModelFactory provideChecklistDiarioEpiViewModelFactory(Context context) {
        DataToAdapterService service = provideDataToAdapterDataSource(context);
        return new ChecklistDiarioEpiViewModelFactory(service);

    }

    public static ChecklistDiarioEpcViewModelFactory provideChecklistDiarioEpcViewModelFactory(Context context) {
        DataToAdapterService service = provideDataToAdapterDataSource(context);
        return new ChecklistDiarioEpcViewModelFactory(service);
    }


    private static AprService provideAPRDataSource(Context context) {
        AppDatabase.setInstance(context);
        Preferences preferences = new Preferences(context);
        WebAPI.unsetAPI();
        WebAPI.setAPI(Constants.CARGA, preferences.getUser(), preferences.getSettings());
        return new AprService(AppDatabase.getInstance(), WebAPI.getApi(), preferences);
    }

    public static HomeAPRViewModelFactory provideHomeAPRViewModelFactory(Context context) {
        AprService service  = provideAPRDataSource(context);
        return new HomeAPRViewModelFactory(service);
    }
}


