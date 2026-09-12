package me.ashy146.godWeapons.Weapons;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.ashy146.godWeapons.GodWeapons;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodCrossbow {
	private static Set<UUID> activeGodCrossbowPlayers = new HashSet<>();
	public static ItemStack getItem() {
		ItemStack godCrossbow = new ItemStack(Material.CROSSBOW);
		ItemMeta metaData = godCrossbow.getItemMeta();
		metaData.setDisplayName("§l§6God §5Crossbow");
        metaData.setLore(List.of(
        		"§n§l§6Divine §7Crossbow §5Apollo",
        		"§9Stuns §cAll §9shield hit with its mighty arrows",
        		"§l§3Ability: §r§6Shoots fireworks with the reload speed of a harp crossbow"
        		));
        
        if(metaData instanceof Damageable damageable) {
			damageable.setMaxDamage(2100);
		}
        
        metaData.getCustomModelDataComponent().setStrings(List.of("God Crossbow"));
		
		godCrossbow.addUnsafeEnchantment(Enchantment.PIERCING, 4);		
		
		metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodCrossbow");
		
		godCrossbow.setItemMeta(metaData);
		
		
		return godCrossbow;
	}
	
	public static void shieldDisable(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Arrow arrow)) {return;}
		if(!(event.getEntity() instanceof Player victim)) {return;}
		
		if(!victim.isBlocking()) {return;}
		
		Entity shooter = (Entity) arrow.getShooter();
		if(shooter instanceof Player apollo) {
			ItemStack mainHandItemStack = apollo.getInventory().getItemInMainHand();
			if(mainHandItemStack.getItemMeta().getPersistentDataContainer().get(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING) == "GodCrossbow"){
				victim.setCooldown(Material.SHIELD, 100);
			}
		}
	}
	
	public static void activateAbility(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItem();
		if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {return;}
		if(!player.isSneaking()) {return;}
		
		if(itemStack == null) {return;}
		
		if(!isGodCrossbow(itemStack)) {return;}
		
		if(activeGodCrossbowPlayers.contains(player.getUniqueId())) {return;}
		
		if(player.hasCooldown(Material.CROSSBOW)) {return;}
		
		ItemMeta metaData = itemStack.getItemMeta();
		
		activeGodCrossbowPlayers.add(player.getUniqueId());
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
		
		final int quickChargeLvl; 
		
		if(metaData.hasEnchant(Enchantment.QUICK_CHARGE)) {
			quickChargeLvl = metaData.getEnchantLevel(Enchantment.QUICK_CHARGE);
			metaData.addEnchant(Enchantment.QUICK_CHARGE, 4, true);
		} else {
			quickChargeLvl = 0;
			metaData.addEnchant(Enchantment.QUICK_CHARGE, 4, true);
		}
		
		itemStack.setItemMeta(metaData);
		
		GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
			metaData.removeEnchant(Enchantment.QUICK_CHARGE);
			if(quickChargeLvl > 0) {
				metaData.addEnchant(Enchantment.QUICK_CHARGE, quickChargeLvl, true);
			}
			player.setCooldown(Material.CROSSBOW, 3600);
			activeGodCrossbowPlayers.remove(player.getUniqueId());
		}, 300);
	}
	
	public static void useAbility(EntityShootBowEvent event) {
		if(!(event.getEntity() instanceof Player player)) {return;}
		ItemStack heldItem = event.getBow();
		if(isGodCrossbow(heldItem)) {
			ItemMeta metaData = heldItem.getItemMeta();

			
			Boolean canUseSlowStrike = activeGodCrossbowPlayers.contains(player.getUniqueId());
			
			if(canUseSlowStrike) {
				Arrow tippedArrow = player.launchProjectile(Arrow.class);
				tippedArrow.setBasePotionType(PotionType.LONG_SLOW_FALLING);
				tippedArrow.setVelocity(event.getProjectile().getVelocity());
				event.setProjectile(tippedArrow);
			}
		}
	}
	
	 public static Set<UUID> getActivePlayers() {
	        return activeGodCrossbowPlayers;
	 }
	 private static boolean isGodCrossbow(ItemStack item) {

	        if (item == null || item.getType() != Material.CROSSBOW) {
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

	        return "GodCrossbow".equals(id);
	    }
}
