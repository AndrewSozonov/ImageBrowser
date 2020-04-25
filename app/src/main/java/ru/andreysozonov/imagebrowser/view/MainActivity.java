package ru.andreysozonov.imagebrowser.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.adapter.RecyclerAdapter;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final String POSITION_KEY = "positionKey";
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {

        String[] itemData = new String[10];
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), itemData);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.SetOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                mainPresenter.onItemClicked(position);
            }
        });
    }

    public void startDetailActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        intent.putExtra(POSITION_KEY, position);
        startActivity(intent);
    }
}
