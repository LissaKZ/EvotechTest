package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Reaction {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("prompt")
    @Expose
    private String prompt;

}
