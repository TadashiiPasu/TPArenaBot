package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.user.PrivateMessageEvent;

public class ResponseOnWhisper {
    private final FightHandler fightHandler = new FightHandler();
    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ResponseOnWhisper(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(PrivateMessageEvent.class, this::onWhisper);
    }

    /**
     * Subscribe to the Whisper Event
     */
    public void onWhisper(PrivateMessageEvent event) {
        String message;
        String privateMessage;
        String target;

        if (event.getMessage().startsWith("~")) {
            String[] commandStrip = event.getMessage().split(" ");
            int moveIndex = Integer.parseInt(commandStrip[0].substring(1));
            if (moveIndex > 0 && moveIndex < 5) {
                sendPublic(fightHandler.updateFight(event.getUser().getId(), moveIndex));
            }
        }
    }

    public void sendPublic(String message) {
        Bot bot = new Bot();
        if (message != null) {
            bot.getTwitchClient().getChat().sendMessage("tadashiipasu", message);
        }
    }

    public void sendPrivate(Bot bot, String message, String target) {
        bot.getTwitchClient().getChat().sendPrivateMessage("tadashiipasu", message);
    }
}
