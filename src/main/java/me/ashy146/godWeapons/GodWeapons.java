package me.ashy146.godWeapons;

import me.ashy146.godWeapons.Commands.GodWeaponsCommand;
import me.ashy146.godWeapons.Database.CraftedItemsDatabase;
import me.ashy146.godWeapons.Events.WeaponListeners;
import me.ashy146.godWeapons.Recipes.GodRecipes;
import me.ashy146.godWeapons.Weapons.GodMace;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class GodWeapons extends JavaPlugin {
	private static GodWeapons instance;
	private static NamespacedKey WEAPONID_KEY;
	private static NamespacedKey damageResistanceKey;
	private static BukkitScheduler scheduler;
	private static Set<UUID> godWeaponCrafters = new HashSet<>();
	private static CraftedItemsDatabase craftedItemsDatabase;
	
    @Override
    public void onEnable() {
    	instance = this;
    	try {
    		
    		if(!getDataFolder().exists()) {
    			getDataFolder().mkdirs();
    		}
    		
    		craftedItemsDatabase = new CraftedItemsDatabase(getDataFolder().getAbsolutePath() + "/godweapons.db");
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		System.out.println("[Godweapons] Failed to create database");
    		Bukkit.getPluginManager().disablePlugin(this);
    	}
        getCommand("godweapons").setExecutor(new GodWeaponsCommand());
        getServer().getPluginManager().registerEvents(new WeaponListeners(), this);
        WEAPONID_KEY = new NamespacedKey(getInstance(), "GodWeaponsID");
        scheduler = this.getServer().getScheduler();
        GodRecipes.register();
        
        scheduler.runTaskTimer(this, () -> {
        	for (UUID playerID : godWeaponCrafters) {
        		Player player = Bukkit.getPlayer(playerID);
        		String location = "[" + Math.floor(player.getLocation().getX()) + "," + Math.floor(player.getLocation().getY()) + "," + Math.floor(player.getLocation().getZ()) + "] in " + player.getWorld().getEnvironment().toString();
        		Bukkit.broadcastMessage("§4§lA God weapon user is now at: " + location);
        	}
        }, 200, 18000);
    }

    @Override
    public void onDisable() {
    	try {
    		craftedItemsDatabase.closeConnection();
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    		System.out.println("[Godweapons] Failed to close database connection");
    	}
    }
    
    public static Set<UUID> getGodWeaponCrafters(){
    	return godWeaponCrafters;
    }
   
    public static NamespacedKey getWeaponsIDNamespace() {
    	return WEAPONID_KEY;
    }
    
    public static NamespacedKey getDamageResistanceKey() {
    	return damageResistanceKey;
    }
    
    public static GodWeapons getInstance() {
        return instance;
    }
    
    public static BukkitScheduler getScheduler() {
    	return scheduler;
    }
    
    public static CraftedItemsDatabase getDatabase() {
    	return craftedItemsDatabase;
    }
}
