package dev.jresch.itempouches;

import dev.jresch.itempouches.items.Bag;
import dev.jresch.itempouches.listeners.InventoryItemListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InventoryItemListener(this), this);

        var bag = new Bag(this);
        bag.initializeItem();
    }

    @Override
    public void onDisable() {
    }
}

