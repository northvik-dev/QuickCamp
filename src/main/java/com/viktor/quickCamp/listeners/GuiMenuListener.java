package com.viktor.quickCamp.listeners;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.utils.CampGUI;
import com.viktor.quickCamp.utils.ConfigsInitialize;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
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
}

