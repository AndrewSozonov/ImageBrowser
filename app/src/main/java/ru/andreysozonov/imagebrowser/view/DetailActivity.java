package ru.andreysozonov.imagebrowser.view;


import butterknife.BindView;
import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.presenter.DetailPresenter;


public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    private static final String POSITION_KEY = "positionKey";
    private static final String URL_KEY = "urlKey";

    @BindView(R.id.detail_imageView)
    ImageView detailImageView;

    @InjectPresenter
    DetailPresenter detailPresenter;

    @ProvidePresenter
    DetailPresenter provideDetailPresenter() {
        return new DetailPresenter(getIntent().getIntExtra(POSITION_KEY, 0), getIntent().getStringExtra(URL_KEY));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void loadImage(String url) {
        Glide
                .with(getApplicationContext())
                .load(url)
                .into(detailImageView);

    }
}
