package omilia.utils;

import Telegram.Bot;
import com.google.gson.Gson;
import omilia.action.OmAction;
import omilia.response.OmResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class OmiliaDialogHandler {

    private HttpsURLConnection connection;
    private String dialogId;

    public OmResponse execute(Bot bot, OmAction action) {
        connection = new OmConnection(bot,dialogId).get();
        try {
            setRequestBody(action);
        } catch (IOException e) {
            System.out.println(e);
        }
        return parseResponse();
    }

    private void setRequestBody(OmAction action) throws IOException {
            OutputStream os = connection.getOutputStream();
            byte[] input = action.asJSONString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

    }

    private OmResponse parseResponse() {
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            return null;
        }

        OmResponse omResponse = new Gson().fromJson(response.toString(), OmResponse.class);

        if(omResponse!=null) {
            this.dialogId = omResponse.getDialogId();
            connection.disconnect();
        }
        return omResponse;
    }
}
