package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.utils.ItemCreator;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * @author Ariloxe
 */
public class ZenitsuKit extends KitSchedule implements Listener {
    private final Random random = new Random(); // Pour générer des nombres aléatoires
    private final Set<Player> lightningActivePlayers = new HashSet<>(); // Liste des joueurs avec l'effet actif

    public ZenitsuKit() {
        super("Zenitsu", KitType.DEFENSIVE, new ItemStack(SkullList.ZENITSU.getItemStack()),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Vous aurez §bSpeed I§7 en permanence.",
                "§f- §7Vous aurez §bSpeed II§7 pendant 9 secondes (§a27 secondes de cooldown§7).",
                "§f- §7Puis, vous aurez §9Weakness I§7 pendant §b7 secondes§7.",
                "§f- §730% de chance d'invoquer un éclair sur l'ennemi frappé après activation de l'item.",
                "");

        super.setSecondsDelay(27);
        Bukkit.getPluginManager().registerEvents(this, ArenaAPI.getApi()); // Enregistrer l'événement du listener
    }

    private final PotionEffect permanentSpeedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false);
    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 20 * 9, 1, false, false); // Speed II
    private final PotionEffect weaknessEffect = new PotionEffect(PotionEffectType.WEAKNESS, 20 * 7, 0, false, false); // Weakness I

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if (player != null) {
            player.removePotionEffect(PotionEffectType.SPEED); // Retirer Speed I
            player.addPotionEffect(speedEffect); // Appliquer Speed II

            // Ajouter le joueur à la liste des joueurs ayant activé l'item pour l'effet d'éclair
            lightningActivePlayers.add(player);

            // Programmer la récupération de Speed I après Speed II et l'application de Weakness
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
                if (player != null) {
                    player.addPotionEffect(weaknessEffect); // Appliquer Weakness I
                    player.addPotionEffect(permanentSpeedEffect); // Récupérer la Speed I permanente
                }
            }, 20 * 9); // 9 secondes après l'activation

            // Supprimer l'effet d'éclair après 9 secondes
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), () -> {
                lightningActivePlayers.remove(player); // Retirer le joueur de la liste
            }, 20 * 9);
        }
    }

    @Override
    public void onEquip(Player player) {
        player.getInventory().addItem(new ItemCreator(Material.FEATHER).name("§eSprint Légendaire §8§l▪ §7Clic-droit").get());
        player.addPotionEffect(permanentSpeedEffect); // Ajouter la Speed I permanente
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            // Vérifier si le joueur a activé l'item et est dans la liste des joueurs avec l'effet actif
            if (lightningActivePlayers.contains(damager)) {
                // 30% de chance d'invoquer un éclair
                if (random.nextInt(100) < 30) { // 30% de chance
                    event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation()); // Effet d'éclair visuel
                    // Infliger exactement 2 cœurs de dégâts (4 points de vie dans Minecraft)
                    if (event.getEntity() instanceof Player) {
                        Player target = (Player) event.getEntity();
                        target.damage(4.0); // Infliger 4 points de dégâts (2 cœurs)
                    }
                }
            }
        }
    }
}
