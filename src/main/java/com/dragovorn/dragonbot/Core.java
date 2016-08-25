package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.bot.DragonBot;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:43 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Core {

    public static void main(String[] args) throws Exception {
        new DragonBot();

//        try {
//            HttpClient client = HttpClientBuilder.create().build();
//
//            HttpGet request = new HttpGet("https://api.github.com/repos/dragovorn/dragon-bot-twitch/releases");
//            request.addHeader("content-type", "application/json");
//
//            HttpResponse response = client.execute(request);
//
//            JSONArray array = new JSONArray(EntityUtils.toString(response.getEntity(), "UTF-8"));
//
//            for (Object object : array) {
//                System.out.println(object.toString());
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
    }
}