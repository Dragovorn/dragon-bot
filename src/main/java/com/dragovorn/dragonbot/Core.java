package com.dragovorn.dragonbot;

import com.dragovorn.dragonbot.api.github.GitHubAPI;

import java.util.Map;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 3:43 PM.
 * as of 8/5/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Core {

    public static void main(String[] args) throws Exception {
//        new DragonBot();

        GitHubAPI gitHubAPI = new GitHubAPI("dragovorn", "dragon-bot-twitch", false);

        Map<String, String> releases = gitHubAPI.getReleases();

        for (Map.Entry<String, String> entry : releases.entrySet()) {
            double newVersion = Double.valueOf(entry.getKey().substring(0, 4));
            double botVersion = Double.valueOf("v1.03a".substring(1, 5));

            char newPatch = entry.getKey().charAt(4);
            char botPatch = "v1.03a".charAt(5);

            if (newVersion > botVersion) {
                System.out.println("New version greater than bot version");
                return;
            } else if (newVersion == botVersion) {
                if (newPatch > botPatch) {
                    System.out.println("New patch greater than patch version " + String.valueOf(newPatch) + " (" + (int) newPatch + ")");
                    return;
                }
            } else {
                System.out.println("Current version older.");
            }
        }
    }
}