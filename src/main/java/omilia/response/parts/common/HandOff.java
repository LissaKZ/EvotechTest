package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HandOff {

    @SerializedName("reason ")
    @Expose
    private String reason;

    @SerializedName("target")
    @Expose
    private String target;
}
