package org.macl.ctc.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.macl.ctc.Main;
import org.macl.ctc.kits.Grandma;
import org.macl.ctc.kits.Kit;
import org.macl.ctc.kits.Snowballer;
import org.macl.ctc.kits.Spy;

public class Interact extends DefaultListener {
    public Interact(Main main) {
        super(main);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        switch(event.getAction()) {
            case PHYSICAL:
                physical(event);
            case RIGHT_CLICK_BLOCK:
                rightClick(event);
            case RIGHT_CLICK_AIR:
                rightClick(event);
            case LEFT_CLICK_BLOCK:
                leftClick(event);
            case LEFT_CLICK_AIR:
                leftClick(event);
            default:
                break;

        }
    }

    private void leftClick(PlayerInteractEvent event) {
    }

    private void rightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Material m = event.getPlayer().getInventory().getItemInMainHand().getType();
        if (kit.kits.get(p.getUniqueId()) != null) {
            Kit k = kit.kits.get(p.getUniqueId());
            if (k instanceof Snowballer) {
                Snowballer snowball = (Snowballer) k;
                if (m == Material.GOLDEN_HOE)
                    snowball.launch();
                else if (m == Material.WOODEN_SWORD)
                    snowball.shootSnowball();
            }
            if(k instanceof Grandma) {
                Grandma g = (Grandma) k;
                if(m == Material.COOKIE)
                    g.heart();
            }
            if(k instanceof Spy) {
                Spy s = (Spy) k;
                if(m == Material.BLAZE_ROD)
                    s.detonate();
            }
        }

    }

    private void physical(PlayerInteractEvent event) {
    }


}
