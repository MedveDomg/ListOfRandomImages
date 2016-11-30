package omg.medvedomg.listofrandomimages.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import omg.medvedomg.listofrandomimages.R;
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

        ImageView imageView;
        TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.idImage);
        }
    }
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_item, parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageViewHolder holder, int position) {
        Picasso.with(context)
                .load(images.get(position).getUrl().toString())
                .fit()
                .into(holder.imageView);
        holder.textView.setText(images.get(position).getId()+"4");

    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
