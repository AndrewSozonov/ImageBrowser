package ru.andreysozonov.imagebrowser.app;

import javax.inject.Singleton;

import dagger.Component;

import ru.andreysozonov.imagebrowser.presenter.MainPresenter;
import ru.andreysozonov.imagebrowser.presenter.MainPresenterTest;

@Singleton
@Component(modules = {TestModule.class})
public interface TestComponent extends AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(MainPresenterTest mainPresenterTest);

}
