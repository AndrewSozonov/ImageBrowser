package ru.andreysozonov.imagebrowser.view;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.presenter.DetailPresenter;


public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    @BindView(R.id.detail_imageView)
    ImageView detailImageView;

    @InjectPresenter
    DetailPresenter detailPresenter;

    @ProvidePresenter
    DetailPresenter provideDetailPresenter() {
        return new DetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void loadImage(String url) {
        Picasso.get()
                .load(url)
                .into(detailImageView);
    }

    @OnClick(R.id.buttonPrevious)
    void onButtonPreviousClick() {
        detailPresenter.buttonPrevious();
    }

    @OnClick(R.id.buttonNext)
    void onButtonNextClick() {
        detailPresenter.buttonNext();
    }
}
