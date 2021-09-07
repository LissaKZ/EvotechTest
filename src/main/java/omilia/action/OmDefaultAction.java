package omilia.action;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OmDefaultAction implements OmAction {
    protected Map<String, Object> requestBody;

    protected OmDefaultAction() {
        requestBody = new HashMap<>();
        requestBody.put("application_id", null);
    }
    protected OmDefaultAction(String name) {
        requestBody = new HashMap<>();
        requestBody.put("application_id", name);
    }


    public String asJSONString() {
        JSONObject body = new JSONObject();
        for (Map.Entry<String, Object> a : requestBody.entrySet())
            body.put(a.getKey(),a.getValue());
        return body.toString();
    }
}
