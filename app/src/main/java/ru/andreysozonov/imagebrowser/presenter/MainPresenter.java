package ru.andreysozonov.imagebrowser.presenter;


import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.andreysozonov.imagebrowser.app.App;
import ru.andreysozonov.imagebrowser.database.HitDao;
import ru.andreysozonov.imagebrowser.model.Model;
import ru.andreysozonov.imagebrowser.model.entity.Hit;
import ru.andreysozonov.imagebrowser.model.entity.Photo;
import ru.andreysozonov.imagebrowser.model.retrofit.ApiHelper;
import ru.andreysozonov.imagebrowser.view.IViewHolder;
import ru.andreysozonov.imagebrowser.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";
    private RecyclerMain recyclerMain;

    @Inject
    Model model;

    @Inject
    ApiHelper apiHelper;

    @Inject
    HitDao hitDao;

    private List<Hit> hitList;

    public MainPresenter() {
        App.getAppComponent().inject(this);
        recyclerMain = new RecyclerMain();
    }

    @Override
    protected void onFirstViewAttach() {
        getImagesFromDatabase();
    }

    public void onItemClicked(int position) {

        model.setCurrentPosition(position);
        getViewState().startDetailActivity();
    }

    public void onSearchSubmit(String message) {
        if (message.equalsIgnoreCase(model.getTheme())) {
            getMorePhoto();
        } else {
            model.setTheme(message);
            model.setPage(1);
            deleteImagesFromDatabase();
        }
    }

    public void getAllPhoto(String theme, int page) {

        Observable<Photo> single = apiHelper.requestServer(theme, page);

        Disposable disposable = single.observeOn(AndroidSchedulers.mainThread()).subscribe(photos -> {

            if (hitList != null) {
                hitList.clear();
                getViewState().updateRecyclerView();
            }

            hitList = photos.hits;
            Log.d(TAG, "HITLIST SIZE " + hitList.size());
            for (int i = 0; i < hitList.size(); i++) {
                hitList.get(i).id = i + 1;
            }
            getViewState().updateRecyclerView();
            putImagesIntoDatabase(hitList);

        }, throwable -> {
            Log.e(TAG, "onError " + throwable);
        });
    }

    public void getMorePhoto() {

        String theme = model.getTheme();
        int page = model.getPage();
        page++;
        model.setPage(page);
        int indexOLastElement = hitList.size();

        Observable<Photo> single = apiHelper.requestServer(theme, page);

        Disposable disposable = single.observeOn(AndroidSchedulers.mainThread()).subscribe(photos -> {
            hitList.addAll(photos.hits);

            for (int i = 0; i < photos.hits.size(); i++) {
                photos.hits.get(i).id = indexOLastElement + i + 1;
            }

            Log.d(TAG, "PAGE " + model.getPage());
            int indexEnd = hitList.size() - 1;
            getViewState().updateRecyclerViewByIndex(indexOLastElement, indexEnd);
            Log.d(TAG, "START ELEMENT " + indexOLastElement);
            Log.d(TAG, "END ELEMENT " + indexEnd);

            putImagesIntoDatabase(photos.hits);

        }, throwable -> {
            Log.e(TAG, "onError " + throwable);
        });

    }

    public void putImagesIntoDatabase(List<Hit> list) {

        Disposable disposable = hitDao.insertList(list).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Log.d(TAG, "putData: " + id);
                }, throwable -> {
                    Log.d(TAG, "putData: " + throwable);
                });
    }

    public void getImagesFromDatabase() {

        Disposable disposable = hitDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(hits -> {
                    if (hits.size() != 0) {
                        Log.d(TAG, "images from database " + hits.size());
                        hitList = hits;
                        getViewState().updateRecyclerView();
                    } else {
                        getAllPhoto(model.getTheme(), 1);
                    }
                }, throwable -> {
                    Log.d(TAG, "putData: " + throwable);
                });
    }

    public void deleteImagesFromDatabase() {
        Disposable disposable = hitDao.deleteAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleted -> {

                    getAllPhoto(model.getTheme(), model.getPage());
                    Log.d(TAG, "deleted images from database " + deleted);


                }, throwable -> {
                    Log.d(TAG, "deletedData: " + throwable);
                });

    }

    private class RecyclerMain implements I2RecyclerMain {

        @Override
        public void bindView(IViewHolder iViewHolder) {
            iViewHolder.setImage(hitList.get(iViewHolder.getPos()).webformatURL);
        }

        @Override
        public int getItemCount() {
            if (hitList != null) {
                return hitList.size();
            }
            return 0;
        }
    }

    public RecyclerMain getRecyclerMain() {
        return recyclerMain;
    }
}
