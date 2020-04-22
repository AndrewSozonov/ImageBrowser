package ru.andreysozonov.imagebrowser.presenter;


import android.util.Log;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.andreysozonov.imagebrowser.model.Model;
import ru.andreysozonov.imagebrowser.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";
    private Model model;

    public MainPresenter() {
        model = new Model();
    }

    private int increaseCount(int count) {
        return count + 1;
    }

    public void onItemClicked(int position) {
        int count = model.getCount();
        int newCount = increaseCount(count);
        model.setCount(newCount);
        Log.d(TAG, "MainPresenter: clicked " + newCount + " times");
        getViewState().startDetailActivity(position);
    }
}
