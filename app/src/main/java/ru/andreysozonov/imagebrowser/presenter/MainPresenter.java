package ru.andreysozonov.imagebrowser.presenter;


import android.util.Log;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
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
    private Model model;
    private ApiHelper apiHelper;
    private List<Hit> hitList;

    public MainPresenter() {

        model = new Model();
        apiHelper = new ApiHelper();
        recyclerMain = new RecyclerMain();
    }

    @Override
    protected void onFirstViewAttach() {
        getAllPhoto();
    }

    private int increaseCount(int count) {
        return count + 1;
    }

    public void onItemClicked(int position) {
        int count = model.getCount();
        int newCount = increaseCount(count);
        model.setCount(newCount);
        Log.d(TAG, "MainPresenter: clicked " + newCount + " times");
        getViewState().startDetailActivity(position, hitList.get(position).largeImageURL);
    }

    public void getAllPhoto() {
        Observable<Photo> single = apiHelper.requestServer();

        Disposable disposable = single.observeOn(AndroidSchedulers.mainThread()).subscribe(photos -> {
            hitList = photos.hits;
            getViewState().updateRecyclerView();

        }, throwable -> {
            Log.e(TAG, "onError " + throwable);
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
