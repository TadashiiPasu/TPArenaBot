package com.github.tadashiipasu.tparenabot;

import com.github.tadashiipasu.tparenabot.handlers.TimerHandler;

public class Main {

    public static void main(String[] args) {
        TimerHandler timerHandler = new TimerHandler();
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();
        long startTime = System.currentTimeMillis();
        while(true) {
            timerHandler.runChecks(startTime);
        }
    }
}