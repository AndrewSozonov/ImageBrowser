package ru.andreysozonov.imagebrowser.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {

    @StateStrategyType(value = AddToEndStrategy.class)
    void startDetailActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void updateRecyclerView();
}
