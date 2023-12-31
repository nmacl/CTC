package org.macl.ctc.events;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.macl.ctc.Main;
import org.macl.ctc.kits.Kit;
import org.macl.ctc.kits.Spy;

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
    public void launch(PlayerLaunchProjectileEvent event) {
        Projectile proj = event.getProjectile();
        Player p = event.getPlayer();
        if(proj instanceof EnderPearl) {
            EnderPearl e = (EnderPearl) proj;
            e.setPassenger(p);
        }
    }

    @EventHandler
    public void land(PlayerTeleportEvent event) {
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
            event.setCancelled(true);
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if(event.getCause() == EntityDamageEvent.DamageCause.VOID)
                p.setHealth(0);
            if(event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING)
                event.setCancelled(true);
            if(main.getKits().get(p.getUniqueId()) != null && main.getKits().get(p.getUniqueId()) instanceof Spy)
                ((Spy) main.getKits().get(p.getUniqueId())).uninvis();


        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if(event.getDamager() instanceof Player) {
                Player attacker = (Player) event.getDamager();
                if(attacker.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE)
                    event.setDamage(0.5);
                if(main.getKits().get(attacker.getUniqueId()) != null && main.getKits().get(attacker.getUniqueId()) instanceof Spy && attacker.getInventory().getItemInMainHand().getType() == Material.IRON_HOE) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*2, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 5, 2));
                }

            }
            if (event.getDamager() instanceof Snowball)
                event.setDamage(0.15);
        }

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
