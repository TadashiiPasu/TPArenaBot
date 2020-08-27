package com.github.tadashiipasu.tparenabot.pojos.moves;

import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Move {
    private Integer id;
    private Integer level;
    private String moveName;
    private String type;
    @Nullable
    private Integer damage;
    @Nullable
    private Integer accuracy;
    @Nullable
    private Integer critChance;
    @Nullable
    private Map<String, Integer> status;
    @Nullable
    private Effect effect;
    private boolean priority;
    private StatBlock requiredStats;

    public Move(Integer id, Integer level, String moveName, String type, @Nullable Integer damage, @Nullable Integer accuracy,
                @Nullable Integer critChance, @Nullable Map<String, Integer> status, @Nullable Effect effect, boolean priority, StatBlock requiredStats) {
        this.id = id;
        this.level = level;
        this.moveName = moveName;
        this.type = type;
        this.damage = damage;
        this.accuracy = accuracy;
        this.status = status;
        this.effect = effect;
        this.priority = priority;
        this.requiredStats = requiredStats;
    }

    public Move() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public @Nullable Integer getDamage() {
        return damage;
    }

    public void setDamage(@Nullable Integer damage) {
        this.damage = damage;
    }

    public @Nullable Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(@Nullable Integer accuracy) {
        this.accuracy = accuracy;
    }

    public @Nullable Integer getCritChance() {
        return critChance;
    }

    public void setCritChance(@Nullable Integer critChance) {
        this.critChance = critChance;
    }

    public @Nullable Map<String, Integer> getStatus() {
        return status;
    }

    public void setStatus(@Nullable Map<String, Integer> status) {
        this.status = status;
    }

    public @Nullable Effect getEffect() {
        return effect;
    }

    public void setEffect(@Nullable Effect effect) {
        this.effect = effect;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public StatBlock getRequiredStats() {
        return requiredStats;
    }

    public void setRequiredStats(StatBlock requiredStats) {
        this.requiredStats = requiredStats;
    }
}
