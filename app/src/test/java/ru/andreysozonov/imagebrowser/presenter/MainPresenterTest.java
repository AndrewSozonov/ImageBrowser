package ru.andreysozonov.imagebrowser.presenter;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import ru.andreysozonov.imagebrowser.app.App;
import ru.andreysozonov.imagebrowser.app.TestComponent;
import ru.andreysozonov.imagebrowser.app.TestModule;
import ru.andreysozonov.imagebrowser.app.DaggerTestComponent;
import ru.andreysozonov.imagebrowser.database.HitDao;
import ru.andreysozonov.imagebrowser.model.entity.Hit;
import ru.andreysozonov.imagebrowser.model.entity.Photo;
import ru.andreysozonov.imagebrowser.model.retrofit.ApiHelper;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    TestComponent component;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);
        component = DaggerTestComponent.builder()
                .testModule(new TestModule() {
                    @Override
                    public ApiHelper provideApiHelper() {
                        ApiHelper apiHelper = super.provideApiHelper();
                        Photo photo = new Photo();
                        photo.hits = new ArrayList<Hit>(20);
                        Mockito.when(apiHelper.requestServer("nature")).thenReturn(Observable.just(photo));
                        return apiHelper;
                    }
                    @Override
                    public HitDao provideHitDao() {
                        HitDao hitDao = super.provideHitDao();
                        return hitDao;
                    }
                }).build();
        App.setComponent(component);
        presenter = Mockito.spy(new MainPresenter());
    }

    @Test
    public void getAllPhoto_isCorrect() {

        component.inject(presenter);
        presenter.getAllPhoto("nature");
    }

    @Test
    public void putImagesIntoDatabase_isCorrect() {
        component.inject(presenter);
        List<Hit> list = new ArrayList<>(20);
        presenter.putImagesIntoDatabase(list);
    }
}

