package org.macl.ctc.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.macl.ctc.Main;

public class Players extends DefaultListener {
    public Players(Main main) {
        super(main);
    }

    @EventHandler
    public void playerJoin(AsyncPlayerPreLoginEvent event) {
        if(world.isUnloading)
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        // Handle player leaving mid game (rebalance teams)
        Player p = event.getPlayer();
        p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        game.stack(p);
    }

    @EventHandler
    public void portal(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if(event.getTo().getBlock().getType() == Material.NETHER_PORTAL)
            game.stack(p);
    }

    @EventHandler
    public void food(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        event.getDrops().clear();
        kit.remove(event.getPlayer());
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        p.teleport(p.getWorld().getSpawnLocation());
        game.respawn(p);
    }

}
