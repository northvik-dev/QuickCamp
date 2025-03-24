package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class LinkItem {
    QuickCamp plugin;

    public LinkItem(QuickCamp plugin){
        this.plugin = plugin;
    }

    public ItemStack getLinkedItem ( ItemStack item, String campName){
        if(item != null){
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta != null) {
                PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
                pdc.set(new NamespacedKey(plugin, "camp"), PersistentDataType.STRING, campName);
                item.setItemMeta(itemMeta);

            }
        }

        return item;
    }
}
