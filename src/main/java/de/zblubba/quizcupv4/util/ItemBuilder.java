package de.zblubba.quizcupv4.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }
    public ItemBuilder setLocalizedName(String localizedName) {
        itemMeta.setLocalizedName(localizedName);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean bool) {
        itemMeta.setUnbreakable(bool);
        return this;
    }
    public ItemBuilder addFlag(ItemFlag... flag) {
        itemMeta.addItemFlags(flag);
        return this;
    }
    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }
    public ItemBuilder addEnchant(Enchantment enchantment, int tier) {
        itemMeta.addEnchant(enchantment, tier, true);
        return this;
    }
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}