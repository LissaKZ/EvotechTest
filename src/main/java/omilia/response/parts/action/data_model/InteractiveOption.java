package omilia.response.parts.action.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InteractiveOption {
    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("semantics")
    @Expose
    private Semantics semantics;
}
