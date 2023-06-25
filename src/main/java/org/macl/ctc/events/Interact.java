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

public class Interact extends DefaultListener {
    public Interact(Main main) {
        super(main);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Material m = event.getPlayer().getInventory().getItemInMainHand().getType();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
            }
        }
    }

}
