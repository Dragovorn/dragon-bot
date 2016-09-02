import com.dragovorn.dragonbot.Keys;
import com.dragovorn.dragonbot.api.twitch.TwitchAPI;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 11:47 AM.
 * as of 8/29/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class twitchAPITest {

    private TwitchAPI api = new TwitchAPI(Keys.twitchClientID);

    /*
     * This method tests the twitch api channel connectivity
     */
    @Test
    public void testTwitchAPI() {
        try {
            System.out.println(api.getChannel("arteezy").toString(4));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    /*
     * This method tests the twitch api streams request
     */
    @Test
    public void testStreams() {
        try {
            System.out.println(api.getStream("arteezy").toString(4));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
}