package omilia.response.parts.action.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import omilia.response.parts.action.data_model.DataModel;

import java.util.List;

@Data
public class Message {

    @SerializedName("prompts")
    @Expose
    private List<Prompt> prompts = null;

    @SerializedName("data_model")
    @Expose
    private DataModel dataModel;

}
