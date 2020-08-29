package com.github.tadashiipasu.tparenabot.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;
import com.github.tadashiipasu.tparenabot.pojos.builders.StatBlockBuilder;
import com.github.tadashiipasu.tparenabot.pojos.Viewer;
import com.github.tadashiipasu.tparenabot.pojos.builders.ViewerBuilder;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ViewerHandler {

    private final String viewerFilePath = "./viewers";

    public void createViewer(String username, String userId) throws IOException {
        StatBlock stats = new StatBlockBuilder()
                .setMaxHealth(10)
                .setCurrentHealth(10)
                .setStrength(10)
                .setMagic(10)
                .setDefense(10)
                .setMagicDefense(10)
                .setAccuracy(10)
                .setEvasion(10)
                .setSpeed(10)
                .build();
        Viewer viewer = new ViewerBuilder()
                .setUsername(username)
                .setUserId(userId)
                .setStreamPoints(0)
                .setCheckedIn(false)
                .setStats(stats)
                .build();

        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        if (viewerFile.exists()) {
            mapper.writeValue(viewerFile, viewer);
        }
    }

    public String permanentLevelup(String userId, String stats) {
        Viewer viewer = getViewer(userId);
        StatBlock viewerStats = viewer.getStats();
        Random rand = new Random();
        List<String> statsList = Arrays.asList(stats.split(","));
        if ((viewer.getStreamPoints() >= stats.length()) || viewer.getStreamPoints() == 1) {
            for (String stat : statsList) {
                switch (stat.toUpperCase()) {
                    case "HP":
                        viewerStats.setMaxHealth(viewerStats.getMaxHealth() +
                                rand.nextInt(Math.round(viewerStats.getMaxHealth()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "STR":
                        viewerStats.setStrength(viewerStats.getStrength() +
                                rand.nextInt(Math.round(viewerStats.getStrength()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "MAG":
                        viewerStats.setMagic(viewerStats.getMagic() +
                                rand.nextInt(Math.round(viewerStats.getMagic()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "DEF":
                        viewerStats.setDefense(viewerStats.getDefense() +
                                rand.nextInt(Math.round(viewerStats.getDefense()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "MDEF":
                        viewerStats.setMagicDefense(viewerStats.getMagicDefense() +
                                rand.nextInt(Math.round(viewerStats.getMagicDefense()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "ACC":
                        viewerStats.setAccuracy(viewerStats.getAccuracy() +
                                rand.nextInt(Math.round(viewerStats.getAccuracy()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "EVA":
                        viewerStats.setEvasion(viewerStats.getEvasion() +
                                rand.nextInt(Math.round(viewerStats.getEvasion()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                    case "SPD":
                        viewerStats.setSpeed(viewerStats.getSpeed() +
                                rand.nextInt(Math.round(viewerStats.getSpeed()/(float)2)) + 1);
                        viewer.setStreamPoints(viewer.getStreamPoints() - 1);
                        break;
                }
            }
            updateViewer(viewer);
            return String.format("%s has permanently upgraded their stats! PogChamp",
                    viewer.getUsername());
        } else {
            return String.format("Uhhh...%s, you don't have enough Stream Points for the permanent upgrade. " +
                            "You earn Stream Points once per stream. Be sure to come back next stream to get more! HeyGuys ",
                    viewer.getUsername());
        }
    }

    public Viewer getViewer(String userId) {
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(viewerFile, Viewer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateViewer(Viewer viewer) {
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", viewer.getUserId()));
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(viewerFile, viewer);
        } catch (Exception ignored) {
        }
    }

    public String getStreamPoints(String userId) {
        Viewer viewer = getViewer(userId);
        return String.format("%s has %dSP! To spend them, use the \"~sp\" command in a whisper! i.e.: ~sp mag",
                viewer.getUsername(),
                viewer.getStreamPoints());
    }

    public String checkout(String username) {
        Bot bot = new Bot();
        if (!bot.userIsPresent(username)) {
            ObjectMapper mapper = new ObjectMapper();
            Viewer viewer;
            for (File viewerFile : new File("./viewers").listFiles()) {
                try {
                    viewer = mapper.readValue(viewerFile, Viewer.class);
                    viewer.setCheckedIn(false);
                    mapper.writeValue(viewerFile, viewer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Task complete!";
        }
        return "You do not have permission to use this command.";
    }
}
