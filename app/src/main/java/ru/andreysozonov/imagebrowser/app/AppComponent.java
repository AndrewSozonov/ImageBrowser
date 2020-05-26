package ru.andreysozonov.imagebrowser.app;


import javax.inject.Singleton;

import dagger.Component;
import ru.andreysozonov.imagebrowser.presenter.DetailPresenter;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;


@Singleton
@Component(modules = {ru.andreysozonov.imagebrowser.app.AppModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(DetailPresenter detailPresenter);
}
