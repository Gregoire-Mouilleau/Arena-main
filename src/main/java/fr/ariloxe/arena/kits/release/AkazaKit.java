package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AkazaKit extends KitSchedule {

    private final PotionEffect strengthEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 20, 0, false, false);
    private final PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 0, false, false);
    private static final int COOLDOWN_TIME = 40;

    public AkazaKit() {
        super("Akaza", KitType.TANK, new ItemStack(Material.BLAZE_POWDER),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous avez 20 coeurs.",
                "§f- §7Cliquez avec l'item pour obtenir Force I et Résistance I pendant 20 secondes. (§a" + COOLDOWN_TIME + " secondes de cooldown§7)"
        );
        super.setSecondsDelay(COOLDOWN_TIME);
    }

    @Override
    public void onEquip(Player player) {
        player.setMaxHealth(40);  // 20 coeurs (2 points de vie = 1 coeur)
        player.setHealth(player.getMaxHealth());
        player.getInventory().addItem(new ItemCreator(Material.BLAZE_POWDER).name("§3Potion de Force et Résistance §8§l▪ §7Clic-droit").get());
        createHealthArmorStand(player);  // Création de l'Armor Stand au-dessus du joueur
    }

    private void createHealthArmorStand(Player player) {
        // Création d'un Armor Stand au-dessus du joueur
        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);  // L'armor stand est invisible
        armorStand.setCustomNameVisible(true);  // Le nom personnalisé est visible
        armorStand.setMarker(true);  // Réduit la hitbox
        armorStand.setGravity(false);  // Pas de gravité pour qu'il reste en l'air
        armorStand.setCustomName("§c" + player.getHealth() + "❤");  // Affichage des points de vie

        // Mise à jour de la position de l'Armor Stand pour qu'il suive le joueur
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead()) {
                    armorStand.remove();  // Supprime l'armor stand si le joueur meurt ou se déconnecte
                    cancel();
                    return;
                }
                // Met à jour la position et la santé affichée
                Location location = player.getLocation().clone().add(0, 1.9, 0);  // Ajuste la hauteur pour être au-dessus du joueur
                armorStand.teleport(location);
                armorStand.setCustomName("§c" + Math.round(player.getHealth()) + "❤");  // Mise à jour de la santé affichée
            }
        }.runTaskTimer(ArenaAPI.getApi(), 0, 10);  // Mise à jour toutes les 0.5 secondes
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if (player != null) {
            player.addPotionEffect(strengthEffect);
            player.addPotionEffect(resistanceEffect);
            player.getInventory().removeItem(new ItemStack(Material.BLAZE_POWDER, 1));
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
                player.getInventory().addItem(new ItemCreator(Material.BLAZE_POWDER).name("§3Potion de Force et Résistance §8§l▪ §7Clic-droit").get());
            }, COOLDOWN_TIME * 20);
        }
    }
}
