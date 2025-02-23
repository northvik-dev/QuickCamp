package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import com.viktor.quickCamp.listeners.GuiMenuListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigsInitialize {

    QuickCamp plugin;
    File file;
    YamlConfiguration config;
    List<Integer> slotIndex = new ArrayList<>();
    List<Integer> nonUsableSlotIndex = new ArrayList<>();
    HashMap<Integer,String> campBlueprint = new HashMap<>();
    Integer saveButton;
    Integer closeButton;

    public ConfigsInitialize (QuickCamp plugin){
        this.plugin = plugin;
        loadFile();
        convertSlotsToInt();
    }

    public void loadFile(){
        file = new File(plugin.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()){
            plugin.getServer().getConsoleSender().sendMessage("Config file doesn't exist!");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void convertSlotsToInt (){
        List<String> slots =  config.getStringList("GUI-settings.placing-slots");
        List<String> nonUsableSlots =  config.getStringList("GUI-settings.non-usable");
        saveButton = config.getInt("GUI-settings.buttons.save");
        closeButton = config.getInt("GUI-settings.buttons.close");

        //Getting camp blueprint
        if (config.getConfigurationSection("CampBlueprint")!= null) {
            for (String key : config.getConfigurationSection("CampBlueprint.").getKeys(false)) {
                int slot = Integer.parseInt(key);

                String item = config.getString("CampBlueprint." + key);
                plugin.getServer().getConsoleSender().sendMessage(slot + " " + item);
                campBlueprint.put(slot, item);
            }
        }

        //Indexing place slots
        for (String row : slots){
            for(String slot : row.split(",")){
                try {
                    slotIndex.add(Integer.parseInt(slot.trim()));
                } catch (NumberFormatException e){
                    plugin.getServer().getConsoleSender().sendMessage("Invalid slot number: " + slot);
                }
            }
        }
        //Indexing non-usable slots
        for (String row : nonUsableSlots){
            for(String slot : row.split(",")){
                try {
                    nonUsableSlotIndex.add(Integer.parseInt(slot.trim()));
                } catch (NumberFormatException e){
                    plugin.getServer().getConsoleSender().sendMessage("Invalid slot number: " + slot);
                }
            }
        }




    }

    public HashMap<Integer,String> getCampBlueprint(){
        return campBlueprint;
    }

    public Integer getSaveButton(){
        return saveButton;
    }
    public Integer getCloseButton(){
        return closeButton;
    }
    public List<Integer> getSlotsIndexes(){
        return slotIndex;
    }
    public List<Integer> getNonUsableSlotsIndexes(){
        return nonUsableSlotIndex;
    }

    public YamlConfiguration getYmlConfig(){
        return config;
    }

    public File getFile(){
        return file;
    }
}
