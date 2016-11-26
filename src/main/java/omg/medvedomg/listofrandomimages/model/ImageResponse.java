package omg.medvedomg.listofrandomimages.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.http.GET;

/**
 * Created by medvedomg on 26.11.16.
 */

public class ImageResponse {

    @SerializedName("images")
    private ArrayList<Image> ImageList;

    public ArrayList<Image> getImageList() {
        return ImageList;
    }
}
