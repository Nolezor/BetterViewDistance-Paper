package me.nolezor.betterviewdistance.Utils;

import com.google.gson.Gson;
import me.nolezor.betterviewdistance.BetterViewDistance;
import me.nolezor.betterviewdistance.Data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerDataStorageUtils {
    private static final Plugin plugin = BetterViewDistance.getPlugin();
    private static ArrayList<PlayerData> playersData = new ArrayList<>();

    public static PlayerData createData(String UUID, int customViewDistance) {
        PlayerData playerData = new PlayerData(UUID, customViewDistance);
        playersData.add(playerData);
        try {
            saveData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return playerData;
    }

    public static PlayerData searchData(String uuid) {
        for (PlayerData data: playersData) {
            if (data.getPlayerUUID().equalsIgnoreCase(uuid)) {
                return data;
            }
        }
        return null;
    }

    public static void saveData() throws IOException {
        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/PlayerData.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(playersData, writer);
        writer.flush();
        writer.close();
    }

    public static void loadPlayersData() throws IOException {
        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/playerData.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            PlayerData[] d = gson.fromJson(reader, PlayerData[].class);
            playersData = new ArrayList<>(Arrays.asList(d));
        }
    }

    public static void fixData() {
        for (PlayerData data: playersData) {
            if (plugin.getServer().getViewDistance() == data.getCurrentViewDistance()) {
                plugin.getServer().getPlayer(data.getPlayerUUID()).setViewDistance(plugin.getServer().getViewDistance());
                playersData.remove(data);
            }
        }
    }

    public static void setPlayerViewDistance(PlayerData data) {
        Player player = plugin.getServer().getPlayer(data.getPlayerUUID());
        player.setViewDistance(data.getCurrentViewDistance());
    }
}
