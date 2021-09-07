package omilia.action;

public class OmEndDialog extends OmDefaultAction {
    public OmEndDialog() {
        super();
        requestBody.put("utterance", "[hup]");
    }
    public OmEndDialog(String name) {
        super(name);
        requestBody.put("utterance", "[hup]");
    }
}
