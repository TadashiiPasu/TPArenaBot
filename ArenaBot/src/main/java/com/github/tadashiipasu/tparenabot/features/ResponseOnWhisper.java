package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.user.PrivateMessageEvent;

public class ResponseOnWhisper {
    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ResponseOnWhisper(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(PrivateMessageEvent.class, this::onWhisper);
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onWhisper(PrivateMessageEvent event) {
        String message;
        String privateMessage;
        String target;
        Bot bot = new Bot();
        bot.getTwitchClient().getChat().sendMessage("TadashiiPasu", String.format("%s responded with %s", event.getUser().getName(), event.getMessage()));
//        if (event.getMessage().startsWith("~")) {
//            String[] commmandStrip = event.getMessage().split(" ");
//            switch(commmandStrip[0].toLowerCase()) {
//                case "~register":
//                    message = "You have been successfully registered!";
//                    sendPublic(event, message);
//                    break;
//                case "~duel":
//                    if (commmandStrip[1].startsWith("@")) {
//                        target = commmandStrip[1].substring(1).toLowerCase();
//                    } else {
//                        target = commmandStrip[1].toLowerCase();
//                    }
//
//                    if (userIsPresent(target)) {
//                        privateMessage = String.format("%s has challenged you to a duel! Do you accept? (Yes/No)", event.getUser().getName());
//                        sendPrivateChallenge(event, event.getUser().getName(), target, privateMessage);
//                        message = String.format("A challenge to %s has been sent!", target);
//                    } else {
//                        message = String.format("%s is not currently here BibleThump", target);
//                    }
//                    sendPublic(event, message);
//                    break;
//                case "~test":
//                    message = "/w TadashiiPasu This was sent via sendMessage";
//                    privateMessage = "This was sent via sendPrivateMessage";
//                    event.getTwitchChat().sendMessage("tadashiipasu", message);
//                    event.getTwitchChat().sendPrivateMessage("tadashiipasu", privateMessage);
//                    break;
//            }
//        }
    }
}
