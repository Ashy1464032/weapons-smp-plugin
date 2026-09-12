package me.ashy146.godWeapons.Weapons;

import me.ashy146.godWeapons.GodWeapons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GodShield {
    public static ItemStack getItem() {
        final ItemStack GodShield = new ItemStack(Material.SHIELD, 1);
        ItemMeta metaData = GodShield.getItemMeta();

        NamespacedKey key = new NamespacedKey("godweapons", "godshield");

        AttributeModifier modifer = new AttributeModifier(
                key,
                10.0,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.OFFHAND
        );
        
        metaData.setDisplayName("§l§6God §5Shield");
        metaData.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metaData.setLore(List.of(
        		"§n§l§6Divine §7Shield §5Aegis",
        		"§4§lAbility: §r§9Grants §c5♥ §9and a body of §7iron"
        		));
        
        if(metaData instanceof Damageable damageable) {
			damageable.setMaxDamage(2400);
		}
        
        metaData.getCustomModelDataComponent().setStrings(List.of("God Shield"));
        
        metaData.getPersistentDataContainer().set(GodWeapons.getWeaponsIDNamespace(), PersistentDataType.STRING, "GodShield");
        
        metaData.addAttributeModifier(Attribute.MAX_HEALTH, modifer);
        
        GodShield.setItemMeta(metaData);

        return GodShield;
    }
}
