package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

public class CampGUI {
    public void gui (Player player, QuickCamp plugin){
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

        Inventory campGui = Bukkit.createInventory(player, 54, "Camp GUI");

        //non usable area
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setItemName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        // Save button
        ItemStack saveButton = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta saveButtonMeta = saveButton.getItemMeta();
        saveButtonMeta.setItemName("Save setup");
        saveButton.setItemMeta(saveButtonMeta);

         // Close button
        ItemStack closeButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.setItemName("Close");
        closeButton.setItemMeta(closeButtonMeta);

        List<Integer> nonUsableSlots = new ArrayList<>(ci.getNonUsableSlotsIndexes());
        for (Integer slot:nonUsableSlots ){
            campGui.setItem(slot,blackGlass );
        }

        campGui.setItem(ci.saveButton,saveButton);
        campGui.setItem(ci.closeButton,closeButton);

        player.openInventory(campGui);
    }

}
