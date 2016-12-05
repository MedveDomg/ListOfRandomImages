package omg.medvedomg.listofrandomimages1.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by medvedomg on 26.11.16.
 */

public class Image {

    @SerializedName("id")
    int id;

    @SerializedName("url")
    String Url;

    @SerializedName("large_url")
    String largeUrl;

    @SerializedName("source_id")
    int sourceId;

    private Image(ImageBuilder builder) {
        this.id = builder.id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getLargeUrl() {

        return largeUrl;
    }

    public String getUrl() {

        return Url;
    }

    public int getId() {

        return id;
    }

    public Image getImage() {
        return this;
    }

    public static class ImageBuilder{

        @SerializedName("id")
        int id;

        @SerializedName("url")
        String Url;

        @SerializedName("large_url")
        String largeUrl;

        @SerializedName("source_id")
        int sourceId;

        public ImageBuilder(int id) {
            this.id = id;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }

        public void setLargeUrl(String largeUrl) {

            this.largeUrl = largeUrl;
        }

        public void setUrl(String url) {

            Url = url;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
