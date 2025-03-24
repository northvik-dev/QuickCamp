package com.northvik.quickCamp.listeners;

import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.commands.CampPlace;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.LinkItem;
import com.sk89q.worldguard.bukkit.event.inventory.UseItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class LinkedItemUseListener implements Listener {

    QuickCamp plugin;
    LinkItem linkItem;
    ConfigsInitialize ci;
    CampPlace campPlace;
    NamespacedKey campKey;
    public LinkedItemUseListener(QuickCamp plugin) {
        this.plugin = plugin;
        this.linkItem = new LinkItem(plugin);
        this.ci = new ConfigsInitialize(plugin);
        this.campKey = new NamespacedKey(plugin,"camp");
    }

    @EventHandler
    public void onLinkedBlockUse(BlockPlaceEvent e) {
        place(e.getPlayer(),e.getItemInHand(),e);
    }
    @EventHandler
    public void onLinkedFrameUseEvent (HangingPlaceEvent e){

        place(e.getPlayer(), e.getItemStack(), e);
    }

    public void place(Player player, ItemStack item, Cancellable cancellable){
        campPlace = new CampPlace(player, plugin, ci);
        if (item == null || !item.hasItemMeta()) return;

        // Iterate through template names
        for (String name : ci.getTemplateNames()) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null && meta.getPersistentDataContainer().has(campKey, PersistentDataType.STRING)) {
                if(meta.getPersistentDataContainer().get(campKey, PersistentDataType.STRING).equals(name)) {
                    plugin.getServer().getScheduler().runTask(plugin, () -> {
                        campPlace.campPlace(name); // Place the camp after the block is in the world
                    });
                    cancellable.setCancelled(true);
                    return; // Stop further iteration once matched
                }
            }
        }
    }
}
