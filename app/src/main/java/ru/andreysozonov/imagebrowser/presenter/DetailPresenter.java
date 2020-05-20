package ru.andreysozonov.imagebrowser.presenter;

import android.util.Log;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.andreysozonov.imagebrowser.app.App;
import ru.andreysozonov.imagebrowser.database.HitDao;
import ru.andreysozonov.imagebrowser.model.Model;
import ru.andreysozonov.imagebrowser.view.DetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static final String TAG = "DetailPresenter";
    private int currentPosition;
    private String largeImageUrl;

    @Inject
    Model model;

    @Inject
    HitDao hitDao;

    public DetailPresenter() {

        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        currentPosition = model.getCurrentPosition();
        getImageFromDatabase();
        Log.d(TAG, "position " + currentPosition);
        getViewState().loadImage(largeImageUrl);
    }

    public void getImageFromDatabase() {

        Disposable disposable = hitDao.getImageById(currentPosition + 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(hit -> {
                    if (hit != null) {
                        largeImageUrl = hit.largeImageURL;
                        getViewState().loadImage(largeImageUrl);
                    }
                }, throwable -> {
                    Log.d(TAG, "putData: " + throwable);
                });
    }

    public void buttonPrevious(){
        if (currentPosition > 0) {
            currentPosition--;
            getImageFromDatabase();
        }
    }

    public void buttonNext() {
        if (currentPosition < 19) {
            currentPosition++;
            getImageFromDatabase();
        }
    }
}
