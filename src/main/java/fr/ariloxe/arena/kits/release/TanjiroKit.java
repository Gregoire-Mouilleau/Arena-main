package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.ArenaAPI;
import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.skull.SkullList;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class TanjiroKit extends KitSchedule {

    private final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 20*30, 0, false, false);
    private final PotionEffect strenghtEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*30, 0, false, false);

    public TanjiroKit() {
        super("Tanjiro", KitType.DPS, SkullList.TANJIRO.getItemStack(),
                "§8» §7Mode : §3Démon-Slayer",
                "§8» §7Pouvoirs:",
                "§f- §7Gagnez les effets §bSpeed I §7et §5Force I§7 pendant 30 secondes. (§a55s de cooldown§7)",
                "§f- §7Quand votre pouvoir sera fini, vous perdrez §c1 coeur permanent§7.");
        super.setSecondsDelay(55);
    }

    @Override
    public void power(KPlayer kPlayer){
        Player player = kPlayer.getBukkitPlayer();
        if(player != null){
            player.addPotionEffect(speedEffect);
            player.addPotionEffect(strenghtEffect);
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.playSound(player.getLocation(), "pyralia.tanjiro", 5, 5));
            player.sendMessage("§3§lMenestis §f» §7Vous avez activé votre §6Danse du dieu du feu§7 ! Vous perdrez §61 coeurs permanents§7 à la fin de votre pouvoir.");
            Bukkit.getScheduler().runTaskLater(ArenaAPI.getApi(), ()-> player.setMaxHealth(player.getMaxHealth() - 2), 20*30);
        }
    }

    @Override
    public void onEquip(Player player){
        player.getInventory().addItem(new ItemCreator(Material.BLAZE_ROD).name("§6Danse du dieu du feu §8§l▪ §7Clic-droit").lore("", "§fVous permet d'activer le pouvoir de la danse", "§fdu dieu du feu toutes les 55s.").get());
    }
}
