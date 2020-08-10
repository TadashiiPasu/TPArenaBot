package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.jetbrains.annotations.NotNull;

public class ResponseOnMessage {
    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ResponseOnMessage(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onMessage);
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onMessage(ChannelMessageEvent event) {
        String message;
        String target;
        if (event.getMessage().startsWith("~")) {
            String[] commmandStrip = event.getMessage().split(" ");
            switch(commmandStrip[0].toLowerCase()) {
                case "~register":
                    message = "You have been successfully registered!";
                    sendPublic(event, message);
                    break;
                case "~duel":
                    if (commmandStrip.length < 2) {
                        sendPublic(event, "To duel someone, you need to add their name! i.e: ~duel TadashiiPasu");
                        break;
                    }

                    target = convertForTwitch(commmandStrip[1]);
                    if (userIsPresent(target)) {
                        message = String.format("%s, %s has challenged you to a duel! Do you accept? (~accept/~reject)", target, event.getUser().getName());
                    } else {
                        message = String.format("%s is not currently here BibleThump", target);
                    }
                    sendPublic(event, message);
                    break;
                case "~accept":
                    sendPublic(event, "A match between two fearsome Viewers is about to take place! PogChamp");
                    break;
                case "~reject":
                    sendPublic(event, "The challenge has been refused. Perhaps some other Viewer is up to the challenge? KappaHD");
                    break;
                case "~help":
                    sendPublic(event, "To participate in Viewer vs Viewer battle, you must first register using the \"~register\" command. " +
                            "Afterwards you can then challenge other viewers who have registered by using the \"~duel\" command!");
                    break;
            }
        }
    }

    @NotNull
    private String convertForTwitch(String rawUser) {
        String target;
        if (rawUser.startsWith("@")) {
            target = rawUser.substring(1).toLowerCase();
        } else {
            target = rawUser.toLowerCase();
        }
        return target;
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendPublic(ChannelMessageEvent event, String message) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

    private boolean userIsPresent(String username) {
        Bot bot = new Bot();
        if (username.startsWith("@")) {
            return bot.userIsPresent(username.substring(1));
        }
        return bot.userIsPresent(username);
    }
}
