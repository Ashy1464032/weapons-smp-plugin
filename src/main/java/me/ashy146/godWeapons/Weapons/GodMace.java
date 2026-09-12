package me.ashy146.godWeapons.Weapons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.ashy146.godWeapons.GodWeapons;
import me.ashy146.godWeapons.Utils.PlayerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GodMace {
	public static ItemStack getItem() {
		ItemStack godMace = new ItemStack(Material.MACE, 1);
		ItemMeta metaData = godMace.getItemMeta();
		
		metaData.setDisplayName("§l§6God §5Mace");
		metaData.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metaData.setLore(List.of(
				"§n§l§6Divine §7Mace §5Gada",
				"§9Smashes §cEnemies §9with extreme force",
				"§l§3Ability: §r§6Launches its user diagnolly forward"
			));

        metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodMace");
        
        
        
        metaData.getCustomModelDataComponent().setStrings(List.of("God Mace"));
		
        godMace.setItemMeta(metaData);
		return godMace;
	}

	public static void useAbility(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItem();
		if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {return;}
		if(itemStack == null) {return;}
		if(player.hasCooldown(Material.MACE)) {return;}
		if(!isGodMace(itemStack)) {return;}
		event.setCancelled(true);
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
		player.setVelocity(PlayerUtils.getLookVector(player, 2f));
		player.setCooldown(Material.MACE, 600);
	}
	
	 private static boolean isGodMace(ItemStack item) {

		 if (item == null || item.getType() != Material.MACE) {
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
	     return "GodMace".equals(id);
	 }
}

