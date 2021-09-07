package omilia.response.parts.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import omilia.response.parts.action.message.FieldToElicit;
import omilia.response.parts.action.message.Message;
import omilia.response.parts.common.Field;

import java.util.List;

@Data
public class Action {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("subtype")
    @Expose
    private String subtype;

    @SerializedName("action_log_event")
    @Expose
    private String actionLogEvent;

    @SerializedName("fields_to_elicit")
    @Expose
    private List<FieldToElicit> fieldsToElicit = null;

    @SerializedName("fields_to_confirm")
    @Expose
    private List<Field> fieldsToConfirm = null;

    @SerializedName("message")
    @Expose
    private Message message;

}