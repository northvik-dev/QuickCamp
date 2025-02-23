package com.viktor.quickCamp.listeners;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.CampGUI;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiMenuListener implements Listener {

    QuickCamp plugin;


    public GuiMenuListener(QuickCamp plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e ){

        Player player = (Player) e.getWhoClicked();
        ConfigsInitialize ci = new ConfigsInitialize(plugin);

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
               player.sendMessage("Blueprint is saved into configs!");
               e.setCancelled(true);
           }

           // close button function
           if (e.getRawSlot()== ci.getCloseButton()){
               player.closeInventory();
               e.setCancelled(true);
           }
        }
    }

    @EventHandler
    public void onClose (InventoryCloseEvent e){

        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        YamlConfiguration config = ci.getYmlConfig();
        config.set("CampBlueprint", null);

        File file = ci.getFile();
        List<Integer> placingSlots = new ArrayList<>(ci.getSlotsIndexes());


        for ( Integer slot : placingSlots) {
           if (!isSlotEmpty(e.getInventory(), slot)){
               config.set("CampBlueprint." + slot, e.getInventory().getItem(slot).getType().name());
               //e.getPlayer().sendMessage("Slot " + slot + " is contains: " + e.getInventory().getItem(slot) );
           }
       }

       try {
           config.save(file);
       } catch (Exception ex) {
           e.getPlayer().sendMessage("Couldn't save configs");
           throw new RuntimeException(ex);
       }


    }
    public boolean isSlotEmpty(Inventory inv, Integer slot){
        ItemStack item = inv.getItem(slot);

        return (item == null || item.getType()==Material.AIR);
    }
}

