package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

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
        String message = "Hello";

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
