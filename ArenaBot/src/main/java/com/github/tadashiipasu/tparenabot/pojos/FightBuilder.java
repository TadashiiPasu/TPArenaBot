package com.github.tadashiipasu.tparenabot.pojos;

public class FightBuilder {
    private String fightId;
    private String wandererOneId;
    private String wandererTwoId;
    public Integer wandererOneMove;
    public Integer wandererTwoMove;

    public FightBuilder setFightId(String username) {
        this.fightId = username;
        return this;
    }

    public FightBuilder setWandererOneId(String wandererOneId) {
        this.wandererOneId = wandererOneId;
        return this;
    }

    public FightBuilder setWandererTwoId(String wandererTwoId) {
        this.wandererTwoId = wandererTwoId;
        return this;
    }

    public FightBuilder setWandererOneMove(Integer wandererOneMove) {
        this.wandererOneMove = wandererOneMove;
        return this;
    }

    public FightBuilder setWandererTwoMove(Integer wandererTwoMove) {
        this.wandererTwoMove = wandererTwoMove;
        return this;
    }

    public Fight build() {
        return new Fight(fightId, wandererOneId, wandererTwoId, wandererOneMove, wandererTwoMove);
    }
}
