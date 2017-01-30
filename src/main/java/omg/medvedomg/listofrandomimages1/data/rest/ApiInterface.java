package omg.medvedomg.listofrandomimages1.data.rest;

import io.reactivex.Observable;
import omg.medvedomg.listofrandomimages1.data.model.Image;
import omg.medvedomg.listofrandomimages1.data.model.ImageResponse;
import retrofit2.http.GET;

/**
 * Created by medvedomg on 11.11.16.
 */

public interface ApiInterface {

    @GET("api/v1/images/latest")
    Observable<ImageResponse> getImageList();

    @GET("api/v1/images/random")
    Observable<Image> getImage();
}
