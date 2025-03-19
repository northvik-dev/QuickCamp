package com.northvik.quickCamp.listeners;

import com.northvik.quickCamp.GUI.MainMenu;
import com.northvik.quickCamp.GUI.SavedTemplatesMenu;
import com.northvik.quickCamp.GUI.TemplateMenu;
import com.northvik.quickCamp.QuickCamp;
import com.northvik.quickCamp.managers.ConfigsInitialize;
import com.northvik.quickCamp.managers.GuiCustomSize;
import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiMenuListener implements Listener {

    QuickCamp plugin;
    final HashMap<Player, Boolean> waitingForInput = new HashMap<>();
    TemplateMenu templateMenu;
    SavedTemplatesMenu savedTemplatesMenu = new SavedTemplatesMenu();
    MainMenu mainMenu = new MainMenu();
    String campName;
    public GuiMenuListener(QuickCamp plugin){
        this.plugin = plugin;
        this.templateMenu = new TemplateMenu(plugin);
    }
    int choiceSize = 1;

    GuiButtonIndexes gbi = new GuiButtonIndexes();
    GuiCustomSize gcs = new GuiCustomSize();

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if (e.getView().getTitle().equals("Template Editor")) {

            gcs.convertSlotsToInt(choiceSize);

            List<Integer> nonUsableSlots = new ArrayList<>(gcs.getNonUsableSlotsIndexes());
            Bukkit.getConsoleSender().sendMessage("nonUsableSlots " + nonUsableSlots);

            e.getInventory().setItem(gbi.getSizeButton(), templateMenu.getSizeButton(choiceSize));

            for (Integer slot : nonUsableSlots) {
                e.getInventory().setItem(slot, templateMenu.getNonUsableItem());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e ){

        Player player = (Player) e.getWhoClicked();
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        YamlConfiguration config = ci.getYmlConfig();
        File file = ci.getFile();


//// MAIN MENU
        if (e.getView().getTitle().equals("QuickCamp menu")){
            for (int i = 0; i < e.getInventory().getSize(); i++){
                if (e.getClick().isMouseClick() && e.getRawSlot()==i ){
                    e.setCancelled(true);
                }
            }
            if (e.getRawSlot()== gbi.getCreateTemplate()){
                startChat(player);
                player.closeInventory();
                e.setCancelled(true);
            }
            if (e.getRawSlot()== gbi.getLoadTemplates()){
                savedTemplatesMenu.menu(player, plugin);
                e.setCancelled(true);
            }
            if (e.getRawSlot()== gbi.getCloseButton()){
                player.closeInventory();
                e.setCancelled(true);
            }
        }
/////// SAVED TEMPLATES
        if (e.getView().getTitle().equals("Saved Templates")){
            for (int i = 0; i < e.getInventory().getSize(); i++){
                if (e.getClick().isMouseClick() && e.getRawSlot()==i ){
                    if( i >= 9 && i <=44 && e.getCurrentItem()!= null){
                        campName = e.getCurrentItem().getItemMeta().getDisplayName();
                        choiceSize = ci.getCampTemplateSize(campName);
                        templateMenu.menu(player);
                        templateMenu.loadTemplate(campName);
                    }
                    e.setCancelled(true);
                }
            }if (e.getRawSlot()== gbi.getCloseButton()){
                mainMenu.menu(player,plugin);
                e.setCancelled(true);
            }
        }
/////// TEMPLATE MENU
        if (e.getView().getTitle().equals("Template Editor")){

            List<Integer> nonUsableSlots = new ArrayList<>(gcs.getNonUsableSlotsIndexes());
            Bukkit.getConsoleSender().sendMessage("nonUsableSlots " + nonUsableSlots);

            for (Integer slot : nonUsableSlots){
                if (e.getClick().isMouseClick() && e.getRawSlot()==slot ){
                    e.setCancelled(true);
                }
            }
            //Size button
            if (e.getRawSlot()== gbi.getSizeButton()){
                if(e.getClick().isLeftClick()) {
                    choiceSize = Math.min(choiceSize + 1, 4);
                } else if (e.getClick().isRightClick()){
                    choiceSize = Math.max(choiceSize -1, 1);
                }
                templateMenu.setChoiceSize(choiceSize);
                e.getClickedInventory().setItem(gbi.getSizeButton(), templateMenu.getSizeButton(choiceSize));

                gcs.convertSlotsToInt(choiceSize);
                for (Integer slot : nonUsableSlots) {
                    e.getInventory().setItem(slot, templateMenu.getNonUsableItem());
                }
                clearSlots(e.getInventory());
                templateMenu.menu(player);
                e.setCancelled(true);
            }
            //non usable slots area


           //save button function
           if (e.getRawSlot()== gbi.getSaveButton()){
               config.set("CampBlueprint."+campName, null);
               saveBlueprint(ci, config, file, e.getInventory());
               player.sendMessage("Blueprint is saved into configs!");
               e.setCancelled(true);
           }

           // close button function
           if (e.getRawSlot()== gbi.getCloseButton()){
               mainMenu.menu(player, plugin);
               e.setCancelled(true);
               choiceSize = 1;
           }
            // clear button function
            if (e.getRawSlot()== gbi.getClearButton()){
                clearSlots(e.getInventory());
                config.set("CampBlueprint."+campName, null);
                ci.saveConfig(config,file);
                e.setCancelled(true);
            }
            // info button function
            if (e.getRawSlot()== gbi.getInfoButton()){
                e.setCancelled(true);
            }

        }
    }
///// CHAT input
    @EventHandler
    public void nameInputListener (AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        ConfigsInitialize ci = new ConfigsInitialize(plugin);
        if (waitingForInput.getOrDefault(player, false)) {
            e.setCancelled(true); // Prevent the message from being sent to public chat
            campName = e.getMessage();

            if (campName.equalsIgnoreCase("cancel")) {
                player.sendMessage("Input cancelled.");
            } else {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    ci.getTemplateNames().add(campName.toLowerCase());
                    templateMenu.menu(player);
                });
            }
            // Remove the player from the waiting list
            waitingForInput.remove(player);
        }

    }
    public void startChat (Player player){
        player.sendMessage("Please enter a name for a new camp:");
        waitingForInput.put(player, true);
    }
    //END CHAT

///SAVE BLUEPRINT
    public void saveBlueprint(ConfigsInitialize ci, YamlConfiguration config, File file, Inventory inventory)
    {
        List<Integer> placingSlots = new ArrayList<>(gcs.getInputSlotsIndexes());
        for ( Integer slot : placingSlots) {
           if (!isSlotEmpty(inventory, slot)){
               config.set("CampBlueprint."+campName+".blueprint." + slot,inventory.getItem(slot).getType().name());
           } else{
               config.set("CampBlueprint."+campName+".blueprint." + slot, Material.AIR.name());

           }
            config.set("CampBlueprint."+campName+".size", choiceSize);
            ci.saveConfig(config,file);
       }
    }
///CLEAR PLACING SLOTS
    public void clearSlots(Inventory inventory){
        List<Integer> placingSlots = new ArrayList<>(gcs.getInputSlotsIndexes());
        for ( Integer slot : placingSlots) {
            if (!isSlotEmpty(inventory, slot)){
                inventory.removeItem(inventory.getItem(slot));
            }
        }
    }

    public boolean isSlotEmpty(Inventory inv, Integer slot){
        ItemStack item = inv.getItem(slot);

        return (item == null || item.getType()==Material.AIR);
    }

}




