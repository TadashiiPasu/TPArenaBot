package com.github.tadashiipasu.tparenabot.pojos.moves;

import org.jetbrains.annotations.Nullable;

@Nullable
public class Effect {
    private String effectName;
    @Nullable
    private String stat;
    @Nullable
    private Integer amount;
    private Integer duration;
    private String target;

    public Effect(String effectName, @Nullable String stat, @Nullable Integer amount, Integer duration, String target) {
        this.effectName = effectName;
        this.stat = stat;
        this.amount = amount;
        this.duration = duration;
        this.target = target;
    }

    public Effect(){
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public @Nullable String getStat() {
        return stat;
    }

    public void setStat(@Nullable String stat) {
        this.stat = stat;
    }

    public @Nullable Integer getAmount() {
        return amount;
    }

    public void setAmount(@Nullable Integer amount) {
        this.amount = amount;
    }

    public @Nullable Integer getDuration() {
        return duration;
    }

    public void setDuration(@Nullable Integer duration) {
        this.duration = duration;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
