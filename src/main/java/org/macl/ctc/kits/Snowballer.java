package org.macl.ctc.kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.macl.ctc.Main;
import org.macl.ctc.timers.cooldownTimer;

public class Snowballer extends Kit {

    public ItemStack rocketJump = newItem(Material.GOLDEN_HOE, ChatColor.YELLOW + "Rocket Jump");

    public Snowballer(Main main, Player p, KitType type) {
        super(main, p, type);
        cooldowns.put("rocketCool", false);
        e.addItem(newItem(Material.WOODEN_SWORD, ChatColor.DARK_BLUE + "Snowball Launcher"));
        e.addItem(rocketJump);
        e.setBoots(newItemEnchanted(Material.DIAMOND_BOOTS, "Feather Boots", Enchantment.PROTECTION_FALL, 7));
        giveWool();
        giveWool();
    }
    public void shootSnowball() {
        p.launchProjectile(Snowball.class);
    }

    public void launch() {
        if(!cooldowns.get("rocketCool")) {
            p.setVelocity(p.getLocation().getDirection().                                                                                                                                                                                                                                                                                                                                                                    multiply(2.3f));
            new cooldownTimer(this, 30, "rocketCool", rocketJump).runTaskTimer(main, 0L, 5L);
        }
    }

    // CLEAN AND TURN INTO A KIT METHOD!

}
