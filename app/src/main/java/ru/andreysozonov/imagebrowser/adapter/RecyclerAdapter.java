package ru.andreysozonov.imagebrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.andreysozonov.imagebrowser.R;
import ru.andreysozonov.imagebrowser.presenter.I2RecyclerMain;
import ru.andreysozonov.imagebrowser.view.IViewHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {


    private static final String TAG = "RecyclerAdapter";

    private Context context;
    private OnItemClickListener itemClickListener;
    I2RecyclerMain i2RecyclerMain;

    public RecyclerAdapter(Context context, I2RecyclerMain i2RecyclerMain) {
        this.context = context;
        this.i2RecyclerMain = i2RecyclerMain;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.position = position;
        i2RecyclerMain.bindView(holder);
    }


    @Override
    public int getItemCount() {
        return i2RecyclerMain.getItemCount();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

        @BindView(R.id.recycler_image_view)
        ImageView imageView;

        private int position = 0;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        @Override
        public int getPos() {
            return position;
        }

        @Override
        public void setImage(String url) {
            Glide
                    .with(context)
                    .load(url)
                    .into(imageView);

        }
    }
}
