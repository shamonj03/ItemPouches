package dev.jresch.itempouches.items;

import dev.jresch.itempouches.enchantments.StorageEnchantment;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Bag {
    public static final Material MATERIAL = Material.CARROT_ON_A_STICK;
    public static final int CUSTOM_MODEL_DATA = 6380001;

    private final Plugin plugin;

    public Bag(Plugin plugin) {
        this.plugin = plugin;
    }

    private ItemStack getItem() {
        var item = new ItemStack(MATERIAL);

        var enchantKey = new NamespacedKey(plugin, StorageEnchantment.NAMESPACED_KEY);
        item.addEnchantment(new StorageEnchantment(enchantKey), 1);

        var meta = item.getItemMeta();
        meta.setCustomModelData(CUSTOM_MODEL_DATA);
        meta.displayName(Component.text("Bag"));

        item.setItemMeta(meta);
        return item;
    }

    public void initializeItem() {
        var key = new NamespacedKey(plugin, "item_pouch");

        var recipe = new ShapedRecipe(key, getItem());
        recipe.shape( "LLL", "LCL", "LLL");
        recipe.setIngredient('C', Material.CHEST);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);
    }

    public static boolean isEqual(ItemStack item) {
        return item != null && item.getType() == MATERIAL && item.getItemMeta().getCustomModelData() == CUSTOM_MODEL_DATA;
    }
}
