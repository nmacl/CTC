package org.macl.ctc;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.macl.ctc.events.Blocks;
import org.macl.ctc.events.Interact;
import org.macl.ctc.events.Players;
import org.macl.ctc.game.GameManager;
import org.macl.ctc.game.KitManager;
import org.macl.ctc.game.WorldManager;

import java.util.ArrayList;

public final class Main extends JavaPlugin implements CommandExecutor {


    public String map = "map";

    public GameManager game;
    public WorldManager worldManager;
    public KitManager kit;
    public String prefix = ChatColor.GOLD + "[CTC] " + ChatColor.GRAY;

    public void send(Player p, String text, ChatColor color) {
        p.sendMessage(prefix + color + text);
    }

    public void send(Player p, String text) {
        p.sendMessage(prefix + text);
    }

    public void broadcast(String text) {
        Bukkit.broadcast(Component.text(prefix + text));
    }

    public void broadcast(String text, ChatColor color) {
        Bukkit.broadcast(Component.text(prefix + color + text));
    }
    public ArrayList<Listener> listens = new ArrayList<Listener>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("ctc").setExecutor(this);
        getLogger().info("Started!");
        kit = new KitManager(this);
        worldManager = new WorldManager(this);
        game = new GameManager(this);

        new Interact(this);
        new Blocks(this);
        new Players(this);

        for(Listener i : listens)
            getServer().getPluginManager().registerEvents(i, this);
        // Setup map / game
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
                worldManager.clean(p);
            } else if(args[0].equalsIgnoreCase("teleport")) {
                World w = Bukkit.getWorld(args[1]);
                p.teleport(w.getSpawnLocation());
            } else if(args[0].equalsIgnoreCase("start")) {
                Bukkit.broadcast(Component.text("Hello"));
            }
            // change later so I can do maps (world manager)
            if(args[0].equalsIgnoreCase("red")) {
                worldManager.setRed(p);
            } else if(args[0].equalsIgnoreCase("blue")) {
                worldManager.setBlue(p);
            } else if(args[0].equalsIgnoreCase("center")) {
                worldManager.setCenter(p);
            } else if(args[0].equalsIgnoreCase("locs")) {
                Bukkit.broadcastMessage(worldManager.getRed().toString() + worldManager.getBlue().toString());
            }

            // reset world

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }

    public ItemStack coreCrush() {
        ItemStack crusher = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = crusher.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "CORE CRUSHER");
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        crusher.setItemMeta(meta);
        return crusher;
    }
}
