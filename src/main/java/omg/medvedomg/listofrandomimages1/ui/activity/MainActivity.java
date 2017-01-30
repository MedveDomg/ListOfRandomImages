package omg.medvedomg.listofrandomimages1.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import omg.medvedomg.listofrandomimages1.R;
import omg.medvedomg.listofrandomimages1.data.model.Image;
import omg.medvedomg.listofrandomimages1.data.model.ImageResponse;
import omg.medvedomg.listofrandomimages1.recyclerviewadapter.ImageAdapter;
import omg.medvedomg.listofrandomimages1.rest.ApiClient;
import omg.medvedomg.listofrandomimages1.rest.ApiInterface;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{


    ApiClient apiClient;
    SwipeRefreshLayout mSwipeRefreshLayout;
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

    ProgressBar bar;
    private String TAG = "TAG";
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private Image uniqueImage;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Call<Image> call = apiService.getImage();
//                call.enqueue(new Callback<Image>() {
//                    @Override
//                    public void onResponse(Call<Image> call, Response<Image> response) {
//                        Log.d("TAG", "in onResponse ");
//                        int statusCode = response.code();
//                        Log.d("TAG", "response.code()" + response.code());
//
//                        uniqueImage = response.body().getImage();
//                        String urlImage = uniqueImage.getUrl();
//
//                        Log.d(TAG, "unique image downloaded" + urlImage);
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("url_image", urlImage);
//                        FragmentManager manager = getSupportFragmentManager();
//                        DialogRandomImageFragment fragment = new DialogRandomImageFragment();
//                        fragment.setArguments(bundle);
//                        fragment.show(manager, "DIALOG_FRAGMENT");
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onFailure(Call<Image> call, Throwable t) {
//                        Log.d("TAG", "image dowload fail");
//                    }
//                });
            }
        });

        bar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        downloadImages(apiService, recyclerView);

    }

    private void downloadImages(ApiInterface apiService, final RecyclerView recyclerView) {
        Log.d(TAG, "downloadImages()");
        Retrofit retrofit = ApiClient.getClient();
        retrofit.create(ApiInterface.class).getImageList()
                .map(this::sleepDear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupRecyclerView);
//        retrofit.create(ApiInterface.class).getImageList()
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ImageResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ImageResponse value) {
//                        Log.d(TAG, "value.getImageList().get(0).getSourceId();" + value.getImageList().get(0).getSourceId());
//                        value.getImageList().get(0).getSourceId();
//                        ImageAdapter adapter = new ImageAdapter(MainActivity.this, value.getImageList());
//                        recyclerView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

//        Call<ImageResponse> call = apiService.getImageList();
//        call.enqueue(new Callback<ImageResponse>() {
//            @Override
//            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
//                Log.d("TAG", "in onResponse ");
//                int statusCode = response.code();
//                Log.d("TAG", "response.code()" + response.code());
//
//                ArrayList<Image> images = response.body().getImageList();
//
//                Log.d(TAG, "images downloaded" + images.get(3).getId());
//                ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
//                recyclerView.setAdapter(adapter);
//                mSwipeRefreshLayout.setRefreshing(false);
//                ItemTouchHelper.Callback callback =
//                        new SimpleItemTouchHelperCallback(adapter);
//                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                touchHelper.attachToRecyclerView(recyclerView);
//            }
//
//            @Override
//            public void onFailure(Call<ImageResponse> call, Throwable t) {
//                Log.d("TAG", "image dowload fail");
//            }
//        });

    public ImageResponse sleepDear(ImageResponse respose) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "SLEEP");
            TimeUnit.SECONDS.sleep(1);

        }
        return respose;
    }
    public void setupRecyclerView(ImageResponse response) {
        bar.setVisibility(View.GONE);
        Log.d(TAG, "response size " + response.getImageList().size());
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, response.getImageList());
                        recyclerView.setAdapter(adapter);
    }
    }
