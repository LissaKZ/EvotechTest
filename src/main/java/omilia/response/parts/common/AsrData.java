package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AsrData {

    @SerializedName("rec_start")
    @Expose
    private Integer recStart;

    @SerializedName("speech_start")
    @Expose
    private Integer speechStart;

    @SerializedName("speech_duration")
    @Expose
    private Integer speechDuration;

    @SerializedName("prompt_timeout")
    @Expose
    private Integer promptTimeout;

}
