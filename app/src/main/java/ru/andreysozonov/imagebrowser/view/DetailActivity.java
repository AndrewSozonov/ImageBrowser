package ru.andreysozonov.imagebrowser.view;


import moxy.MvpAppCompatActivity;
import android.os.Bundle;
import moxy.MvpView;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.presenter.DetailPresenter;


public class DetailActivity extends MvpAppCompatActivity implements MvpView {

    private static final String POSITION_KEY = "positionKey";

    @InjectPresenter
    DetailPresenter detailPresenter;

    @ProvidePresenter
    DetailPresenter provideDetailPresenter() {
        return new DetailPresenter(getIntent().getIntExtra(POSITION_KEY, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
