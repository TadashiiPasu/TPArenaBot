package com.github.tadashiipasu.tparenabot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ResponseOnMessage {

    private final String viewerFilePath = "./viewers";
    private final WandererHandler wandererHandler = new WandererHandler();
    private final ViewerHandler viewerHandler = new ViewerHandler();
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
                        wandererRegistration(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "~stats":
                    if (commandStrip.length < 2) {
                        sendPublic(event, wandererHandler.sendStats(getUserId(event.getUser().getName())));
                    } else {
                        sendPublic(event, wandererHandler.sendStats(getUserId(commandStrip[1])));
                    }
                    break;
                case "~moveslist":
                    sendPublic(event, wandererHandler.sendMoveList(getUserId(event.getUser().getName())));
                    break;
                case "~moveinfo":
                    sendPublic(event, wandererHandler.sendMoveInfo(getUserId(event.getUser().getName()),
                            Integer.parseInt(commandStrip[1])));
                    break;
                case "~duel":
                    try {
                        duelStartup(event, commandStrip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "~accept":
                    sendPublic(event, "A match between two fearsome Viewers is about to take place! PogChamp");
//                    fightStartup(event,);
                    break;
                case "~reject":
                    sendPublic(event, "The challenge has been refused. Perhaps some other Viewer is up to the challenge? KappaHD");
                    break;
                case "~help":
                    sendPublic(event, "To participate in Viewer vs Viewer battle, you must first register using the \"~register\" command. " +
                            "Afterwards you can then challenge other viewers who have registered by using the \"~duel\" command!");
                    break;
                case "~test":
                    try {
                        test(getUserId(event.getUser().getName()));
                        sendPublic(event, "the test has triggered");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void test(String userId) throws IOException {

    }

    private void wandererRegistration(ChannelMessageEvent event) throws IOException {
        String message = "";
        String userId = getUserId(event.getUser().getName());
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        if (viewerFile.createNewFile()) {
            viewerHandler.createViewer(event.getUser().getName(), userId);
            message = wandererHandler.createWanderer(event.getUser().getName(), userId);
        } else {
            if (wandererHandler.wandererIsRegistered(userId)) {
                message = "You have already been registered. Get to dueling!";
            } else {
                message = wandererHandler.createWanderer(event.getUser().getName(), userId);
            }
        }
        sendPublic(event, message);
    }

    private void duelStartup(ChannelMessageEvent event, String[] commandStrip) throws IOException {
        String target;
        String message;
        if (commandStrip.length < 2) {
            sendPublic(event, "To duel someone, you need to add their name! i.e: ~duel TadashiiPasu");
            return;
        }

        target = convertForTwitch(commandStrip[1]);
        if (userIsPresent(target)) {
            Wanderer wanderer = wandererHandler.getWanderer(getUserId(target));
            assert wanderer != null;
            wanderer.setChallenged(true);
            message = String.format("%s, %s has challenged you to a duel! Do you accept? (~accept/~reject)", target, event.getUser().getName());
        } else {
            message = String.format("%s is not currently here BibleThump", target);
        }
        sendPublic(event, message);
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

    //To send consecutive message during one event
    private void sleep(Integer seconds) {
        int millis = seconds * 1000;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendPublic(ChannelMessageEvent event, String message) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

    private boolean userIsPresent(String username) {
        Bot bot = new Bot();
        return bot.userIsPresent(username);
    }

    private String getUserId(String username) {
        Bot bot = new Bot();
        return bot.getUserId(username);
    }
}
