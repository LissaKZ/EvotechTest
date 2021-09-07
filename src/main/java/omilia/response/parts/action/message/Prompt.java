package omilia.response.parts.action.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Prompt {

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("voice")
    @Expose
    private Boolean voice;

    @SerializedName("visualize")
    @Expose
    private Boolean visualize;

    @SerializedName("prompt_urls")
    @Expose
    private List<String> promptUrls = null;

}
