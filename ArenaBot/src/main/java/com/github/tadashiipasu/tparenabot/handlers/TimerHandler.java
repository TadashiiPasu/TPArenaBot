package com.github.tadashiipasu.tparenabot.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.pojos.Fight;
import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;

import java.io.File;
import java.io.IOException;

public class TimerHandler {
    public void runChecks (long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long elapsedMinutes = elapsedSeconds / 60;
        checkExperienceGain(elapsedSeconds, elapsedMinutes);
        checkHealthRegen(elapsedSeconds);
        checkChallenges();
    }

    public void checkExperienceGain(long elapsedSeconds, long elapsedMinutes) {
        if (elapsedMinutes % 5 == 0 && elapsedSeconds % 60 == 0) {
            ObjectMapper mapper = new ObjectMapper();
            Wanderer wanderer;
            Integer levelMultiplier;
            for (File wandererFile : new File("./wanderers").listFiles()) {
                try {
                    wanderer = mapper.readValue(wandererFile, Wanderer.class);
                    levelMultiplier = (wanderer.getLevel() / 3) + 1;
                    wanderer.setExperience(wanderer.getExperience()+(10*levelMultiplier));
                    mapper.writeValue(wandererFile, wanderer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sleep(1);
        }
    }

    public void checkHealthRegen(long elapsedSeconds) {
        if (elapsedSeconds % 60 == 0) {
            ObjectMapper mapper = new ObjectMapper();
            Wanderer wanderer;
            StatBlock adjustedStats;
            int healthRegen;
            for (File wandererFile : new File("./wanderers").listFiles()) {
                try {
                    wanderer = mapper.readValue(wandererFile, Wanderer.class);
                    if (!wanderer.getDueling() && wanderer.getAdjustedStats().getCurrentHealth() < wanderer.getAdjustedStats().getMaxHealth()) {
                        adjustedStats = wanderer.getAdjustedStats();
                        healthRegen = Math.round((float)adjustedStats.getMaxHealth() / 12);
                        if (healthRegen > 1) {
                            adjustedStats.setCurrentHealth(adjustedStats.getCurrentHealth() + healthRegen);
                        } else {
                            adjustedStats.setCurrentHealth(adjustedStats.getCurrentHealth() + 1);
                        }
                        wanderer.setAdjustedStats(adjustedStats);
                    }
                    mapper.writeValue(wandererFile, wanderer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sleep(1);
        }
    }

    public void checkChallenges() {
        ObjectMapper mapper = new ObjectMapper();
        Fight fight;
        WandererHandler wandererHandler = new WandererHandler();
        FightHandler fightHandler = new FightHandler();
        for (File fightFile : new File("./fights").listFiles()) {
            try {
                fight = mapper.readValue(fightFile, Fight.class);
                long challengedTimer = (System.currentTimeMillis() - fight.fightStart) / 1000;
                if (challengedTimer > 60 && !wandererHandler.getWanderer(fight.getWandererTwoId()).getDueling()) {
                    fightHandler.fightCancel(fight.getWandererTwoId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sleep(1);
    }

    private void sleep(Integer seconds) {
        int millis = seconds * 1000;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
