package com.github.tadashiipasu.tparenabot.pojos;

import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wanderer {
    private String username;
    private String userId;
    private Integer level;
    private Integer experience;
    private StatBlock stats;
    private List<Integer> moves;
    @Nullable
    private List<String> status;
    @Nullable
    private List<Effect> effect;
    private boolean challenged;
    private boolean dueling;
    private String opponentId;

    public Wanderer(String username, String userId, Integer level, Integer experience, StatBlock stats,
                    List<Integer> moves, @Nullable List<String> status, @Nullable List<Effect> effect, boolean challenged,
                    boolean dueling, String opponentId) {
        this.username = username;
        this.userId = userId;
        this.level = level;
        this.experience = experience;
        this.stats = stats;
        this.moves = moves;
        this.status = status;
        this.effect = effect;
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

    public StatBlock getStats() {
        return stats;
    }

    public void setStats(StatBlock stats) {
        this.stats = stats;
    }

    public List<Integer> getMoves() {
        return moves;
    }

    public void setMoves(List<Integer> moves) {
        this.moves = moves;
    }

    public @Nullable List<String> getStatus() {
        return status;
    }

    public void setStatus(@Nullable List<String> status) {
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
