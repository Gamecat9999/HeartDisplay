package com.heavensystem.heartdisplay;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HeartDisplay extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HealthListener(), this);
        Bukkit.getLogger().info("[HeartDisplay] Enabled and listening for health updates.");
    }
}