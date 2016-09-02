/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE..
 */

import com.dragovorn.dragonbot.Keys;
import com.dragovorn.dragonbot.api.twitch.TwitchAPI;
import org.junit.Test;

import static org.junit.Assert.fail;

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