package ru.andreysozonov.imagebrowser.view;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.andreysozonov.imagebrowser.presenter.MainPresenter;

public class RecyclerScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    private int picturesVisible = 6;
    private int picturesOnRequest = 20;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    GridLayoutManager layoutManager;
    private MainPresenter mainPresenter;

    public RecyclerScrollListener(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {

            layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount == (previousTotal + picturesOnRequest)) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if ((!loading) && (totalItemCount - firstVisibleItem == picturesVisible)) {
                loading = true;
                mainPresenter.getMorePhoto();
            }
        }
    }
}



