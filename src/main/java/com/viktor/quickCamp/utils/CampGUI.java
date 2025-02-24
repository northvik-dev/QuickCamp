package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
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
        //Clear button
        ItemStack clearButton = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta clearButtonMeta = clearButton.getItemMeta();
        clearButtonMeta.setItemName("Clear");
        clearButton.setItemMeta(clearButtonMeta);
        //Info button
        ItemStack infoButton = new ItemStack(Material.YELLOW_CANDLE);
        ItemMeta infoButtonMeta = infoButton.getItemMeta();
        infoButtonMeta.setItemName("Info");
        infoButtonMeta.setLore(Arrays.asList("-Some info", "-Info again"));
        infoButton.setItemMeta(infoButtonMeta);

        List<Integer> nonUsableSlots = new ArrayList<>(ci.getNonUsableSlotsIndexes());
        for (Integer slot:nonUsableSlots ){
            campGui.setItem(slot,blackGlass );
        }
        HashMap<Integer,String> campBlueprint = new HashMap<>(ci.getCampBlueprint());
        for (var entry: campBlueprint.entrySet()){
            String matString = entry.getValue();

            ItemStack item = new ItemStack(Material.valueOf(matString));
            campGui.setItem(entry.getKey(),item);
        }


        campGui.setItem(ci.saveButton,saveButton);
        campGui.setItem(ci.closeButton,closeButton);
        campGui.setItem(ci.clearButton,clearButton);
        campGui.setItem(ci.infoButton,infoButton);

        player.openInventory(campGui);
    }

}
