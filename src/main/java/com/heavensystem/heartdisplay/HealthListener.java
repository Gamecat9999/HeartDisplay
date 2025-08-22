package com.heavensystem.heartdisplay;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scoreboard.*;

public class HealthListener implements Listener {

    private final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    private final Objective healthObj;

    public HealthListener() {
        if (board.getObjective("displayhealth") == null) {
            healthObj = board.registerNewObjective("displayhealth", "health", "❤");
            healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        } else {
            healthObj = board.getObjective("displayhealth");
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            healthObj.getScore(player.getName()).setScore((int) player.getHealth());
        }
    }

    private void updateHealth(LivingEntity entity) {
        int current = (int) entity.getHealth();
        int max = (int) entity.getMaxHealth();

        if (entity instanceof Player player) {
            healthObj.getScore(player.getName()).setScore(current);
        } else {
            entity.setCustomName("❤ " + current + "/" + max);
            entity.setCustomNameVisible(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("HeartDisplay"),
                () -> updateHealth(entity),
                1L
            );
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("HeartDisplay"),
                () -> updateHealth(entity),
                1L
            );
        }
    }
}