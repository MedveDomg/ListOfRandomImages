package omg.medvedomg.listofrandomimages.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import omg.medvedomg.listofrandomimages.model.Image;

/**
 * Created by medvedomg on 26.11.16.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    ArrayList<Image> images;
    Context context;

    public ImageAdapter(Context context, ArrayList imageList) {
        images = imageList;
        this.context = context;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
