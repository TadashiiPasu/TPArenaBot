package com.github.tadashiipasu.tparenabot.pojos.moves;

import org.jetbrains.annotations.Nullable;

@Nullable
public class Effect {
    @Nullable
    private String effectName;
    @Nullable
    private String stat;
    @Nullable
    private Integer amount;
    @Nullable
    private Integer duration;

    public Effect(@Nullable String effectName, @Nullable String stat, @Nullable Integer amount, @Nullable Integer duration) {
        this.effectName = effectName;
        this.stat = stat;
        this.amount = amount;
        this.duration = duration;
    }

    public Effect(){
    }

    public String getEffect() {
        return effectName;
    }

    public void setEffect(String effectName) {
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
}
