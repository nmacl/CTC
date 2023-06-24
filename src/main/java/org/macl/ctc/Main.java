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
import org.macl.ctc.events.Blocks;
import org.macl.ctc.game.GameManager;
import org.macl.ctc.game.WorldManager;

public final class Main extends JavaPlugin implements CommandExecutor {


    public String map = "map";

    public GameManager game;
    public WorldManager worldManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("ctc").setExecutor(this);
        getLogger().info("Started!");
        getServer().getPluginManager().registerEvents(new Blocks(this), this);
        // Setup map / game
        game = new GameManager(this);
        worldManager = new WorldManager(this);
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
                worldManager.clean();
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
}
