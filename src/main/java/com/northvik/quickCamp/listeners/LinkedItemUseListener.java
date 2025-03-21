package com.northvik.quickCamp.listeners;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.commands.CampPlace;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LinkItem;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class LinkedItemUseListener implements Listener {

    QuickCamp plugin;
    LinkItem linkItem;
    ConfigsInitialize ci;
    CampPlace campPlace;
    public LinkedItemUseListener(QuickCamp plugin) {
        this.plugin = plugin;
        this.linkItem = new LinkItem(plugin);
        this.ci = new ConfigsInitialize(plugin);
    }

    @EventHandler
    public void onLinkedItemUseEvent(BlockPlaceEvent e) {
        campPlace = new CampPlace(e.getPlayer(), plugin, ci);
        ItemStack itemInHand = e.getItemInHand();
        if (itemInHand == null || !itemInHand.hasItemMeta()) return;

        // Iterate through template names
        for (String name : ci.getTemplateNames()) {
            ItemMeta meta = itemInHand.getItemMeta();

            if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(plugin, name), PersistentDataType.BOOLEAN)) {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    campPlace.campPlace(name); // Place the camp after the block is in the world
                });
                e.setCancelled(true);
                return; // Stop further iteration once matched
            }
        }
    }
}
