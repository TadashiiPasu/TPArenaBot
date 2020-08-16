package com.github.tadashiipasu.tparenabot.pojos;

public class Viewer {
    private String username;
    private String userId;
    private Integer streamPoints;
    private StatBlock stats;

    public Viewer(String username, String userId, Integer streamPoints, StatBlock stats) {
        this.username = username;
        this.userId = userId;
        this.streamPoints = streamPoints;
        this.stats = stats;
    }

    public Viewer() {
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

    public Integer getStreamPoints() {
        return streamPoints;
    }

    public void setStreamPoints(Integer streamPoints) {
        this.streamPoints = streamPoints;
    }

    public StatBlock getStats() {
        return stats;
    }

    public void setStats(StatBlock stats) {
        this.stats = stats;
    }

}
