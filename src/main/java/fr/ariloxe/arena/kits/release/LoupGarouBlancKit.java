package fr.ariloxe.arena.kits.release;

import fr.ariloxe.arena.kits.KitSchedule;
import fr.ariloxe.arena.kits.KitType;
import fr.ariloxe.arena.player.KPlayer;
import fr.ariloxe.arena.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Ariloxe
 */
public class LoupGarouBlancKit extends KitSchedule {

    public LoupGarouBlancKit() {
        super("Kit de Loup-Garou Blanc", KitType.TANK, new ItemStack(Material.REDSTONE),
                "§8» §7Mode : §9Loup-Garou UHC",
                "§8» §7Pouvoirs:",
                "§f- §7Vous possédez 15 cœurs",
                "§f- §c§lNéanmoins§7, seul votre plastron et vos bottes seront en diamant.");
        setSecondsDelay(35); // Définir le cooldown à 35 secondes
    }

    @Override
    public void power(KPlayer kPlayer) {
        Player player = kPlayer.getBukkitPlayer();
        if (player != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 0, false, false));
            player.sendMessage("§3§lMenestis §f» §7Vous avez activé votre pouvoir pendant 15 secondes.");
        }
    }

    @Override
    public void onEquip(Player player) {
        player.setMaxHealth(30);
        player.setHealth(player.getMaxHealth());
        player.getInventory().setHelmet(new ItemCreator(Material.IRON_HELMET).unbreakable(true).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).get());
        
        // Ajouter l'item "The Night" (os)
        ItemStack bone = new ItemCreator(Material.BONE).name("§cThe Night §8§l▪ §7Clic-droit").get();
        player.getInventory().addItem(bone);
    }

    public void usePower(KPlayer kPlayer) {
        use(kPlayer); // Appelle la méthode use() pour gérer le cooldown et exécuter le pouvoir
    }
}
