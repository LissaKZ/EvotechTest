package omilia.action;

public class OmStartDialog extends OmDefaultAction {
    public OmStartDialog() {
        super();
        requestBody.put("source", "telegram");
    }
    public OmStartDialog(String name) {
        super(name);
        requestBody.put("source", "chat");
    }
}
