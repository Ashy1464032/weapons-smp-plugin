package me.ashy146.godWeapons.Events;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import me.ashy146.godWeapons.GodWeapons;
import me.ashy146.godWeapons.Utils.PlayerUtils;
import me.ashy146.godWeapons.Weapons.GodAxe;
import me.ashy146.godWeapons.Weapons.GodBow;
import me.ashy146.godWeapons.Weapons.GodCrossbow;
import me.ashy146.godWeapons.Weapons.GodMace;
import me.ashy146.godWeapons.Weapons.GodShield;
import me.ashy146.godWeapons.Weapons.GodSpear;
import me.ashy146.godWeapons.Weapons.GodSword;
import me.ashy146.godWeapons.Weapons.GodTrident;

public class WeaponListeners implements Listener{
	
	@EventHandler
	public void onTridentStrike(ProjectileHitEvent event) {
		GodTrident.useAbility(event);
	}
	
	@EventHandler
	public void onTridentUse(PlayerInteractEvent event) {
		GodTrident.activateAbility(event);
	}
	
	@EventHandler
	public void onMaceUse(PlayerInteractEvent event) {
		GodMace.useAbility(event);
	}
	
	@EventHandler
	public void onSpearUse(PlayerInteractEvent event) {
		GodSpear.activateAbility(event);
	}
	
	@EventHandler
	public void onSwordUse(PlayerInteractEvent event) {
		GodSword.activateAbility(event);
	}
	
	@EventHandler
	public void onGodSwordHit(EntityDamageByEntityEvent event) {
		GodSword.useAbility(event);
	}
	
	@EventHandler
	public void onAxeUse(PlayerInteractEvent event) {
		GodAxe.activateAbility(event);
	}
	
	@EventHandler
	public void onAxeSuperHit(EntityDamageByEntityEvent event) {
		GodAxe.useAbility(event);
	}
	

	@EventHandler
	public void onGodCrossbowHit(EntityDamageByEntityEvent event) {
		GodCrossbow.shieldDisable(event);
	}
	
	@EventHandler
	public void onCrossbowAbilityUse(PlayerInteractEvent event) {
		GodCrossbow.activateAbility(event);
	}

	@EventHandler
	public void onCrossbowReadySuperArrow(EntityShootBowEvent event) {
		GodCrossbow.useAbility(event);
	}
	
	@EventHandler
	public void resetWeaponAbilities(PlayerQuitEvent event) {
		GodBow.getActivePlayers().remove(event.getPlayer().getUniqueId());
		GodTrident.getActivePlayers().remove(event.getPlayer().getUniqueId());
		GodSword.getActivePlayers().remove(event.getPlayer().getUniqueId());
		GodAxe.getActivePlayers().remove(event.getPlayer().getUniqueId());
		GodCrossbow.getActivePlayers().remove(event.getPlayer().getUniqueId());
		
	}
	
	@EventHandler
	public void onGodBowInteract(PlayerInteractEvent event) {
	    GodBow.useItem(event);
	}

	@EventHandler
	public void onGodBowAnimation(PlayerAnimationEvent event) {
	    GodBow.activateAbility(event);
	}
	
	@EventHandler
	public void godShieldDamageCalculator(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player player)) {return;}
		if(!(player.getInventory().getItemInOffHand().getType() == Material.SHIELD)) {return;}
		
		ItemStack shield = player.getInventory().getItemInOffHand();
		if(!(shield.getItemMeta().getPersistentDataContainer().has(GodWeapons.getWeaponsIDNamespace()))) {return;}
		
		if(shield.getItemMeta().getPersistentDataContainer().get(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING) == "GodShield") {
			event.setDamage(event.getDamage() * 0.8);
		}
	}

	@EventHandler
	public void onPlayerCraft(CraftItemEvent event) {
	    ItemStack result = event.getRecipe().getResult();
	    if (result == null || result.getType() == Material.AIR) return;

	    ItemStack[] godItems = {
	        GodAxe.getItem(), GodBow.getItem(), GodCrossbow.getItem(),
	        GodMace.getItem(), GodShield.getItem(), GodSpear.getItem(),
	        GodSword.getItem(), GodTrident.getItem()
	    };

	    for (ItemStack item : godItems) {
	        if (item != null && item.isSimilar(result)) {
	            Player player = (Player) event.getWhoClicked();
	            
	            String location = "[" + Math.floor(player.getLocation().getX()) + "," + Math.floor(player.getLocation().getY()) + "," + Math.floor(player.getLocation().getZ()) + "] in " + player.getWorld().getEnvironment().toString();
	            
	            player.sendMessage("§6§lYou crafted a god item!");
	            Bukkit.broadcastMessage("§4§lA god weapon was crafted at: " + location);
	            
	            GodWeapons.getDatabase().addWeapon(player.getUniqueId(), result.getItemMeta().getPersistentDataContainer().get(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING));
	            
	            GodWeapons.getGodWeaponCrafters().add(player.getUniqueId());
	            GodWeapons.getScheduler().runTaskLater(GodWeapons.getInstance(), () -> {
	            	GodWeapons.getGodWeaponCrafters().remove(player.getUniqueId());
	            }, 72000);
	            //72000 = 60min
	            if(event.getRecipe() instanceof Keyed keyed) {
	            	Bukkit.removeRecipe(keyed.getKey());
	            }
	            break;
	        }
	    }
	}

}
