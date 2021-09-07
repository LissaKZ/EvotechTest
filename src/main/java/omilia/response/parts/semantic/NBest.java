package omilia.response.parts.semantic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NBest {

    @SerializedName("utterance")
    @Expose
    private String utterance;

    @SerializedName("entities")
    @Expose
    private List<Entity> entities;

    @SerializedName("confidence")
    @Expose
    private Float confidence;

    @SerializedName("index")
    @Expose
    private Integer index;

}
