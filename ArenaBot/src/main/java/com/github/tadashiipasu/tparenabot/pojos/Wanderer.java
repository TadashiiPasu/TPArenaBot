package com.github.tadashiipasu.tparenabot.pojos;

import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class Wanderer {
    private String username;
    private String userId;
    private Integer level;
    private Integer experience;
    private StatBlock originalStats;
    private StatBlock adjustedStats;
    private ModBlock modifiers;
    private List<Integer> moves;
    private Integer levelupMove;
    @Nullable
    private Map<String, Integer> status;
    @Nullable
    private List<Effect> effect;
    private boolean challenger;
    private boolean challenged;
    private boolean dueling;
    private String opponentId;

    public Wanderer(String username, String userId, Integer level, Integer experience, StatBlock originalStats,
                    StatBlock adjustedStats, ModBlock modifiers, List<Integer> moves, Integer levelupMove,
                    @Nullable Map<String, Integer> status, @Nullable List<Effect> effect, boolean challenger,
                    boolean challenged, boolean dueling, String opponentId) {
        this.username = username;
        this.userId = userId;
        this.level = level;
        this.experience = experience;
        this.originalStats = originalStats;
        this.adjustedStats = adjustedStats;
        this.modifiers = modifiers;
        this.moves = moves;
        this.levelupMove = levelupMove;
        this.status = status;
        this.effect = effect;
        this.challenger = challenger;
        this.challenged = challenged;
        this.dueling = dueling;
        this.opponentId = opponentId;
    }

    public Wanderer() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public StatBlock getOriginalStats() {
        return originalStats;
    }

    public void setOriginalStats(StatBlock originalStats) {
        this.originalStats = originalStats;
    }

    public StatBlock getAdjustedStats() {
        return adjustedStats;
    }

    public void setAdjustedStats(StatBlock adjustedStats) {
        this.adjustedStats = adjustedStats;
    }

    public ModBlock getModifiers() {
        return modifiers;
    }

    public void setModifiers(ModBlock modifiers) {
        this.modifiers = modifiers;
    }

    public List<Integer> getMoves() {
        return moves;
    }

    public void setMoves(List<Integer> moves) {
        this.moves = moves;
    }

    public Integer getLevelupMove() {
        return levelupMove;
    }

    public void setLevelupMove(Integer levelupMove) {
        this.levelupMove = levelupMove;
    }

    public @Nullable Map<String, Integer> getStatus() {
        return status;
    }

    public void setStatus(@Nullable Map<String, Integer> status) {
        this.status = status;
    }

    public @Nullable List<Effect> getEffect() {
        return effect;
    }

    public void setEffect(@Nullable List<Effect> effect) {
        this.effect = effect;
    }

    public boolean isChallenged() {
        return challenged;
    }

    public boolean isDueling() {
        return dueling;
    }

    public boolean isChallenger() {
        return challenger;
    }

    public void setChallenger(boolean challenger) {
        this.challenger = challenger;
    }

    public boolean getChallenged() {
        return challenged;
    }

    public void setChallenged(boolean challenged) {
        this.challenged = challenged;
    }

    public boolean getDueling() {
        return dueling;
    }

    public void setDueling(boolean dueling) {
        this.dueling = dueling;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

}
