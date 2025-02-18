package com.viktor.quickCamp.listeners;

import com.viktor.quickCamp.utils.BlocksLocationList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CampGUI {

    public void gui (Player player){
        Inventory campGui = Bukkit.createInventory(player, 54, "Camp GUI");

        ItemStack glass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        for (int i = 0; i < campGui.getSize(); i++) {
            campGui.setItem(i, glass);
        }

        player.openInventory(campGui);
    }

}
