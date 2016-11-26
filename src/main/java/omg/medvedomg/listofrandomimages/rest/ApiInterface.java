package omg.medvedomg.listofrandomimages.rest;

import omg.medvedomg.listofrandomimages.model.ImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by medvedomg on 11.11.16.
 */

public interface ApiInterface {

    @GET("api/v1/images/latest")
    Call<ImageResponse> getImageList();
}
