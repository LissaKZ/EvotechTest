package omilia.response.parts.semantic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import omilia.response.parts.common.Intent;

import java.util.List;

@Data
public class SemanticInterpretation {

    @SerializedName("confidence")
    @Expose
    private Float confidence;

    @SerializedName("n_best")
    @Expose
    private List<NBest> nBest;

    @SerializedName("intents")
    @Expose
    private List<Intent> intents;

    @SerializedName("number_of_results")
    @Expose
    private Integer numberOfResults;

    @SerializedName("result_type")
    @Expose
    private String resultType;

    @SerializedName("nlu_app")
    @Expose
    private String nluApp;

}
