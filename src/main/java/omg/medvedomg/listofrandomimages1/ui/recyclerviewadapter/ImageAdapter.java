package omg.medvedomg.listofrandomimages1.ui.recyclerviewadapter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import omg.medvedomg.listofrandomimages1.R;
import omg.medvedomg.listofrandomimages1.data.model.Image;
import omg.medvedomg.listofrandomimages1.services.DownloadService;

/**
 * Created by medvedomg on 26.11.16.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements ItemTouchHelperAdapter {

    ArrayList<Image> images;

    Context context;

    private final String TAG = "Adapter TAG";

    private final int NOTIFICATION_ID = 1234;

    int progressThread = 0;
    private Notification.Builder builder;
    private int maxProgress;
    private NotificationManager manager;

    public ImageAdapter(Context context, ArrayList imageList) {
        images = imageList;
        this.context = context;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(images, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(images, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        images.remove(position);
        notifyItemRemoved(position);
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
        openAlertDialog(holder, position);

        holder.textView.setText(images.get(position).getId()+"4");

    }

    private void openAlertDialog(final ImageViewHolder holder, final int position) {
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Do you wanna download picture?")
                        .setTitle("Download?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(TAG, "onClick in AlertDialog");
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        1);
                                Intent intent = new Intent(context, DownloadService.class);
                                intent.putExtra("url", images.get(position).getUrl().toString());
                                updateProgress();
                                intent.putExtra("reciever", new DownloadProgressReceiver(new Handler()));
                                context.startService(intent);
//                                if (ContextCompat.checkSelfPermission(context,
//                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    Log.d(TAG, "check permission " + (ContextCompat.checkSelfPermission(context,
//                                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                            != PackageManager.PERMISSION_GRANTED));
//                                    // Should we show an explanation?
//
//                                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
//                                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
////                                       ActivityCompat.requestPermissions(context,);
//                                    } else {
//
//                                        // No explanation needed, we can request the permission.
//
////                                        ActivityCompat.requestPermissions(thisActivity,
////                                                new String[]{Manifest.permission.READ_CONTACTS},
////                                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                                        // app-defined int constant. The callback method gets the
//                                        // result of the request.
//                                    }
//                                }


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    private void updateProgress() {
        maxProgress = 100;

        builder = new Notification.Builder(context)
                .setContentTitle("Downloading image")
                .setSmallIcon(R.mipmap.ic_launcher);
        manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(NOTIFICATION_ID, builder.build());

    }

    private class DownloadProgressReceiver extends ResultReceiver{


        private int fullProgress;

        public DownloadProgressReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.FULL_PROGRESS) {
                fullProgress = resultData.getInt("fullprogress");
                Log.d(TAG, "fullProgress is " + fullProgress);

                builder.setProgress(100, 0, false);
            }
            else if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
//                int fullProress = resultData.getInt("fullprogress");
                Log.d("ResultReciever TAG", "progress from service is " + progress);
                builder.setProgress(99,progress,false);
                manager.notify(NOTIFICATION_ID, builder.build());

            }
        }
    }
}
