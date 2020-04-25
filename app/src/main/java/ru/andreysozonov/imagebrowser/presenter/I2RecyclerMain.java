package ru.andreysozonov.imagebrowser.presenter;

import ru.andreysozonov.imagebrowser.view.IViewHolder;

public interface I2RecyclerMain {

    void bindView(IViewHolder iViewHolder);

    int getItemCount();
}
