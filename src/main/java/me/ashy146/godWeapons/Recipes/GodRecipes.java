package me.ashy146.godWeapons.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.ashy146.godWeapons.GodWeapons;
import me.ashy146.godWeapons.Weapons.GodAxe;
import me.ashy146.godWeapons.Weapons.GodBow;
import me.ashy146.godWeapons.Weapons.GodCrossbow;
import me.ashy146.godWeapons.Weapons.GodMace;
import me.ashy146.godWeapons.Weapons.GodShield;
import me.ashy146.godWeapons.Weapons.GodSpear;
import me.ashy146.godWeapons.Weapons.GodSword;
import me.ashy146.godWeapons.Weapons.GodTrident;

public class GodRecipes {
	public static void register() {
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodMace")) {
			addGodMaceRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodTrident")) {
			addGodTridentRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodCrossbow")) {
			addGodCrossbowRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodBow")) {
			addGodBowRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodSword")) {
			addGodSwordRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodShield")) {
			addGodShieldRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodSpear")) {
			addGodSpearRecipe();
		}
		if(!GodWeapons.getDatabase().hasWeaponBeenCrafted("GodAxe")) {
			addGodAxeRecipe();
		}
	}
	
	private static void addGodMaceRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godmacerecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodMace.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.PLAYER_HEAD);
		recipe.setIngredient('N', Material.NETHERITE_INGOT);
		recipe.setIngredient('R', Material.BREEZE_ROD);
		recipe.setIngredient('C', Material.HEAVY_CORE);
		
		Bukkit.addRecipe(recipe);
	}
	
	private static void addGodTridentRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godtridentrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodTrident.getItem().clone());
		recipe.shape("PNS","RCR","SNP");
		
		recipe.setIngredient('S', Material.NETHERITE_SCRAP);
		recipe.setIngredient('N', Material.IRON_NAUTILUS_ARMOR);
		recipe.setIngredient('R', Material.CONDUIT);
		recipe.setIngredient('C', Material.TRIDENT);
		recipe.setIngredient('P', Material.PLAYER_HEAD);
		
		Bukkit.addRecipe(recipe);
	}
	private static void addGodCrossbowRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godcrossbowrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodCrossbow.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.NETHERITE_SCRAP);
		recipe.setIngredient('N', Material.PLAYER_HEAD);
		recipe.setIngredient('R', Material.PHANTOM_MEMBRANE);
		recipe.setIngredient('C', Material.CROSSBOW);
		
		Bukkit.addRecipe(recipe);
	}
	private static void addGodBowRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godbowrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodBow.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.SPECTRAL_ARROW);
		recipe.setIngredient('N', Material.NETHERITE_SCRAP);
		recipe.setIngredient('R', Material.PLAYER_HEAD);
		recipe.setIngredient('C', Material.BOW);
		
		Bukkit.addRecipe(recipe);
	}
	
	private static void addGodSwordRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godswordrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodSword.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.PLAYER_HEAD);
		recipe.setIngredient('N', Material.BLAZE_ROD);
		recipe.setIngredient('R', Material.NETHERITE_INGOT);
		recipe.setIngredient('C', Material.NETHERITE_SWORD);
		
		Bukkit.addRecipe(recipe);
	}
	
	private static void addGodShieldRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godshieldrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodShield.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.PLAYER_HEAD);
		recipe.setIngredient('N', Material.NETHERITE_NAUTILUS_ARMOR);
		recipe.setIngredient('R', Material.TURTLE_HELMET);
		recipe.setIngredient('C', Material.SHIELD);
		
		Bukkit.addRecipe(recipe);
	}
	
	private static void addGodSpearRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godspearrecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodSpear.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.NETHERITE_SCRAP);
		recipe.setIngredient('N', Material.PLAYER_HEAD);
		recipe.setIngredient('R', Material.SUGAR);
		recipe.setIngredient('C', Material.NETHERITE_SPEAR);
		
		Bukkit.addRecipe(recipe);
	}
	private static void addGodAxeRecipe() {
		NamespacedKey key = new NamespacedKey(GodWeapons.getInstance(), "godaxerecipe");
		ShapedRecipe recipe = new ShapedRecipe(key, GodAxe.getItem().clone());
		recipe.shape("SNS","RCR","SNS");
		
		recipe.setIngredient('S', Material.NETHERITE_SCRAP);
		recipe.setIngredient('N', Material.PLAYER_HEAD);
		recipe.setIngredient('R', Material.BLAZE_ROD);
		recipe.setIngredient('C', Material.NETHERITE_AXE);
		
		Bukkit.addRecipe(recipe);
	}
}
