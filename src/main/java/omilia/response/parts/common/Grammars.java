package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Grammars {

    @SerializedName("speech_grammar")
    @Expose
    private String speechGrammar;

    @SerializedName("dtmf_grammar")
    @Expose
    private String dtmfGrammar;

    @SerializedName("workflow_id")
    @Expose
    private String workflowId;
}
