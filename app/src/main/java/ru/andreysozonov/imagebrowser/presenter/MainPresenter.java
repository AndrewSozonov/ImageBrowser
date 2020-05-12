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
import ru.andreysozonov.imagebrowser.App;
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

    private int increaseCount(int count) {
        return count + 1;
    }

    public void onItemClicked(int position) {
        int count = model.getCount();
        int newCount = increaseCount(count);
        model.setCount(newCount);
        model.setCurrentPosition(position);
        Log.d(TAG, "MainPresenter: clicked " + newCount + " times");
        getViewState().startDetailActivity();
    }

    public void getAllPhoto() {
        Observable<Photo> single = apiHelper.requestServer();

        Disposable disposable = single.observeOn(AndroidSchedulers.mainThread()).subscribe(photos -> {
            hitList = photos.hits;
            getViewState().updateRecyclerView();
            putImagesIntoDatabase(hitList);

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
                        getAllPhoto();
                    }
                }, throwable -> {
                    Log.d(TAG, "putData: " + throwable);
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
