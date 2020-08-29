package com.github.tadashiipasu.tparenabot.pojos.builders;

import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.Viewer;

public class ViewerBuilder {
    private String username;
    private String userId;
    private Integer streamPoints;
    private boolean checkedIn;
    private StatBlock stats;

    public ViewerBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public ViewerBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public ViewerBuilder setStreamPoints(Integer streamPoints) {
        this.streamPoints = streamPoints;
        return this;
    }

    public ViewerBuilder setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
        return this;
    }
    public ViewerBuilder setStats(StatBlock stats) {
        this.stats = stats;
        return this;
    }

    public Viewer build() {
        return new Viewer(username, userId, streamPoints, checkedIn, stats);
    }
}
