package org.macl.ctc.timers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.macl.ctc.kits.Kit;
import org.macl.ctc.kits.Snowballer;

public class cooldownTimer extends BukkitRunnable {
    Kit k;
    int timer;
    String name;
    ItemStack item;
    short maxDur;
    int initialCount = 0;

    int itemCount = 0;

    public cooldownTimer(Kit k, int timer, String name, ItemStack item) {
        this.k = k;
        this.timer = timer;
        this.name = name;
        this.item = item;
        this.maxDur = item.getType().getMaxDurability();
        k.cooldowns.put(name, true);
        ItemMeta itemMeta = item.getItemMeta();
        Damageable damage = (Damageable) itemMeta;
        if (itemMeta instanceof Damageable){
            damage.setDamage(maxDur-2);
        }
        this.initialCount = timer;

    }

    @Override
    public void run() {
        Player p = k.getPlayer();
        if(p.getInventory().first(item.getType()) == -1 || p == null || k.main.game.started == false || k.main.kit.kits.get(p.getUniqueId()) == null) {
            this.cancel();
            return;
        }

        if(timer <= 5)
            k.playExp(timer);

        // FIX
        //int totalDmg = counter*(durability/ogCount);

        //Ratio = Durability / Initial Count (100/10) = 10 damages per iteration
        double ratio = Math.ceil(maxDur/initialCount);

        // Added a seperate variable going from 1 to n because I'm lazy
        itemCount++;
        int dmg = ((maxDur)-(int)(ratio*itemCount));

        int slot = p.getInventory().first(item.getType());
        ItemStack stack = p.getInventory().getItem(slot);
        ItemMeta itemMeta = stack.getItemMeta();
        Damageable damage = (Damageable) itemMeta;
        if (itemMeta instanceof Damageable){
            damage.setDamage(dmg);
        }

        stack.setItemMeta(itemMeta);

        if(timer == 0) {
            k.cooldowns.put(name, false);
            this.cancel();
            p.getInventory().remove(item.getType());
            p.getInventory().setItem(slot, item);
        }
        p.updateInventory();
        timer--;
    }
}

