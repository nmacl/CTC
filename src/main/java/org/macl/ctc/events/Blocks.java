package org.macl.ctc.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.macl.ctc.Main;


public class Blocks extends DefaultListener {

    public Blocks(Main main) {
        super(main);
    }

    @EventHandler
    public void worldSave(WorldSaveEvent event) {
        Bukkit.broadcast(Component.text("This should not be happening. " + event.getWorld().getName()));
    }

    @EventHandler
    public void worldUnload(WorldUnloadEvent event) {
        Bukkit.broadcast(Component.text("Broken"));
    }


}
