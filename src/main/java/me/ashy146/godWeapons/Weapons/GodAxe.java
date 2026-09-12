package me.ashy146.godWeapons.Weapons;

import java.security.PublicKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import me.ashy146.godWeapons.GodWeapons;
import me.ashy146.godWeapons.Utils.PlayerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class GodAxe {
	private static Set<UUID> activeGodAxePlayers = new HashSet<>();
	
	public static DamageType getLabrysCurse() {
		return Registry.DAMAGE_TYPE.get(new NamespacedKey("godweapons","curse_of_labrys"));
	}
		
	
	public static ItemStack getItem() {
		ItemStack godAxe = new ItemStack(Material.NETHERITE_AXE, 1);
		ItemMeta metaData = godAxe.getItemMeta();
		metaData.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		metaData.setDisplayName("§l§6God §5Axe");
		metaData.getCustomModelDataComponent().setStrings(List.of("God Axe"));
		
		metaData.setLore(List.of(
				"§n§l§6Divine §7Axe §5Labrys",
				"§9Ignores §call §9shields",
				"§l§3Ability: §r§6on next crit, stun enemy for 10 seconds and deal increased damage"
				));
		
		metaData.setDamageType(getLabrysCurse());
				
		metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodAxe");
		
		godAxe.setItemMeta(metaData);
		
		return godAxe;
	}
	
	public static void activateAbility(PlayerInteractEvent event) {
		if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {return;}
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItem();
		if(itemStack == null) {return;}
		if(!isGodAxe(itemStack)) {return;}

		if(activeGodAxePlayers.contains(player.getUniqueId())) {return;}
		
		if(player.hasCooldown(Material.NETHERITE_AXE)) {return;}
		
		activeGodAxePlayers.add(player.getUniqueId());
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cAbility Used"));
	}
	
	public static void useAbility(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) {return;}
		Player player = (Player) event.getDamager();
		Entity victim = event.getEntity();
		
		
		ItemStack weapon = player.getInventory().getItemInMainHand();
		if(!isGodAxe(weapon)) {return;}
		ItemMeta meta = weapon.getItemMeta();
		boolean canSuperCrit = activeGodAxePlayers.contains(player.getUniqueId());
		
		if(canSuperCrit && 	PlayerUtils.isCritical(player)) {
			event.setDamage(event.getDamage() * 1.2);
			if(victim instanceof Player bitch) {
				bitch.addPotionEffect(PotionEffectType.SLOWNESS.createEffect(200, 255));
			}
			activeGodAxePlayers.remove(player.getUniqueId());
			player.setCooldown(Material.NETHERITE_AXE, 2400);
		}
	}
	
	 public static Set<UUID> getActivePlayers() {
	        return activeGodAxePlayers;
	 }
	 private static boolean isGodAxe(ItemStack item) {

		 if (item == null || item.getType() != Material.NETHERITE_AXE) {
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

	     return "GodAxe".equals(id);
	 }

}
