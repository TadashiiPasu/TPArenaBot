package com.github.tadashiipasu.tparenabot.pojos;

public class Viewer {
    private String username;
    private String userId;
    private Integer streamPoints;
    private boolean checkedIn;
    private StatBlock stats;

    public Viewer(String username, String userId, Integer streamPoints, boolean checkedIn, StatBlock stats) {
        this.username = username;
        this.userId = userId;
        this.streamPoints = streamPoints;
        this.checkedIn = checkedIn;
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

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public StatBlock getStats() {
        return stats;
    }

    public void setStats(StatBlock stats) {
        this.stats = stats;
    }

}
