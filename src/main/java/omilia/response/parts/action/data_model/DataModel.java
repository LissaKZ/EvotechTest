package omilia.response.parts.action.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DataModel {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("template_name")
    @Expose
    private String templateName;

    @SerializedName("interactive_option")
    @Expose
    private List<InteractiveOption> interactiveOptions = null;

}
