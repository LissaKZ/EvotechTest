package omilia.response.parts.semantic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class Instance {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("begin")
    @Expose
    private Integer begin;

    @SerializedName("end")
    @Expose
    private Integer end;

    @SerializedName("confidence")
    @Expose
    private Double confidence;

    @SerializedName("features")
    @Expose
    private Map<String, String> features;

    @SerializedName("covered_text")
    @Expose
    private String coveredText;

}
