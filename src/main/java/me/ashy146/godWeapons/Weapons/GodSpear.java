package me.ashy146.godWeapons.Weapons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import me.ashy146.godWeapons.GodWeapons;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodSpear {
	public static ItemStack getItem() {
		ItemStack godSpear = new ItemStack(Material.NETHERITE_SPEAR, 1);
		ItemMeta meta = godSpear.getItemMeta();
		
		meta.setDisplayName("§l§6God §5Spear");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		meta.setLore(List.of(
				"§n§l§6Divine §7Spear §5Longinus",
				"§9Grants §cGreat §9Speed",
				"§l§4Right Click Ability: §r§64 Block reach even when not using this spear"
				));
		
		meta.getCustomModelDataComponent().setStrings(List.of("God Spear"));
		
        NamespacedKey AttributeKey = new NamespacedKey(GodWeapons.getInstance(), "godspearspeed");
        
        AttributeModifier increasedSpeed = new AttributeModifier(
				AttributeKey,
				0.4,
				Operation.ADD_SCALAR,
				EquipmentSlotGroup.ANY
		);
        meta.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodSpear");
        if(!meta.hasAttributeModifiers()) {
        	meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, increasedSpeed);
        }
        
        godSpear.setItemMeta(meta);
        
        
		
		return godSpear;
	}
	
	public static void activateAbility(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.isSneaking()) {return;}
		ItemStack itemStack = event.getItem();
		if(itemStack == null || itemStack.getType() != Material.NETHERITE_SPEAR) {return;}
		
		if(player.hasCooldown(Material.NETHERITE_SPEAR)) {return;}
		if(!(itemStack.getItemMeta().getPersistentDataContainer().get(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING) == "GodSpear")) {return;}
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godspearrange");
		
		AttributeModifier increasedRange = new AttributeModifier(
				key,
				1,
				Operation.ADD_NUMBER,
				EquipmentSlotGroup.ANY
		);
		
		if(player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).getModifiers().contains(increasedRange)) {return;}
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
		
		player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).addModifier(increasedRange);
		
		GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
			player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).removeModifier(increasedRange);
        	player.setCooldown(itemStack, 3600);
		}, 600);
	}
}
