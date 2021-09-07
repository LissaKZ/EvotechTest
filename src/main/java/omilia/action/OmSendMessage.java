package omilia.action;

public class OmSendMessage extends OmDefaultAction {
    public OmSendMessage(String utterance) {
        super();
        requestBody.put("utterance", utterance);
    }
    public OmSendMessage(String name,String utterance) {
        super(name);
        requestBody.put("utterance", utterance);
    }
}
