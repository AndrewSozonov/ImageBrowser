package ru.andreysozonov.imagebrowser.app;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.andreysozonov.imagebrowser.database.HitDao;
import ru.andreysozonov.imagebrowser.model.Model;
import ru.andreysozonov.imagebrowser.model.retrofit.ApiHelper;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;

@Module
public class TestModule {

    @Provides
    public ApiHelper provideApiHelper(){
        return Mockito.mock(ApiHelper.class);
    }

    @Provides
    public Model provideModel(){
        return Mockito.mock(Model.class);
    }

    @Provides
    public HitDao provideHitDao(){
        return Mockito.mock(HitDao.class);
    }

    @Provides
    public MainPresenter provideMainPresenter(){
        return Mockito.mock(MainPresenter.class);
    }
}
