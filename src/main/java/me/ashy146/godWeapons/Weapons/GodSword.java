package me.ashy146.godWeapons.Weapons;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import me.ashy146.godWeapons.GodWeapons;
import me.ashy146.godWeapons.Utils.PlayerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodSword {
	
	private static Set<UUID> activeGodSwordPlayers = new HashSet<>();
	
	public static ItemStack getItem() {
		ItemStack godSword = new ItemStack(Material.NETHERITE_SWORD, 1);
		
		NamespacedKey DamageKey = new NamespacedKey(GodWeapons.getInstance(), "godsworddamage");
		NamespacedKey SpeedKey = new NamespacedKey(GodWeapons.getInstance(), "godswordspeed");
		
		AttributeModifier damageModifer = new AttributeModifier(
                DamageKey,
                10.0,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
        );
		
		AttributeModifier speedModifer = new AttributeModifier(
                SpeedKey,
                -2.4000000953674316,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
        );
		
		ItemMeta metaData = godSword.getItemMeta();
		metaData.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		metaData.getCustomModelDataComponent().setStrings(List.of("God Sword"));
		metaData.setLore(List.of(
				"§n§l§6Divine §7Blade §5Gram",
				"§9Deals §c10 §9attack damage",
				"§l§3Ability: §r§6temporailly makes all attacks crit"
				));
		
		metaData.setDisplayName("§l§6God §5Sword");
		
		metaData.addAttributeModifier(Attribute.ATTACK_DAMAGE, damageModifer);
		metaData.addAttributeModifier(Attribute.ATTACK_SPEED, speedModifer);
		
		
		
		metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodSword");
		godSword.setItemMeta(metaData);
		
		return godSword.clone();
	}
	
	public static void activateAbility(PlayerInteractEvent event) {
		if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {return;}
		
		Player player = event.getPlayer();
		ItemStack Sword = event.getItem();
		
		if(Sword == null) {return;}
		
		if(!isGodSword(Sword)) {return;}
		UUID playerUUID = player.getUniqueId();
		
		if (activeGodSwordPlayers.contains(playerUUID)) {
            return;
        }
		
		if(player.hasCooldown(Material.NETHERITE_SWORD)) {return;}
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
		
		activeGodSwordPlayers.add(playerUUID);
		
		GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
			activeGodSwordPlayers.remove(playerUUID);
			player.setCooldown(Material.NETHERITE_SWORD, 3600);
		}, 300);
		
	}
	
	public static void useAbility(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) {return;}
		Player player = (Player) event.getDamager();
		
		ItemStack weapon = player.getInventory().getItemInMainHand();
		ItemMeta meta = weapon.getItemMeta();
		
		if(!isGodSword(weapon)) {return;}
		
		boolean canAutoCrit = activeGodSwordPlayers.contains(player.getUniqueId());
		
		if(canAutoCrit && !PlayerUtils.isCritical(player)) {
			event.setDamage(event.getDamage() * 1.5);
			event.getDamager().getWorld().spawnParticle(Particle.CRIT, event.getDamager().getLocation(), 10);
		}
	}
	
	 public static Set<UUID> getActivePlayers() {
	        return activeGodSwordPlayers;
	 }
	 
	 private static boolean isGodSword(ItemStack item) {

		 if (item == null || item.getType() != Material.NETHERITE_SWORD) {
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
	     return "GodSword".equals(id);
	 }
}
