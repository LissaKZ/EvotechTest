package omilia.action;

import org.json.JSONObject;

public class OmStartDialog extends OmDefaultAction {
    public OmStartDialog() {
        super();
        requestBody.put("source", "telegram");
    }
    public OmStartDialog(String name) {
        super(name);
        requestBody.put("source", "chat");
    }
    public OmStartDialog(String name,String source) {
        super(name);
        JSONObject semantics=new JSONObject();
        semantics.put("channel",source);
        semantics.put("chatBotChannel",source);
        JSONObject context=new JSONObject();
        context.put("semantics",semantics);
        requestBody.put("source", "chat");
        requestBody.put("context",context);
    }
}
