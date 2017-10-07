Dragon Bot
==========
The Dragon Bot is a [Twitch](https://twitch.tv/ "Twitch.tv") bot that allows other developers to make plugins and expand it to make it better,
this bot was created with the ideas that [Spigot](https://www.spigotmc.org/ "Spigot's Webpage") was created under. To allow the users to make their
own addons to it to make it fit their needs.

**This project uses Java 8 so please make sure you have it**

Plugins
-------
These are some plugins that I have made for the bot that serve as a nice way to introduce people to the API that comes with it.
  
- [Essentials](https://github.com/Dragovorn/essentials "Dragon Bot Essentials' Github") - A plugin aimed at adding essential functionality to the bot.

API
---
You can get access to the API by downloading the bot's jar file and including it in your build path
or you can run `mvn install` on the latest tag.

**I need to get around to putting the bot on my maven repo**

Compiling
---------
I use maven (as apparent by the presence of a `pom.xml`) so you can get 'nightly' builds
by cloning the repo and running `mvn package shade:shade`

Deprecation Policy
------------------
I like to clean deprecated things out as quickly as possible, sometimes something that is
not deprecated might get removed too because I replaced it with something I think to be more effective.

Feature Requesting
------------------
Please request a feature by either making a pull request with said feature or making an issue
requesting said feature.

Help me out
-----------
Help me continue working on this bot by donating so I can keep my workstation running
and maybe if I have enough money I can get a Jenkins build server running. [Donate here](https://twitch.streamlabs.com/Dragovorn)