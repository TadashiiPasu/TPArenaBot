package com.github.tadashiipasu.tparenabot.pojos.builders;

import com.github.tadashiipasu.tparenabot.pojos.ModBlock;

public class ModBlockBuilder {
    private String strength;
    private String magic;
    private String defense;
    private String magicDefense;
    private String accuracy;
    private String evasion;
    private String speed;

    public ModBlockBuilder setStrength(String strength) {
        this.strength = strength;
        return this;
    }

    public ModBlockBuilder setMagic(String magic) {
        this.magic = magic;
        return this;
    }

    public ModBlockBuilder setDefense(String defense) {
        this.defense = defense;
        return this;
    }

    public ModBlockBuilder setMagicDefense(String magicDefense) {
        this.magicDefense = magicDefense;
        return this;
    }

    public ModBlockBuilder setAccuracy(String accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public ModBlockBuilder setEvasion(String evasion) {
        this.evasion = evasion;
        return this;
    }

    public ModBlockBuilder setSpeed(String speed) {
        this.speed = speed;
        return this;
    }

    public ModBlock build() {
        return new ModBlock(strength, magic, defense, magicDefense, accuracy, evasion, speed);
    }
}
