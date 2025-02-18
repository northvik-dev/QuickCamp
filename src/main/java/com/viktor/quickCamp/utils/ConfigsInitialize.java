package com.viktor.quickCamp.utils;

import com.viktor.quickCamp.QuickCamp;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigsInitialize {
    QuickCamp plugin;
    File file;
    YamlConfiguration config;
    List<Integer> slotIndex = new ArrayList<>();

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
        List<String> slots =  config.getStringList("placingArea.placing-slots-rows");

        for (String row : slots){
            for(String slot : row.split(",")){
                try {
                    slotIndex.add(Integer.parseInt(slot.trim()));
                } catch (NumberFormatException e){
                    plugin.getServer().getConsoleSender().sendMessage("Invalid slot number: " + slot);
                }
            }
        }
    }
    public List<Integer> getSlotsIndexes(){
        return slotIndex;
    }

    public YamlConfiguration getYmlConfig(){
        return config;
    }
}
