package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.handlers.FightHandler;
import com.github.tadashiipasu.tparenabot.handlers.ViewerHandler;
import com.github.tadashiipasu.tparenabot.handlers.WandererHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.io.IOException;

public class ResponseOnMessage {

    private final String viewerFilePath = "./viewers";
    private final WandererHandler wandererHandler = new WandererHandler();
    private final ViewerHandler viewerHandler = new ViewerHandler();
    private final FightHandler fightHandler = new FightHandler();
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
        if (event.getMessage().startsWith("~")) {
            String[] commandStrip = event.getMessage().split(" ");
            switch(commandStrip[0].toLowerCase()) {
                case "~register":
                    try {
                        sendPublic(event, wandererHandler.wandererRegistration(event, getUserId(event.getUser().getName())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "~stats":
                    if (commandStrip.length < 2) {
                        sendPublic(event, wandererHandler.sendStats(event.getUser().getId()));
                    } else {
                        sendPublic(event, wandererHandler.sendStats(getUserId(commandStrip[1])));
                    }
                    break;
                case "~moves":
                    sendPublic(event, wandererHandler.sendMoveList(event.getUser().getId()));
                    break;
                case "~moveinfo":
                    sendPublic(event, wandererHandler.sendMoveInfo(event.getUser().getId(),
                            Integer.parseInt(commandStrip[1])));
                    break;
                case "~duel":
                    sendPublic(event, fightHandler.fightInvite(event.getUser().getId(), commandStrip));
                    break;
                case "~accept":
                    sendPublic(event, fightHandler.fightStartup(event.getUser().getId()));
                    break;
                case "~reject":
                    sendPublic(event, fightHandler.fightCancel(event.getUser().getId()));
                    break;
                case "~sp":
                    sendPublic(event, viewerHandler.getStreamPoints(event.getUser().getId()));
                    break;
                case "~checkout":
                    sendPublic(event, viewerHandler.checkout(event.getUser().getName()));
                    break;
                case "~xp":
                    if (commandStrip.length == 3) {
                        sendPublic(event, wandererHandler.giveExperience(event.getUser().getName(), getUserId(convertForTwitch(commandStrip[1])), commandStrip[2]));
                    } else {
                        sendPublic(event, "Invalid arguments");
                    }
                    break;
                case "~help":
                    sendPublic(event, "To participate in Viewer vs Viewer battle, you must first register using the \"~register\" command. " +
                            "Afterwards you can then challenge other viewers who have registered by using the \"~duel\" command!");
                    break;
                case "~commands":
                    sendPublic(event, "~register, ~stats, ~moves, ~moveinfo <moveNumber>, ~duel <target>, ~sp");
                    break;
                case "~test":
                    sendPrivate(event, "PRIVMSG #jtv :.w tadashiipasu HeyGuys", "tadashiipasu");
                    break;
            }
        }
    }

    public void sendPublic(ChannelMessageEvent event, String message) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

    public void sendPrivate(ChannelMessageEvent event, String message, String target) {
        event.getTwitchChat().sendPrivateMessage("tadashiipasu", message);
    }

    private void test(String userId) throws IOException {

    }

    //To send consecutive message during one event
    private void sleep(Integer seconds) {
        int millis = seconds * 1000;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getUserId(String username) {
        Bot bot = new Bot();
        return bot.getUserId(username);
    }

    private String convertForTwitch(String rawUser) {
        String target;
        if (rawUser.startsWith("@")) {
            target = rawUser.substring(1).toLowerCase();
        } else {
            target = rawUser.toLowerCase();
        }
        return target;
    }
}