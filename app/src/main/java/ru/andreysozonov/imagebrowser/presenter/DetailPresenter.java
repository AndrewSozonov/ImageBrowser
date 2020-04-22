package ru.andreysozonov.imagebrowser.presenter;

import android.util.Log;
import moxy.MvpPresenter;
import moxy.MvpView;

public class DetailPresenter extends MvpPresenter<MvpView> {

    private static final String TAG = "DetailPresenter";
    int position;

    public DetailPresenter(int position) {
        this.position = position;

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "position " + position);
    }
}
