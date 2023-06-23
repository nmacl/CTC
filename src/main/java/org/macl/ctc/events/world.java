package org.macl.ctc.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.macl.ctc.Main;


public class world extends eventManager {

    public world(Main main) {
        super(main);
    }

    @EventHandler
    public void worldSave(WorldSaveEvent event) {
        Bukkit.broadcast(Component.text("This should not be happening. " + event.getWorld().getName()));
    }

    @EventHandler
    public void playerJoin(AsyncPlayerPreLoginEvent event) {
        if(main.isUnloading)
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
    }

    @EventHandler
    public void worldUnload(WorldUnloadEvent event) {
        if(event.isCancelled()) {
            Bukkit.broadcast(Component.text("Cancelled"));
        }
    }

    @EventHandler
    public void worldLoad(WorldLoadEvent event) {

    }

}
