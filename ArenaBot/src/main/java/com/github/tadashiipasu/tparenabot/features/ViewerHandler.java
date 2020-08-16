package com.github.tadashiipasu.tparenabot.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.StatBlockBuilder;
import com.github.tadashiipasu.tparenabot.pojos.Viewer;
import com.github.tadashiipasu.tparenabot.pojos.ViewerBuilder;

import java.io.File;
import java.io.IOException;

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
                .setStats(stats)
                .build();

        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        if (viewerFile.exists()) {
            mapper.writeValue(viewerFile, viewer);
        }
    }

    public Viewer getViewer(String userId) throws IOException {
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(viewerFile, Viewer.class);
    }
}
