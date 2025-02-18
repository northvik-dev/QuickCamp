package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CampGUI {

    public void gui (Player player, QuickCamp plugin){
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

        Inventory campGui = Bukkit.createInventory(player, 54, "Camp GUI");
        ItemStack glass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);

        List<Integer> slots = new ArrayList<>(ci.getSlotsIndexes());

        for (Integer slot : slots) {
            campGui.setItem(slot, glass);
        }

        player.openInventory(campGui);
    }

}
