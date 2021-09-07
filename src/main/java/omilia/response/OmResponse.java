package omilia.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import omilia.response.parts.action.Action;
import omilia.response.parts.common.*;
import omilia.response.parts.semantic.SemanticInterpretation;

import java.util.List;

@Data
public class OmResponse {

    @SerializedName("dialogId")
    @Expose
    private String dialogId;

    @SerializedName("dialog_metadata")
    @Expose
    private DialogMetadata dialogMetadata;

    @SerializedName("step")
    @Expose
    private Integer step;

    @SerializedName("semantic_interpretation")
    @Expose
    private SemanticInterpretation semanticInterpretation;

    @SerializedName("fields")
    @Expose
    private List<Field> fields = null;

    @SerializedName("action")
    @Expose
    private Action action;

    @SerializedName("trackers")
    @Expose
    private List<String> trackers;

    @SerializedName("grammars")
    @Expose
    private Grammars grammars;

    @SerializedName("handoff_data")
    @Expose
    private HandOff handOff;

    @SerializedName("prompt")
    @Expose
    private String prompt;

    @SerializedName("prompt_duration")
    @Expose
    private Integer promptDuration;

    @SerializedName("intent")
    @Expose
    private Intent intent;

    @SerializedName("target")
    @Expose
    private Target target;

    @SerializedName("sensitivity")
    @Expose
    private String sensitivity;

    @SerializedName("step_timestamp")
    @Expose
    private Long stepTimestamp;

    @SerializedName("asr_data")
    @Expose
    private AsrData asrData;

    @SerializedName("locale")
    @Expose
    private String locale;

    @SerializedName("input_modality")
    @Expose
    private String inputModality;

    @SerializedName("has_encrypted_data")
    @Expose
    private Boolean hasEncryptedData;

    @SerializedName("encrypted")
    @Expose
    private Integer encrypted;

    @SerializedName("reaction")
    @Expose
    private Reaction reaction;

    @SerializedName("dialog_step_event")
    @Expose
    private List<DialogEvent> dialogStepEvents;

}
