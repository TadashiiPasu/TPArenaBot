package com.github.tadashiipasu.tparenabot.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.pojos.*;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class WandererHandler {

    private final String wandererFilePath = "./wanderers";
    private final ViewerHandler viewerHandler = new ViewerHandler();
    private final MoveHandler moveHandler = new MoveHandler();

    public String wandererRegistration(ChannelMessageEvent event, String userId) throws IOException {
        String viewerFilePath = "./viewers";
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        if (viewerFile.createNewFile()) {
            viewerHandler.createViewer(event.getUser().getName(), userId);
            return createWanderer(event.getUser().getName(), userId);
        } else {
            if (wandererIsRegistered(userId)) {
                return "You have already been registered. Get to dueling!";
            } else {
                return createWanderer(event.getUser().getName(), userId);
            }
        }
    }

    public String createWanderer(String username, String userId) throws IOException {
        StatBlock viewerStats = viewerHandler.getViewer(userId).getStats();
        Integer maxHealth = statMod(viewerStats.getMaxHealth());
        StatBlock stats = new StatBlockBuilder()
                .setMaxHealth(maxHealth)
                .setCurrentHealth(maxHealth)
                .setStrength(statMod(viewerStats.getStrength()))
                .setMagic(statMod(viewerStats.getMagic()))
                .setDefense(statMod(viewerStats.getDefense()))
                .setMagicDefense(statMod(viewerStats.getMagicDefense()))
                .setAccuracy(statMod(viewerStats.getAccuracy()))
                .setEvasion(statMod(viewerStats.getEvasion()))
                .setSpeed(statMod(viewerStats.getSpeed()))
                .build();
        String modifier = "2/2";
        ModBlock modifiers = new ModBlockBuilder()
                .setStrength(modifier)
                .setMagic(modifier)
                .setDefense(modifier)
                .setMagicDefense(modifier)
                .setAccuracy(modifier)
                .setEvasion(modifier)
                .setSpeed(modifier)
                .build();
        List<Integer> moveList = moveHandler.selectMove(getWanderer(userId), 4);
        Wanderer wanderer = new WandererBuilder()
                .setUsername(username)
                .setUserId(userId)
                .setLevel(1)
                .setExperience(0)
                .setOriginalStats(stats)
                .setAdjustedStats(stats)
                .setModifiers(modifiers)
                .setMoves(moveList)
                .setStatus(null)
                .setEffect(null)
                .setChallenger(false)
                .setChallenged(false)
                .setDueling(false)
                .setOpponentId("")
                .build();

        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        String message;
        if (wandererFile.createNewFile()) {
            mapper.writeValue(wandererFile, wanderer);
            message = "You have been successfully registered!";
        } else {
            message = "An error has occurred during registration";
        }
        return message;
    }

    public boolean wandererIsRegistered(String userId) {
        return new File(String.format(wandererFilePath + "/%s.json", userId)).exists();
    }

    public Wanderer getWanderer(String userId) {
        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(wandererFile, Wanderer.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateWanderer(Wanderer wanderer) {
        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", wanderer.getUserId()));
        if (wanderer.getOriginalStats().getCurrentHealth() < 1) {
            wandererFile.delete();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(wandererFile, wanderer);
            } catch (Exception ignored) {
            }
        }
    }

    public String sendMoveList(String userId) {
        Wanderer wanderer = getWanderer(userId);
        StringBuilder message = new StringBuilder(String.format("%s knows: ", wanderer.getUsername()));
        for (int i = 0; i < wanderer.getMoves().size(); i++) {
            message.append(String.format("%s", moveHandler.getMove(wanderer.getMoves().get(i)).getMoveName()));
            if (i != wanderer.getMoves().size() - 1) {
                message.append(", ");
            }
        }
        return message.toString();
    }

    public String sendMoveInfo(String userId, Integer moveIndex) {
        Wanderer wanderer = getWanderer(userId);
        Move move = moveHandler.getMove(wanderer.getMoves().get(moveIndex - 1));
        return String.format("Info on %s - Type: %s, Power:%d, Accuracy:%d Crit Chance:%d",
                move.getMoveName(),
                move.getType(),
                move.getDamage(),
                move.getAccuracy(),
                move.getCritChance()) + "%";
    }

    public String sendStats(String userId) {
        Wanderer wanderer = getWanderer(userId);
        StatBlock stats = wanderer.getAdjustedStats();
        return String.format("%s's stats - Level:%d, XP:%d, HP:%d, STR:%d, MAG:%d, DEF:%d, MDF:%d, EVA:%d, ACC:%d, SPD:%d",
                wanderer.getUsername(),
                wanderer.getLevel(),
                wanderer.getExperience(),
                stats.getCurrentHealth(),
                stats.getStrength(),
                stats.getMagic(),
                stats.getDefense(),
                stats.getMagicDefense(),
                stats.getAccuracy(),
                stats.getEvasion(),
                stats.getSpeed());
    }

    public void resetStats(Wanderer wanderer) {
        StatBlock originalStats = wanderer.getOriginalStats();
        StatBlock adjustedStats = wanderer.getAdjustedStats();
        adjustedStats.setStrength(originalStats.getStrength());
        adjustedStats.setMagic(originalStats.getMagic());
        adjustedStats.setDefense(originalStats.getDefense());
        adjustedStats.setMagicDefense(originalStats.getMagicDefense());
        adjustedStats.setAccuracy(originalStats.getAccuracy());
        adjustedStats.setEvasion(originalStats.getEvasion());
        adjustedStats.setSpeed(originalStats.getSpeed());
    }

    private Integer statMod(Integer stat) {
        Random rand = new Random();
        if (rand.nextInt()%2 == 0) {
            return stat + rand.nextInt(stat/2);
        } else {
            return stat - rand.nextInt(stat/2);
        }
    }
}
