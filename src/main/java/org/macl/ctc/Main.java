package org.macl.ctc;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.macl.ctc.events.world;

public final class Main extends JavaPlugin implements CommandExecutor {

    public boolean isUnloading = false;

    public String map = "map";

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("ctc").setExecutor(this);
        getLogger().info("Started!");
        getServer().getPluginManager().registerEvents(new world(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Ended");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.isOp())
                return false;

            if(args[0].equalsIgnoreCase("reset")) {

                isUnloading = true;
                clearAll();
                boolean unloaded = getServer().unloadWorld(Bukkit.getWorld(map), false);
                Bukkit.broadcast(Component.text("unload success " + unloaded));

                getServer().createWorld(new WorldCreator(map));
                Bukkit.broadcast(Component.text("load success"));
                isUnloading = false;
            } else if(args[0].equalsIgnoreCase("teleport")) {
                World w = Bukkit.getWorld(args[1]);
                p.teleport(w.getSpawnLocation());
            } else if(args[0].equalsIgnoreCase("start")) {
                Bukkit.broadcast(Component.text("Hello"));
            }

            // reset world

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }

    public void clearAll() {
        World w =  getServer().getWorld("world");
        for(Player p : Bukkit.getOnlinePlayers())
            p.kick();
        for(Chunk c : w.getLoadedChunks()) {
            c.load();
            for(Entity e : c.getEntities()) {
                e.remove();
            }
            c.unload(true);
        }
    }
}
