package org.macl.ctc.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.macl.ctc.Main;
import org.macl.ctc.kits.Spy;

import java.util.ArrayList;
import java.util.UUID;


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

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        event.setDropItems(false);
        game.resetCenter();
        Player p = event.getPlayer();
        Block b = event.getBlock();

        if(b.getType() == Material.OBSIDIAN && main.game.started && p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
            ArrayList<Material> blocs = getNearbyBlocks(b.getLocation(), 5);
            for(Material m : blocs) {
                //FIX CONSOLE ERROR, maybe cancel event?
                if(game.redHas(p) && game.center == 1 && m == Material.LAPIS_ORE)
                    game.stop(p);
                if(game.blueHas(p) && game.center == 2 && m == Material.REDSTONE_ORE)
                    game.stop(p);

            }
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Block b = event.getBlock();
        Player p = event.getPlayer();
        game.resetCenter();
        if (b.getType() == Material.RED_CANDLE)
            if (main.getKits().get(p.getUniqueId()) != null && main.getKits().get(p.getUniqueId()) instanceof Spy) {
                Spy s = (Spy) main.getKits().get(p.getUniqueId());
                if (s.getDetonate() != null)
                    return;
                s.setDetonate(b.getLocation());
                s.addDetonate();
                p.sendMessage(ChatColor.GREEN + "Remote explosive activated! Right click blaze rod to activate");
            }
    }


    @EventHandler
    public void blockBurn(BlockBurnEvent event) {
        game.resetCenter();
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent event) {
        event.setYield(0);
        game.resetCenter();
    }

    public ArrayList<Material> getNearbyBlocks(Location location, int radius) {
        ArrayList<Material> blocks = new ArrayList<Material>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z).getType());
                }
            }
        }
        return blocks;
    }


}
