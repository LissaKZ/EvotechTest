package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DialogEvent {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("actionLog")
    @Expose
    private Boolean actionLog;

    @SerializedName("exitReason")
    @Expose
    private String exitReason;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("logName")
    @Expose
    private String logName;

    @SerializedName("index")
    @Expose
    private Integer index;

    @SerializedName("event")
    @Expose
    private String event;

    @SerializedName("task")
    @Expose
    private String task;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("active-intent")
    @Expose
    private String activeIntent;

    @SerializedName("task-id")
    @Expose
    private String taskId;
}
