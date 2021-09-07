package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DialogMetadata {

    @SerializedName("telegram")
    @Expose
    private String app;

    @SerializedName("channel")
    @Expose
    private String channel;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("app_version")
    @Expose
    private Integer appVersion;

    @SerializedName("diamant_instance")
    @Expose
    private String diamantInstance;

    @SerializedName("diamant_version")
    @Expose
    private String diamantVersion;

    @SerializedName("dialog_timestamp")
    @Expose
    private Long dialogTimestamp;

    @SerializedName("hashed_origin_uri")
    @Expose
    private String hashedOriginUri;

    @SerializedName("destination_uri")
    @Expose
    private String destinationUri;

    @SerializedName("device_type")
    @Expose
    private String deviceType;

    @SerializedName("no_inputs")
    @Expose
    private Integer noInputs;

    @SerializedName("no_matches")
    @Expose
    private Integer noMatches;

    @SerializedName("connection_id")
    @Expose
    private String connectionId;

    @SerializedName("origin_uri")
    @Expose
    private String originUri;

}
