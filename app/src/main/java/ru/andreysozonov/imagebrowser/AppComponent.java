package ru.andreysozonov.imagebrowser;


import javax.inject.Singleton;

import dagger.Component;
import ru.andreysozonov.imagebrowser.presenter.DetailPresenter;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(DetailPresenter detailPresenter);
}
