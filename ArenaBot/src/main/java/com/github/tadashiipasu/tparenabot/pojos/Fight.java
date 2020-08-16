package com.github.tadashiipasu.tparenabot.pojos;

public class Fight {
    public String fightId;
    public Wanderer wandererOne;
    public Wanderer wandererTwo;
    public boolean wandererOneReady;
    public boolean wandererTwoReady;

    public String getFightId() {
        return fightId;
    }

    public void setFightId(String fightId) {
        this.fightId = fightId;
    }

    public Wanderer getWandererOne() {
        return wandererOne;
    }

    public void setWandererOne(Wanderer wandererOne) {
        this.wandererOne = wandererOne;
    }

    public Wanderer getWandererTwo() {
        return wandererTwo;
    }

    public void setWandererTwo(Wanderer wandererTwo) {
        this.wandererTwo = wandererTwo;
    }

    public boolean isWandererOneReady() {
        return wandererOneReady;
    }

    public void setWandererOneReady(boolean wandererOneReady) {
        this.wandererOneReady = wandererOneReady;
    }

    public boolean isWandererTwoReady() {
        return wandererTwoReady;
    }

    public void setWandererTwoReady(boolean wandererTwoReady) {
        this.wandererTwoReady = wandererTwoReady;
    }
}
