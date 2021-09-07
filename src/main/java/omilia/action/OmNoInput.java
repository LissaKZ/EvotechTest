package omilia.action;

public class OmNoInput extends OmDefaultAction {
    public OmNoInput() {
        super();
        requestBody.put("utterance", "[noinput]");
    }
    public OmNoInput(String name) {
        super(name);
        requestBody.put("utterance", "[noinput]");
    }
}
