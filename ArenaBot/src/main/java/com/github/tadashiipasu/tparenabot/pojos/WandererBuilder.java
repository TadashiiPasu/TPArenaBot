package com.github.tadashiipasu.tparenabot.pojos;

import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class WandererBuilder {
    private String username;
    private String userId;
    private Integer level;
    private Integer experience;
    private StatBlock originalStats;
    private StatBlock adjustedStats;
    private ModBlock modifiers;
    private List<Integer> moves;
    @Nullable
    private Map<String, Integer> status;
    @Nullable
    private List<Effect> effect;
    private boolean challenger;
    private boolean challenged;
    private boolean dueling;
    private String opponentId;

    public WandererBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public WandererBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public WandererBuilder setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public WandererBuilder setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public WandererBuilder setOriginalStats(StatBlock originalStats) {
        this.originalStats = originalStats;
        return this;
    }

    public WandererBuilder setAdjustedStats(StatBlock adjustedStats) {
        this.adjustedStats = adjustedStats;
        return this;
    }

    public WandererBuilder setModifiers(ModBlock modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public WandererBuilder setMoves(List<Integer> moves) {
        this.moves = moves;
        return this;
    }

    public WandererBuilder setStatus(Map<String, Integer> status) {
        this.status = status;
        return this;
    }

    public WandererBuilder setEffect(List<Effect> effect) {
        this.effect = effect;
        return this;
    }

    public WandererBuilder setChallenger(boolean challenger) {
        this.challenger = challenger;
        return this;
    }

    public WandererBuilder setChallenged(boolean challenged) {
        this.challenged = challenged;
        return this;
    }

    public WandererBuilder setDueling(boolean dueling) {
        this.dueling = dueling;
        return this;
    }

    public WandererBuilder setOpponentId(String opponentId) {
        this.opponentId = opponentId;
        return this;
    }

    public Wanderer build() {
        return new Wanderer(username, userId, level, experience, originalStats, adjustedStats, modifiers, moves, status, effect, challenger, challenged, dueling, opponentId);
    }
}
