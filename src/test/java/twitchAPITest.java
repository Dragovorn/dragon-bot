import com.dragovorn.dragonbot.Keys;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 11:47 AM.
 * as of 8/29/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class twitchAPITest {

    /*
     * This method tests connectivity to the twitch api
     */
    @Test
    public void testTwitchAPI() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet("https://api.twitch.tv/kraken/channels/dragovorn");
        request.addHeader("content-type", "application/json");
        request.addHeader("Client-ID", Keys.twitchClientID);
        request.addHeader("Accept", "application/vnd.twitchtv.v3+json");

        HttpResponse response = client.execute(request);

        System.out.println(new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8")).getString("display_name"));
    }
}