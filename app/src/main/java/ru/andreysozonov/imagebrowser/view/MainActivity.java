package ru.andreysozonov.imagebrowser.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
    private Toolbar toolbar;
    private RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerScrollListener recyclerScrollListener;

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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    @Override
    public void onResume(Bundle savedInstanceState) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, query);
                searchView.clearFocus();
                mainPresenter.onSearchSubmit(query);
                searchView.setQuery("",false);
                search.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this, mainPresenter.getRecyclerMain());
        recyclerView.addItemDecoration(new GridItemDecoration(20));
        recyclerScrollListener = new RecyclerScrollListener(mainPresenter);
        recyclerView.addOnScrollListener(recyclerScrollListener);
        recyclerAdapter.SetOnItemClickListener((view, position) -> mainPresenter.onItemClicked(position));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void startDetailActivity() {
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecyclerView() {
        Log.d(TAG, "updateRecyclerView: ");
        recyclerScrollListener.setPreviousTotal(0);
        recyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateRecyclerViewByIndex(int startIndex, int endIndex) {
        Log.d(TAG, "updateRecyclerViewByIndex: ");
        recyclerAdapter.notifyItemRangeInserted(startIndex,endIndex);
    }
}
