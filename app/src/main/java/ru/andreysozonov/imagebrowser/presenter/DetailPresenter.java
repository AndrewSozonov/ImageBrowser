package ru.andreysozonov.imagebrowser.presenter;

import android.util.Log;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.andreysozonov.imagebrowser.view.DetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static final String TAG = "DetailPresenter";
    private int position;
    private String url;

    public DetailPresenter(int position, String url) {
        this.position = position;
        this.url = url;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "position " + position);
        getViewState().loadImage(url);
    }
}
