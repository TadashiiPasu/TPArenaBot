package com.github.tadashiipasu.tparenabot.pojos;

public class StatBlock {
    private Integer maxHealth;
    private Integer currentHealth;
    private Integer strength;
    private Integer magic;
    private Integer defense;
    private Integer magicDefense;
    private Integer accuracy;
    private Integer evasion;
    private Integer speed;

    public StatBlock(Integer maxHealth, Integer currentHealth, Integer strength, Integer magic, Integer defense,
                     Integer magicDefense, Integer accuracy, Integer evasion, Integer speed) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.strength = strength;
        this.magic = magic;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.accuracy = accuracy;
        this.evasion = evasion;
        this.speed = speed;
    }

    public StatBlock() {
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(Integer magicDefense) {
        this.magicDefense = magicDefense;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getEvasion() {
        return evasion;
    }

    public void setEvasion(Integer evasion) {
        this.evasion = evasion;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
