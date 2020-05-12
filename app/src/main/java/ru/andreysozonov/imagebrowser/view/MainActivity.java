package ru.andreysozonov.imagebrowser.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.adapter.RecyclerAdapter;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    private RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    public MainPresenter providePresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this, mainPresenter.getRecyclerMain());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.SetOnItemClickListener((view, position) -> mainPresenter.onItemClicked(position));
    }

    public void startDetailActivity() {
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecyclerView() {
        Log.d(TAG, "updateRecyclerView: ");
        recyclerAdapter.notifyDataSetChanged();
    }
}
