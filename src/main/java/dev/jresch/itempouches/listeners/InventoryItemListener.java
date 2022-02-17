package dev.jresch.itempouches.listeners;

import de.tr7zw.nbtapi.NBTItem;
import dev.jresch.itempouches.FlatFile;
import dev.jresch.itempouches.items.Bag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryItemListener implements Listener {

    private final Plugin plugin;
    private final Map<UUID, UUID> uuidMap;

    public InventoryItemListener(Plugin plugin) {
        this.plugin = plugin;
        this.uuidMap = new HashMap<>();
    }

    @EventHandler
    public void onUseItem(PlayerInteractEvent event) {
        final var player = event.getPlayer();

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)
            || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            final var item = player.getInventory().getItemInMainHand();

            if (Bag.isEqual(item)) {
                final var inv = Bukkit.createInventory(player, 9, Component.text("Bag"));

                final var nbti = new NBTItem(item);
                final var uuid = nbti.getUUID("uuid");

                uuidMap.put(player.identity().uuid(), uuid);

                final ItemStack[] items = FlatFile.load(plugin.getDataFolder() + "/fileName" + uuid + ".extension");

                if(items != null && items.length > 0) {
                    inv.setContents(items);
                }

                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        final var title = (TextComponent) event.getView().title();

        if(title.content() == "Bag") {
            final var player = (Player) event.getPlayer();
            final var inventory = event.getInventory();

            var uuid = uuidMap.remove(player.identity().uuid());
            FlatFile.save(plugin.getDataFolder() + "/fileName" + uuid + ".extension", inventory.getStorageContents());
        }
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {
        final var recipe = event.getRecipe();
        final var item = recipe.getResult();

        if (Bag.isEqual(item)) {
            final var nbti = new NBTItem(item);
            nbti.setUUID("uuid", UUID.randomUUID());
            nbti.mergeNBT(item);

            event.getInventory().setResult(item);
        }
    }
}
