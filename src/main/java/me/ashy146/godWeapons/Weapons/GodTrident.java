package me.ashy146.godWeapons.Weapons;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;


import me.ashy146.godWeapons.GodWeapons;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodTrident {
	
	private static Set<UUID> activeGodTridentPlayers = new HashSet<>();
	
	public static ItemStack getItem() {
		ItemStack godTrident = new ItemStack(Material.TRIDENT, 1);
		ItemMeta metaData = godTrident.getItemMeta();
		
		metaData.setDisplayName("§l§6God §5Trident");
        
		metaData.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        
		metaData.getCustomModelDataComponent().setStrings(List.of("God Trident"));
		
		if(metaData instanceof Damageable damageable) {
			damageable.setMaxDamage(2100);
		}
		
		metaData.setLore(List.of(
				"§n§l§6Divine §7Trident §5Poseidon",
				"§9Grants The §cBlessing §9of The Sea",
				"§l§3Ability: §r§6Ensures all who are struke by this weapon shall face the wrath of olympus"
				));
		
        
        
        metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodTrident");
        
        NamespacedKey waterspeedKey = new NamespacedKey(GodWeapons.getInstance(), "godtridentwaterspeed");
        NamespacedKey waterbreathingKey = new NamespacedKey(GodWeapons.getInstance(), "godtridentwaterbreathing");
       
        AttributeModifier increasedWaterSpeed = new AttributeModifier(
				waterspeedKey,
				3.0,
				Operation.ADD_SCALAR,
				EquipmentSlotGroup.MAINHAND
		);
        AttributeModifier increasedWaterBreathing = new AttributeModifier(
				waterbreathingKey,
				20,
				Operation.ADD_NUMBER,
				EquipmentSlotGroup.ANY
		);
        
        metaData.addAttributeModifier(Attribute.WATER_MOVEMENT_EFFICIENCY, increasedWaterSpeed);
        metaData.addAttributeModifier(Attribute.OXYGEN_BONUS, increasedWaterBreathing);
        
        godTrident.setItemMeta(metaData);
		
		
		return godTrident.clone();
	}
	
	public static Set<UUID> getActivePlayers() {
        return activeGodTridentPlayers;
    }
	
	public static void activateAbility(PlayerInteractEvent event) {
		if(!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {return;}
		if(!event.getPlayer().isSneaking()) {return;}
		
		Player player = event.getPlayer();
		ItemStack Trident = event.getItem();
		
		if(Trident == null) {return;}
		
		if(!isGodTrident(Trident)) {return;}
		UUID playerUUID = player.getUniqueId();
		
		if (activeGodTridentPlayers.contains(playerUUID)) {
            return;
        }
		
		if(player.hasCooldown(Material.TRIDENT)) {return;}
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
		
		activeGodTridentPlayers.add(playerUUID);
		
		GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
			activeGodTridentPlayers.remove(playerUUID);
			player.setCooldown(Material.TRIDENT, 6000);
		}, 600);
		
	}
	
	public static void useAbility(ProjectileHitEvent event) {
		if(!(event.getEntity() instanceof Trident trident)) {return;}
		
		boolean canGodStrike = false;
		
		if(event.getEntity().getShooter() instanceof Player striker) {
			canGodStrike = activeGodTridentPlayers.contains(striker.getUniqueId());
		}
		
		
		
		
		if(canGodStrike) {
			Location targetLocation = trident.getLocation();
			
			targetLocation.getWorld().strikeLightning(targetLocation);
		}
	}
	
    private static boolean isGodTrident(ItemStack item) {

        if (item == null || item.getType() != Material.TRIDENT) {
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

        return "GodTrident".equals(id);
    }
}
