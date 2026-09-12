package me.ashy146.godWeapons.Utils;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerUtils {
	public static Vector getLookVector(Player player, float mult) {
	    
	    float yawRad = (float) Math.toRadians(player.getLocation().getYaw());
	    float pitchRad = (float) Math.toRadians(player.getLocation().getPitch());
	    
	    float x = (float) ((float) -Math.sin(yawRad) * Math.cos(pitchRad));
	    float z = (float) ((float) Math.cos(yawRad) * Math.cos(pitchRad));

	    return new Vector(x * mult, mult, z * mult);
	}
	
	public static Boolean isCritical(Player player) {
		   return player.getFallDistance() > 0.0F
		            && !player.isOnGround()
		            && !player.isInsideVehicle()
		            && !player.isSprinting()
		            && !player.isSwimming()
		            && player.getAttackCooldown() > 0.9F;
	}
}
