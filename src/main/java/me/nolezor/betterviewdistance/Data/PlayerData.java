package me.nolezor.betterviewdistance.Data;

public class PlayerData {
    final private String playerUUID;
    private int currentViewDistance;

    public PlayerData(String playerUUID, int customViewDistance) {
        this.playerUUID = playerUUID;
        this.currentViewDistance = customViewDistance;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public int getCurrentViewDistance() {
        return currentViewDistance;
    }

    public void setCurrentViewDistance(int currentViewDistance) {
        this.currentViewDistance = currentViewDistance;
    }
}
