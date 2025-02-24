package com.viktor.quickCamp.listeners;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuiMenuListener implements Listener {

    QuickCamp plugin;


    public GuiMenuListener(QuickCamp plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e ){

        Player player = (Player) e.getWhoClicked();
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        YamlConfiguration config = ci.getYmlConfig();
        File file = ci.getFile();

        if (e.getView().getTitle().equals("Camp GUI")){
            List<Integer> nonUsableSlots = new ArrayList<>(ci.getNonUsableSlotsIndexes());
            List<Integer> placingSlots = new ArrayList<>(ci.getSlotsIndexes());

            //non usable slots area
            for (Integer slot : nonUsableSlots){
                if (e.getClick().isMouseClick() && e.getRawSlot()==slot ){
                    e.setCancelled(true);
                }
            }

            //blueprint area
            for (Integer slot : placingSlots){
                if (e.getClick().isMouseClick() && e.getRawSlot()==slot ){

                }
            }

           //save button function
           if (e.getRawSlot()== ci.getSaveButton()){
               saveBlueprint(ci, config, file, e.getInventory());
               player.sendMessage("Blueprint is saved into configs!");
               e.setCancelled(true);
           }

           // close button function
           if (e.getRawSlot()== ci.getCloseButton()){
               player.closeInventory();
               e.setCancelled(true);
           }
            // clear button function
            if (e.getRawSlot()== ci.getClearButton()){
                clearSlots(e.getInventory(), ci);
                config.set("CampBlueprint", null);
                saveConfig(config,file);
                e.setCancelled(true);
            }
            // info button function
            if (e.getRawSlot()== ci.getInfoButton()){
                e.setCancelled(true);
            }
        }
    }

    public void saveBlueprint(ConfigsInitialize ci, YamlConfiguration config, File file, Inventory inventory)
    {
        List<Integer> placingSlots = new ArrayList<>(ci.getSlotsIndexes());
        for ( Integer slot : placingSlots) {
           if (!isSlotEmpty(inventory, slot)){
               config.set("CampBlueprint." + slot,inventory.getItem(slot).getType().name());
           }
           saveConfig(config,file);
       }
    }

    public void clearSlots(Inventory inventory, ConfigsInitialize ci){
        List<Integer> placingSlots = new ArrayList<>(ci.getSlotsIndexes());
        for ( Integer slot : placingSlots) {
            if (!isSlotEmpty(inventory, slot)){
                inventory.removeItem(inventory.getItem(slot));
            }
        }
    }

    public void saveConfig(YamlConfiguration config, File file){
        try {
            config.save(file);
        } catch (Exception ex) {
            Bukkit.getServer().getConsoleSender().sendMessage("Couldn't save configs");
            throw new RuntimeException(ex);
        }
    }

    public boolean isSlotEmpty(Inventory inv, Integer slot){
        ItemStack item = inv.getItem(slot);

        return (item == null || item.getType()==Material.AIR);
    }
}

