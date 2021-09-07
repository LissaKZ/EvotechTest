package omilia.response.parts.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import omilia.response.parts.semantic.Instance;

import java.util.List;

@Data
public class Field {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("instances")
    @Expose
    private List<Instance> instances = null;

}
