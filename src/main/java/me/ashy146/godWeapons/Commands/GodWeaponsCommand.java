package me.ashy146.godWeapons.Commands;


import me.ashy146.godWeapons.Weapons.GodAxe;
import me.ashy146.godWeapons.Weapons.GodBow;
import me.ashy146.godWeapons.Weapons.GodCrossbow;
import me.ashy146.godWeapons.Weapons.GodMace;
import me.ashy146.godWeapons.Weapons.GodShield;
import me.ashy146.godWeapons.Weapons.GodSpear;
import me.ashy146.godWeapons.Weapons.GodSword;
import me.ashy146.godWeapons.Weapons.GodTrident;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class GodWeaponsCommand implements TabExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player player) {
			switch (args[0]) {
			case "GShield": {
				player.getInventory().addItem(GodShield.getItem());
				break;
			}
			case "GSword": {
				player.getInventory().addItem(GodSword.getItem());
				break;
			}
			case "GAxe": {
				player.getInventory().addItem(GodAxe.getItem());
				break;
			}
			case "GMace": {
				player.getInventory().addItem(GodMace.getItem());
				break;
			}
			case "GSpear": {
				player.getInventory().addItem(GodSpear.getItem());
				break;
			}
			case "GTrident": {
				player.getInventory().addItem(GodTrident.getItem());
				break;
			}
			case "GBow": {
				player.getInventory().addItem(GodBow.getItem());
				break;
			}
			case "GCrossbow": {
				player.getInventory().addItem(GodCrossbow.getItem());
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + args[0]);
			}
		}
		
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return List.of(
                "GShield",
                "GSword",
                "GAxe",
                "GMace",
                "GSpear",
                "GTrident",
                "GCrossbow",
                "GBow"
        );
	}
}
