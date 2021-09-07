package omilia.response.parts.semantic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Entity {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("instances")
    @Expose
    private List<Instance> instances;

    @SerializedName("sensitivity-level")
    @Expose
    private String sensitivityLevel;
}
