package ru.andreysozonov.imagebrowser.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface DetailView extends MvpView {

    @StateStrategyType(value = AddToEndStrategy.class)
    void loadImage(String url);
}
