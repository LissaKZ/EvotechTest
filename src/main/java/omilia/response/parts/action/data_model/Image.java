package omilia.response.parts.action.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Image {
    @SerializedName("url")
    @Expose
    private String url;
}
