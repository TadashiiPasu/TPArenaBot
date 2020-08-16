package com.github.tadashiipasu.tparenabot.pojos;

import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandererBuilder {
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

    public WandererBuilder setStats(StatBlock stats) {
        this.stats = stats;
        return this;
    }

    public WandererBuilder setMoves(List<Integer> moves) {
        this.moves = moves;
        return this;
    }

    public WandererBuilder setStatus(List<String> status) {
        this.status = status;
        return this;
    }

    public WandererBuilder setEffect(List<Effect> effect) {
        this.effect = effect;
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
        return new Wanderer(username, userId, level, experience, stats, moves, status, effect, challenged, dueling, opponentId);
    }
}
