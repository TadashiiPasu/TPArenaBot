package com.github.tadashiipasu.tparenabot.pojos;

public class Fight {
    public String fightId;
    public String wandererOneId;
    public String wandererTwoId;
    public Integer wandererOneMove;
    public Integer wandererTwoMove;


    public Fight(String fightId, String wandererOneId, String wandererTwoId, Integer wandererOneMove,
                 Integer wandererTwoMove) {
        this.fightId = fightId;
        this.wandererOneId = wandererOneId;
        this.wandererTwoId = wandererTwoId;
        this.wandererOneMove = wandererOneMove;
        this.wandererTwoMove = wandererTwoMove;
    }

    public Fight() {
    }

    public String getFightId() {
        return fightId;
    }

    public void setFightId(String fightId) {
        this.fightId = fightId;
    }

    public String getWandererOne() {
        return wandererOneId;
    }

    public void setWandererOne(String wandererOne) {
        this.wandererOneId = wandererOneId;
    }

    public String getWandererTwo() {
        return wandererTwoId;
    }

    public void setWandererTwo(String wandererTwo) {
        this.wandererTwoId = wandererTwo;
    }

    public String getWandererOneId() {
        return wandererOneId;
    }

    public void setWandererOneId(String wandererOneId) {
        this.wandererOneId = wandererOneId;
    }

    public String getWandererTwoId() {
        return wandererTwoId;
    }

    public void setWandererTwoId(String wandererTwoId) {
        this.wandererTwoId = wandererTwoId;
    }

    public Integer getWandererOneMove() {
        return wandererOneMove;
    }

    public void setWandererOneMove(Integer wandererOneMove) {
        this.wandererOneMove = wandererOneMove;
    }

    public Integer getWandererTwoMove() {
        return wandererTwoMove;
    }

    public void setWandererTwoMove(Integer wandererTwoMove) {
        this.wandererTwoMove = wandererTwoMove;
    }
}
