package ru.andreysozonov.imagebrowser.view;

import android.os.Bundle;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {

    void onResume(Bundle savedInstanceState);

    @StateStrategyType(value = SkipStrategy.class)
    void startDetailActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void updateRecyclerView();

    @StateStrategyType(value = SkipStrategy.class)
    void updateRecyclerViewByIndex(int indexStart, int indexEnd);
}
