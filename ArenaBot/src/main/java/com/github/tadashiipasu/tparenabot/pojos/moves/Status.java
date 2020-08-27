package com.github.tadashiipasu.tparenabot.pojos.moves;

public enum Status {
    BURN("Burn"),
    FREEZE("Freeze"),
    PARALYSIS("Paralysis"),
    POISON("Poison"),
    SLEEP("Sleep");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}
