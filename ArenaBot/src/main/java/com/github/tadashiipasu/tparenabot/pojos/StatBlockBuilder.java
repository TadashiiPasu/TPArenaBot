package com.github.tadashiipasu.tparenabot.pojos;

public class StatBlockBuilder {
    private Integer maxHealth;
    private Integer currentHealth;
    private Integer strength;
    private Integer magic;
    private Integer defense;
    private Integer magicDefense;
    private Integer accuracy;
    private Integer evasion;
    private Integer speed;

    public StatBlockBuilder setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public StatBlockBuilder setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public StatBlockBuilder setStrength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public StatBlockBuilder setMagic(Integer magic) {
        this.magic = magic;
        return this;
    }

    public StatBlockBuilder setDefense(Integer defense) {
        this.defense = defense;
        return this;
    }

    public StatBlockBuilder setMagicDefense(Integer magicDefense) {
        this.magicDefense = magicDefense;
        return this;
    }

    public StatBlockBuilder setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public StatBlockBuilder setEvasion(Integer evasion) {
        this.evasion = evasion;
        return this;
    }

    public StatBlockBuilder setSpeed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public StatBlock build() {
        return new StatBlock(maxHealth, currentHealth, strength, magic, defense, magicDefense, accuracy, evasion, speed);
    }
}
