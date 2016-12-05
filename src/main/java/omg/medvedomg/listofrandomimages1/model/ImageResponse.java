package omg.medvedomg.listofrandomimages1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
