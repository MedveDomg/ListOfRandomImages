package omg.medvedomg.listofrandomimages1.ui;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import omg.medvedomg.listofrandomimages1.R;
import omg.medvedomg.listofrandomimages1.model.Image;
import omg.medvedomg.listofrandomimages1.model.ImageResponse;
import omg.medvedomg.listofrandomimages1.recyclerviewadapter.ImageAdapter;
import omg.medvedomg.listofrandomimages1.recyclerviewadapter.SimpleItemTouchHelperCallback;
import omg.medvedomg.listofrandomimages1.rest.ApiClient;
import omg.medvedomg.listofrandomimages1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TAG";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private Image uniqueImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<Image> call = apiService.getImage();
                call.enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(Call<Image> call, Response<Image> response) {
                        Log.d("TAG", "in onResponse ");
                        int statusCode = response.code();
                        Log.d("TAG", "response.code()" + response.code());

                        uniqueImage = response.body().getImage();
                        String urlImage = uniqueImage.getUrl();

                        Log.d(TAG, "unique image downloaded" + urlImage);

                        Bundle bundle = new Bundle();
                        bundle.putString("url_image", urlImage);
                        FragmentManager manager = getSupportFragmentManager();
                        DialogRandomImageFragment fragment = new DialogRandomImageFragment();
                        fragment.setArguments(bundle);
                        fragment.show(manager, "DIALOG_FRAGMENT");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {
                        Log.d("TAG", "image dowload fail");
                    }
                });
            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        downloadImages(apiService, recyclerView);

    }

    private void downloadImages(ApiInterface apiService, final RecyclerView recyclerView) {
        Call<ImageResponse> call = apiService.getImageList();
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Log.d("TAG", "in onResponse ");
                int statusCode = response.code();
                Log.d("TAG", "response.code()" + response.code());

                ArrayList<Image> images = response.body().getImageList();

                Log.d(TAG, "images downloaded" + images.get(3).getId());
                ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
                ItemTouchHelper.Callback callback =
                        new SimpleItemTouchHelperCallback(adapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(recyclerView);
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("TAG", "image dowload fail");
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView

        }
    };

}
