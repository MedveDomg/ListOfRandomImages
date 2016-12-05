package omg.medvedomg.listofrandomimages1.rest;

import android.graphics.Movie;

import omg.medvedomg.listofrandomimages1.model.Image;
import omg.medvedomg.listofrandomimages1.model.ImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by medvedomg on 11.11.16.
 */

public interface ApiInterface {

    @GET("api/v1/images/latest")
    Call<ImageResponse> getImageList();

    @GET("api/v1/images/random")
    Call<Image> getImage();
}
