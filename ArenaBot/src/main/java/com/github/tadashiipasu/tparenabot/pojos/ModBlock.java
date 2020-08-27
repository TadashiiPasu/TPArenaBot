package com.github.tadashiipasu.tparenabot.pojos;

public class ModBlock {
    private String strength;
    private String magic;
    private String defense;
    private String magicDefense;
    private String accuracy;
    private String evasion;
    private String speed;

    public ModBlock(String strength, String magic, String defense,
                    String magicDefense, String accuracy, String evasion, String speed) {
        this.strength = strength;
        this.magic = magic;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.accuracy = accuracy;
        this.evasion = evasion;
        this.speed = speed;
    }

    public ModBlock() {
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(String magicDefense) {
        this.magicDefense = magicDefense;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getEvasion() {
        return evasion;
    }

    public void setEvasion(String evasion) {
        this.evasion = evasion;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
