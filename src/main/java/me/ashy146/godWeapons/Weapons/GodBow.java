package me.ashy146.godWeapons.Weapons;

import java.security.PrivateKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import me.ashy146.godWeapons.GodWeapons;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodBow {
	
private static Set<UUID> activeGodBowPlayers = new HashSet<>();

	
	public static ItemStack getItem() {
		ItemStack godBow = new ItemStack(Material.BOW);
		ItemMeta metaData = godBow.getItemMeta();
		metaData.setDisplayName("§l§6God §5Bow");
		
		metaData.setLore(List.of(
				"§n§l§6Divine §7Bow §eArtemis",
				"§l§4Right Click Ability: §r§6Shoots arrows of §elight temporarily with left click"
				));
		
		if(metaData instanceof Damageable damageable) {
			damageable.setMaxDamage(2100);
		}
		
		metaData.getCustomModelDataComponent().setStrings(List.of("God Bow"));
		
		godBow.addUnsafeEnchantment(Enchantment.POWER, 6);
		
		
		metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodBow");
		
		godBow.setItemMeta(metaData);
		
		
		return godBow;
	}
	
    public static void activateAbility(PlayerAnimationEvent event) {

        Player player = event.getPlayer();

        if (event.getAnimationType() != PlayerAnimationType.ARM_SWING) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!isGodBow(item)) {
            return;
        }

        UUID uuid = player.getUniqueId();

        if (activeGodBowPlayers.contains(uuid)) {
            return;
        }

        if (player.hasCooldown(Material.BOW)) {
            return;
        }
        
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));

        activeGodBowPlayers.add(uuid);
        
        GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
        	activeGodBowPlayers.remove(uuid);

            player.setCooldown(Material.BOW, 3600);
        }, 300);
    }
	
    private static boolean isGodBow(ItemStack item) {

        if (item == null || item.getType() != Material.BOW) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        String id = meta.getPersistentDataContainer().get(
            GodWeapons.getWeaponsIDNamespace(),
            PersistentDataType.STRING
        );

        return "GodBow".equals(id);
    }

    public static Set<UUID> getActivePlayers() {
        return activeGodBowPlayers;
    }
    
    public static void useItem(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack bow = event.getItem();

        if (bow == null) {
            return;
        }

        if (!isGodBow(bow)) {
            return;
        }

        UUID uuid = player.getUniqueId();

        if (!activeGodBowPlayers.contains(uuid)) {
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            return;
        }

        event.setCancelled(true);

        useAbility(player, bow);
    }
    
	private static void useAbility(Player player, ItemStack bow) {
        int powerLevel = bow.getEnchantmentLevel(Enchantment.POWER);
        
        SpectralArrow arrow = player.launchProjectile(SpectralArrow.class);
        double damage = 2.0 + (0.5 * (powerLevel + 1));
        arrow.setDamage(damage);
        arrow.setPickupStatus(PickupStatus.CREATIVE_ONLY);
        arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.0));
	}
	
}
