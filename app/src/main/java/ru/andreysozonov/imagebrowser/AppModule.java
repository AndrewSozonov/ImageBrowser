package ru.andreysozonov.imagebrowser;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andreysozonov.imagebrowser.database.HitDao;
import ru.andreysozonov.imagebrowser.model.Model;
import ru.andreysozonov.imagebrowser.model.entity.Hit;
import ru.andreysozonov.imagebrowser.model.retrofit.ApiHelper;


@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Model provideModel() {
        return new Model();
    }

    @Singleton
    @Provides
    ApiHelper provideApiHelper() {
        return new ApiHelper();
    }

    @Singleton
    @Provides
    HitDao provideHitDao() {
        return App.getAppDatabase().hitDao();
    }
}
