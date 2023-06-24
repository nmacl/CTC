package org.macl.ctc.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.macl.ctc.Default;
import org.macl.ctc.Main;

public class WorldManager extends Default {

    public boolean isUnloading = false;
    public String map = "map";

    private World w;

    public WorldManager(Main main) {
        super(main);
        w = main.getServer().getWorld("map");
    }

    // CLeanup world by unloading then loading world. Should be implemented with file systems to grab unloaded alternate maps.
    public void clean() {
        isUnloading = true;
        // fix this to kick back with bungee cord
        for(Player p : Bukkit.getOnlinePlayers())
            p.kick();

        for(Chunk c : w.getLoadedChunks()) {
            c.load();
            for(Entity e : c.getEntities()) {
                e.remove();
            }
            c.unload(true);
        }

        boolean unloaded = main.getServer().unloadWorld(Bukkit.getWorld(map), false);
        Bukkit.broadcast(Component.text("unload: " + unloaded));

        main.getServer().createWorld(new WorldCreator(map));
        Bukkit.broadcast(Component.text("load success"));

        isUnloading = false;
    }

}
