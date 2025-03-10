package com.northvik.quickCamp.utils;

import com.northvik.quickCamp.QuickCamp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigsInitialize {

    QuickCamp plugin;
    File file;
    File locationFile;
    YamlConfiguration config;
    YamlConfiguration campLocationConfig;
    List<Integer> slotIndex = new ArrayList<>();
    List<Integer> nonUsableSlotIndex = new ArrayList<>();
    HashMap<Integer,String> campBlueprint = new HashMap<>();
    Integer saveButton;
    Integer closeButton;
    Integer clearButton;
    Integer infoButton;

    public ConfigsInitialize (QuickCamp plugin){
        this.plugin = plugin;
        loadFile();
        convertSlotsToInt();
    }

    public void loadFile(){
        file = new File(plugin.getPlugin().getDataFolder(), "config.yml");
        locationFile = new File(plugin.getPlugin().getDataFolder(),"playerCampLocations.yml");
        if (!file.exists() || !locationFile.exists()){
            plugin.getServer().getConsoleSender().sendMessage("Config file doesn't exist!");
        }
        campLocationConfig = YamlConfiguration.loadConfiguration(locationFile);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void convertSlotsToInt (){
        List<String> slots =  config.getStringList("GUI-settings.placing-slots");
        List<String> nonUsableSlots =  config.getStringList("GUI-settings.non-usable");
        saveButton = config.getInt("GUI-settings.buttons.save");
        closeButton = config.getInt("GUI-settings.buttons.close");
        clearButton = config.getInt("GUI-settings.buttons.clear");
        infoButton = config.getInt("GUI-settings.buttons.info");

        //Getting camp blueprint
        if (config.getConfigurationSection("CampBlueprint")!= null) {
            for (String key : config.getConfigurationSection("CampBlueprint.").getKeys(false)) {
                int slot = Integer.parseInt(key);

                String item = config.getString("CampBlueprint." + key);
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

    //BUTTONS
    public Integer getSaveButton(){
        return saveButton;
    }
    public Integer getCloseButton(){
        return closeButton;
    }
    public Integer getClearButton(){
        return clearButton;
    }
    public Integer getInfoButton(){
        return infoButton;
    }
    //SLOTS
    public List<Integer> getSlotsIndexes(){
        return slotIndex;
    }
    public List<Integer> getNonUsableSlotsIndexes(){
        return nonUsableSlotIndex;
    }
    //CONFIG
    public YamlConfiguration getYmlConfig(){
        return config;
    }

    public YamlConfiguration getCampLocationConfig(){
        return campLocationConfig;
    }
    public File getLocationFile(){
        return locationFile;
    }
    public File getFile(){
        return file;
    }
    public HashMap<Integer,String> getCampBlueprint(){
        return campBlueprint;
    }


    ///CONFIG SAVE
    public void saveConfig(YamlConfiguration config, File file){
        try {
            config.save(file);
        } catch (Exception ex) {
            Bukkit.getServer().getConsoleSender().sendMessage("Couldn't save configs");
            throw new RuntimeException(ex);
        }
    }
}
