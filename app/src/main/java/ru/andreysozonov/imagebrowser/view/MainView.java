package ru.andreysozonov.imagebrowser.view;

import android.os.Bundle;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {

    void onResume(Bundle savedInstanceState);

    @StateStrategyType(value = AddToEndStrategy.class)
    void startDetailActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void updateRecyclerView();
}
