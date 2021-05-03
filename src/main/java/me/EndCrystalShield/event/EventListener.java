package me.EndCrystalShield.event;

import me.EndCrystalShield.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.logging.Logger;

public class EventListener implements Listener {

    private final Logger logger;
    private Main plugin;

    public EventListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.logger = plugin.getLogger();

        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrystalDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.ENDER_CRYSTAL) {
            Player player = (Player) event.getEntity();
            EnderCrystal damager = (EnderCrystal) event.getDamager();
            double damage = event.getDamage();
            if (player.isBlocking() && damage > 10 && playerFacingCrystal(damager, player)) {
                event.setDamage(10);
                Location loc = player.getLocation().clone();
                Zombie zombie = (Zombie) player.getWorld().spawnEntity(loc.add(loc.getDirection().normalize().multiply(2)),
                        EntityType.ZOMBIE);
                zombie.setInvisible(true);
                zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                player.damage(0.5, zombie);
                zombie.remove();
                
                //Adjust the shield's cooldown here
                player.setCooldown(Material.SHIELD, 20);
            }
        }
    }

    private boolean playerFacingCrystal(Entity entity, Player player) {
        Double yaw = 2*Math.PI-Math.PI*player.getLocation().getYaw()/180;
        Vector v = entity.getLocation().toVector().subtract(player.getLocation().toVector());
        Vector r = new Vector(Math.sin(yaw),0, Math.cos(yaw));
        float theta = r.angle(v);
        if (Math.PI/2<theta && theta<3*Math.PI/2) {
            return false;
        }
        return true;
    }
}
