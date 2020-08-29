package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.handlers.FightHandler;
import com.github.tadashiipasu.tparenabot.handlers.ViewerHandler;
import com.github.tadashiipasu.tparenabot.handlers.WandererHandler;
import com.github.twitch4j.common.events.user.PrivateMessageEvent;

public class ResponseOnWhisper {
    private final FightHandler fightHandler = new FightHandler();
    private final WandererHandler wandererHandler = new WandererHandler();
    private final ViewerHandler viewerHandler = new ViewerHandler();
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
        if (event.getMessage().startsWith("~")) {
            String[] commandStrip = event.getMessage().split(" ");
            if (commandStrip[0].substring(1).equals("levelup")) {
                if (commandStrip.length == 2) {
                    sendPublic(wandererHandler.levelup(event.getUser().getId(), commandStrip[1]));
                }
            } else if (commandStrip[0].substring(1).equals("exchange")) {
                if (commandStrip.length == 2) {
                    sendPublic(wandererHandler.exchangeMove(event.getUser().getId(), Integer.parseInt(commandStrip[1])));
                }
            } else if (commandStrip[0].substring(1).equals("sp")) {
                if (commandStrip.length == 2) {
                    sendPublic(viewerHandler.permanentLevelup(event.getUser().getId(), commandStrip[1]));
                }
            } else {
                int moveIndex = Integer.parseInt(commandStrip[0].substring(1));
                if (moveIndex > 0 && moveIndex < 5) {
                    sendPublic(fightHandler.updateFight(event.getUser().getId(), moveIndex));
                }
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
