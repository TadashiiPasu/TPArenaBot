package com.github.tadashiipasu.tparenabot.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.StatBlockBuilder;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;
import com.github.tadashiipasu.tparenabot.pojos.WandererBuilder;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WandererHandler {

    private final String wandererFilePath = "./wanderers";
    private final ViewerHandler viewerHandler = new ViewerHandler();
    private final MoveHandler moveHandler = new MoveHandler();

    public String createWanderer(String username, String userId) throws IOException {
        StatBlock viewerStats = viewerHandler.getViewer(userId).getStats();
        StatBlock stats = new StatBlockBuilder()
                .setMaxHealth(viewerStats.getMaxHealth())
                .setCurrentHealth(viewerStats.getCurrentHealth())
                .setStrength(viewerStats.getStrength())
                .setMagic(viewerStats.getMagic())
                .setDefense(viewerStats.getDefense())
                .setMagicDefense(viewerStats.getMagicDefense())
                .setAccuracy(viewerStats.getAccuracy())
                .setEvasion(viewerStats.getEvasion())
                .setSpeed(viewerStats.getSpeed())
                .build();
        List<Integer> moveList = moveHandler.selectMove(getWanderer(userId), 4);
        Wanderer wanderer = new WandererBuilder()
                .setUsername(username)
                .setUserId(userId)
                .setLevel(1)
                .setExperience(0)
                .setStats(stats)
                .setMoves(moveList)
                .setStatus(null)
                .setEffect(null)
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
        return String.format("Info %s - Type: %s, Power:%d, Accuracy:%d",
                move.getMoveName(),
                move.getType(),
                move.getDamage(),
                move.getAccuracy());
    }

    public String sendStats(String userId) {
        Wanderer wanderer = getWanderer(userId);
        StatBlock stats = wanderer.getStats();
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
}
